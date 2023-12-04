package com.penguin.esms.components.staff;

import com.penguin.esms.mapper.DTOtoEntityMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class StaffService {
    private final StaffRepository staffRepository;
    private final DTOtoEntityMapper mapper;

    public StaffEntity update(StaffDTO staffDTO, String id){
        StaffEntity staff = staffRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Staff not existed"
                ));
        mapper.updateStaffFromDto(staffDTO, staff);
        return staffRepository.save(staff);
    }
}
