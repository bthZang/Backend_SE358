package com.penguin.esms.components.statistic;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.penguin.esms.components.category.CategoryEntity;
import com.penguin.esms.components.saleBill.SaleBillEntity;
import com.penguin.esms.components.saleBill.SaleBillService;
import com.penguin.esms.components.saleProduct.SaleProductEntity;
import com.penguin.esms.components.statistic.dto.StatisticDTO;
import com.penguin.esms.envers.AuditEnversInfo;
import com.penguin.esms.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class StatisticService {
    private final StatisticRepository repo;
    private final SaleBillService saleBillService;

    public StatisticEntity add(String name, Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(object);
        StatisticEntity entity = new StatisticEntity(name, jsonString, new Date(), TimeUtils.getDay(new Date()));
        return repo.save(entity);
    }

    public StatisticEntity getByName(String name) {
        return repo.findByName(name).get();
    }

    public StatisticDTO getRevenuePeriod(Date start, Date end) throws JsonProcessingException {
        StatisticDTO dto = new StatisticDTO(null, 0l, 0l, 0);
        Map<String, StatisticDTO> map = new HashMap<>();
        for (Long i = TimeUtils.getDay(start); i < TimeUtils.getDay(end); i++) {
            Optional<StatisticEntity> statisticEntityOptional = repo.findByName("revenueByPeriod" + (i - 1) + i);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                StatisticDTO statistic = objectMapper.readValue(statisticEntityOptional.get().getData(), StatisticDTO.class);
                dto.setQuantity(dto.getQuantity() + statistic.getQuantity());
                dto.setRevenue(dto.getRevenue() + statistic.getRevenue());
            }catch (NoSuchElementException s){}

        }
        return dto;
    }

    public StatisticDTO getRevenueDate(Date date) throws JsonProcessingException {
        Long i = TimeUtils.getDay(date);
        StatisticDTO dto = new StatisticDTO(null, 0l, 0l, 0);
        Map<String, StatisticDTO> map = new HashMap<>();
        Optional<StatisticEntity> statisticEntityOptional = repo.findByName("revenueByDate" + i);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            StatisticDTO statistic = objectMapper.readValue(statisticEntityOptional.get().getData(), StatisticDTO.class);
            dto.setQuantity(dto.getQuantity() + statistic.getQuantity());
            dto.setRevenue(dto.getRevenue() + statistic.getRevenue());
        } catch (NoSuchElementException s) {
        }

        return dto;
    }

    public StatisticDTO revenueByPeriod(Date start, Date end) throws JsonProcessingException {
        StatisticDTO dto = new StatisticDTO(null, 0l, 0l, 0);
        Map<String, StatisticDTO> map = new HashMap<>();
        List<AuditEnversInfo> auditEnversInfoList = (List<AuditEnversInfo>) saleBillService.getAllRevisions(start, end);
        for (AuditEnversInfo i : auditEnversInfoList) {
            SaleBillEntity saleBill = (SaleBillEntity) i.getRevision();
            for (SaleProductEntity t : saleBill.getSaleProducts()) {
                try {
                    dto.setQuantity(dto.getQuantity() + t.getQuantity());
                    dto.setRevenue(dto.getRevenue() + t.getPrice() * t.getQuantity());
                } catch (NullPointerException e) {
                }
            }
        }
        add("revenueByPeriod" + TimeUtils.getDay(start) + TimeUtils.getDay(end), dto);
        return dto;
    }

    public List<?> revenueByCategory(Date start, Date end) throws JsonProcessingException {
        StatisticDTO dto = new StatisticDTO();
        Map<String, StatisticDTO> map = new HashMap<>();
        List<AuditEnversInfo> auditEnversInfoList = (List<AuditEnversInfo>) saleBillService.getAllRevisions(start, end);
        for (AuditEnversInfo i : auditEnversInfoList) {
            SaleBillEntity saleBill = (SaleBillEntity) i.getRevision();
            for (SaleProductEntity t : saleBill.getSaleProducts()) {
                try {
                    CategoryEntity category = t.getProduct().getCategory();
                    dto = map.get(category.getName());
                    if (dto == null) {
                        dto = new StatisticDTO();
                        dto.setName(category.getName());
                        dto.setRevenue(0L);
                        dto.setQuantity(0);
                    }
                    dto.setQuantity(dto.getQuantity() + t.getQuantity());
                    dto.setRevenue(dto.getRevenue() + t.getQuantity() * t.getPrice());
                    map.put(category.getName(), dto);
                } catch (NullPointerException e) {
                }
            }
        }
        return Arrays.asList(map.entrySet().toArray());
    }

}
