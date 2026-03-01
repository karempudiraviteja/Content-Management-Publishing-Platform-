package com.micro.content;


import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    
    
    // CREATE DRAFT
    @PreAuthorize("hasRole('AUTHOR')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Map<String,String> request){

        Content content = contentService.createDraft(
                request.get("title"),
                request.get("body")
        );

        return ResponseEntity.ok(content);
    }

    // GET MY CONTENT
    @PreAuthorize("hasRole('AUTHOR')")
    @GetMapping("/my")
    public ResponseEntity<?> myContent(){
        return ResponseEntity.ok(contentService.getMyContent());
    }

    // UPDATE
    @PreAuthorize("hasRole('AUTHOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable UUID id,
            @RequestBody Map<String,String> request){

        return ResponseEntity.ok(
                contentService.updateDraft(
                        id,
                        request.get("title"),
                        request.get("body")
                )
        );
    }

    // DELETE
    @PreAuthorize("hasRole('AUTHOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id){
        contentService.deleteContent(id);
        return ResponseEntity.ok("Deleted");
    }
    
    @PreAuthorize("hasAnyRole('PUBLISHER','SUPER_ADMIN')")
    @GetMapping("/approved")
    public ResponseEntity<List<Content>> getApproved() {
        List<Content> approvedContent = contentService.getApprovedContent();
        return ResponseEntity.ok(approvedContent);
    }
}