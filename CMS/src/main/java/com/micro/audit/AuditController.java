package com.micro.audit;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/audit")
public class AuditController {

    @Autowired
    private AuditRepository auditRepository;

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/content/{contentId}")
    public ResponseEntity<?> getLogs(@PathVariable UUID contentId){
        return ResponseEntity.ok(
                auditRepository.findByContentId(contentId)
        );
    }
}