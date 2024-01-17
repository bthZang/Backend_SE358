package com.penguin.esms.components.permission;

import com.penguin.esms.components.permission.dto.PermissionRequest;
import com.penguin.esms.components.permission.dto.StaffPermissionResponse;
import com.penguin.esms.components.staff.StaffEntity;
import com.penguin.esms.components.staff.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PermissionService {
    private final PermissionRepo permissionRepo;
    private final StaffRepository staffRepository;

    public StaffPermissionResponse getStaffPermission(String staffId) {
        StaffEntity staff = staffRepository.findById(staffId).get();
        return new StaffPermissionResponse(permissionRepo.findByStaffIdAndIsStopped(staffId, false), (List<GrantedAuthority>) staff.getAuthorities());
    }

    public PermissionEntity add(PermissionRequest permissionRequest, String staffId) {
        Optional<StaffEntity> staff = staffRepository.findById(staffId);
        PermissionEntity permission = new PermissionEntity();
        permission.setPermissionType(permissionRequest.getPermissionType());
        permission.setEntityType(permissionRequest.getEntityType());
        permission.setEntityId(permissionRequest.getEntityId());
        staff.ifPresent(permission::setStaff);
        return permissionRepo.save(permission);
    }

    public void remove(String permissionId) {
        permissionRepo.deleteById(permissionId);
        PermissionEntity permission = permissionRepo.findById(permissionId).get();
        permission.setIsStopped(true);
        permissionRepo.save(permission);
    }
}
