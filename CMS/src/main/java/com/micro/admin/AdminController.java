package com.micro.admin;


import com.micro.user.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // View all users
    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    // Change user role
    @PutMapping("/users/{userId}/role")
    public ResponseEntity<?> changeRole(
            @PathVariable UUID userId,
            @RequestBody Map<String, String> request) {

        Role role = Role.valueOf(request.get("role"));

        return ResponseEntity.ok(
                adminService.changeUserRole(userId, role)
        );
    }

    // Delete content
    @DeleteMapping("/content/{contentId}")
    public ResponseEntity<?> deleteContent(
            @PathVariable UUID contentId) {

        adminService.deleteContent(contentId);
        return ResponseEntity.ok("Content deleted by admin");
    }

    // View all audit logs
    @GetMapping("/audit")
    public ResponseEntity<?> getAuditLogs() {
        return ResponseEntity.ok(
                adminService.getAllAuditLogs()
        );
    }
}