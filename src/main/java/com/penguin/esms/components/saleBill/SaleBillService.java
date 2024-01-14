package com.penguin.esms.components.saleBill;

import com.penguin.esms.components.customer.CustomerEntity;
import com.penguin.esms.components.customer.CustomerRepo;
import com.penguin.esms.components.saleBill.dto.SaleBillDTO;
import com.penguin.esms.components.staff.StaffEntity;
import com.penguin.esms.mapper.DTOtoEntityMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class SaleBillService {
    private final SaleBillRepo saleBillRepo;
    private final DTOtoEntityMapper mapper;
    private final CustomerRepo customerRepo;

    public SaleBillEntity post(SaleBillDTO saleBillDTO, StaffEntity staff) {
        saleBillDTO.setStaffId(staff.getId());
        CustomerEntity customer = customerRepo.findById(saleBillDTO.getCustomerId()).get();
        SaleBillEntity sale = updateFromDTO(saleBillDTO, new SaleBillEntity());
        sale.setCustomer(customer);
        saleBillRepo.save(sale);
        return sale;
    }

    private SaleBillEntity updateFromDTO(SaleBillDTO saleBillDTO, SaleBillEntity sale) {
        mapper.updateSaleBillFromDto(saleBillDTO, sale);
        return sale;
    }

}
