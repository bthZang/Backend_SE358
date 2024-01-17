package com.penguin.esms.components.staff;

import com.penguin.esms.components.staff.requests.NewStaffRequest;
import com.penguin.esms.services.AmazonS3Service;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("staff")
@RequiredArgsConstructor
public class StaffController {

    private final StaffRepository staffRepository;
    private final StaffService staffService;
    private final AmazonS3Service amazonS3Service;

    @PostMapping("")
    public ResponseEntity<?> postStaff(@RequestParam @Nullable MultipartFile photo, StaffDTO dto) throws Exception {
        if (photo != null){
            String objectURL = amazonS3Service.addFile(photo, dto.getName() + "_" + photo.getOriginalFilename());
            dto.setPhotoURL(objectURL);
        }

        return ResponseEntity.ok(staffService.addStaff(dto));
    }
    @GetMapping("profile")
    public ResponseEntity<?> getStaffProfile(Principal connectedUser) {
        StaffEntity staff = (StaffEntity) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return ResponseEntity.ok(staff);
    }
    @GetMapping("resigned")
    @PreAuthorize("hasAuthority('VIEW_ITEM:STAFF:' + #id) or hasAuthority('VIEW_ALL:STAFF') or hasAuthority('ADMIN')")
    public List<StaffEntity> getAllResigned(@RequestParam(defaultValue = "") String name) {
        return staffService.findResigned(name);
    }

    
    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_ITEM:STAFF:' + #id) or hasAuthority('VIEW_ALL:STAFF') or hasAuthority('ADMIN')")
    public List<StaffEntity> getAll(@RequestParam(defaultValue = "") String name) {
        return staffService.findByName(name);
    }
  
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('VIEW_ITEM:STAFF:' + #id) or hasAuthority('VIEW_ALL:STAFF') or hasAuthority('ADMIN')")
    public StaffEntity getStaffById(@PathVariable String id) {
        return staffService.getOne(id);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('CREATE:STAFF') or hasAuthority('ADMIN')")
    public ResponseEntity<?> createStaff(@RequestBody NewStaffRequest newStaff) {
        return ResponseEntity.ok(staffRepository.save(new StaffEntity(newStaff.getName(), newStaff.getPhone(), newStaff.getPassword(), newStaff.getEmail(), newStaff.getCitizenId(), newStaff.getRole())));
    }
    @PutMapping(path = "{id}")
    public ResponseEntity<?>  edit(@RequestBody StaffDTO staffDTO, @PathVariable String id) {
        return ResponseEntity.ok(staffService.update(staffDTO, id));
    }
    @PutMapping(path = "changePassword")
    public void changePass(@RequestParam String oldPassword, @RequestParam String newPassword, Principal connectedUser) throws Exception {
        staffService.changePassword(oldPassword, newPassword, connectedUser);
    }
    @DeleteMapping(path = "{id}")
    public void delete(@PathVariable String id) {
        staffService.remove(id);
    }
}
