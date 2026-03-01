package com.micro.version;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/version")
public class VersionController {

    @Autowired
    private VersionService versionService;

    @PreAuthorize("hasAnyRole('AUTHOR','EDITOR','SUPER_ADMIN')")
    @GetMapping("/{contentId}")
    public ResponseEntity<?> history(@PathVariable UUID contentId){
        return ResponseEntity.ok(
                versionService.getHistory(contentId)
        );
    }
}