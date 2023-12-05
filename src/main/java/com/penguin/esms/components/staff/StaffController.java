package com.penguin.esms.components.staff;

import com.penguin.esms.components.staff.requests.NewStaffRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping("staff")
@RequiredArgsConstructor
public class StaffController {

    private final StaffRepository staffRepository;
    private final StaffService staffService;

    @GetMapping("profile")
    public ResponseEntity<?> getStaffProfile(Principal connectedUser){

        return ResponseEntity.ok(connectedUser);
    }

    @GetMapping("")
    public ResponseEntity<?> getList(@RequestParam(defaultValue = "") String name) {
        return ResponseEntity.ok(staffService.findByName(name));
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
    @DeleteMapping(path = "{id}")
    public void delete(@PathVariable String id){
        staffService.delete(id);
    }
}
