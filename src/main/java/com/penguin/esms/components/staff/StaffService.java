package com.penguin.esms.components.staff;

import com.penguin.esms.mapper.DTOtoEntityMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StaffService {
    private final StaffRepository staffRepository;
    private final DTOtoEntityMapper mapper;

    public void getStaffProfile(Principal connectedUser){
        StaffEntity staff = (StaffEntity) ((UsernamePasswordAuthenticationToken)connectedUser).getPrincipal();
    }

    public StaffEntity update(StaffDTO staffDTO, String id){
        StaffEntity staff = staffRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Staff not existed"
                ));
        mapper.updateStaffFromDto(staffDTO, staff);
        return staffRepository.save(staff);
    }
    
    public void remove(String id) {
        Optional<StaffEntity> staff = staffRepository.findById(id);
        if (staff.isEmpty())
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, new Error("Staff not existed").toString());
        staff.get().setIsStopped(true);
        staffRepository.save(staff.get());
    }

     public List<StaffEntity> findByName(String name) {
        return staffRepository.findByNameContainingIgnoreCaseAndIsStopped(name, false);
    }

    public List<StaffEntity> findResigned(String name) {
        return staffRepository.findByNameContainingIgnoreCaseAndIsStopped(name, true);
    }

    public StaffEntity getOne(String id) {
        Optional<StaffEntity> staff = staffRepository.findById(id);
        if (staff.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Staff not found");
        }
        return staff.get();
    }
}
