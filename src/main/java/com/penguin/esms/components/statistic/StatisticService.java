package com.penguin.esms.components.statistic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.penguin.esms.components.statistic.dto.StatisticDTO;
import com.penguin.esms.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class StatisticService {
    private final StatisticRepository repo;
    public StatisticEntity add(String name, Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(object);
        StatisticEntity entity = new StatisticEntity(name, jsonString, new Date(), TimeUtils.getDay(new Date()));
        return repo.save(entity);
    }

    public StatisticEntity getByName(String name) {
        return repo.findByName(name).get();
    }
}