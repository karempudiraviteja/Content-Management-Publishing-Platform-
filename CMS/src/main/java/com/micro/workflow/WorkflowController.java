package com.micro.workflow;



import com.micro.content.Content;
import com.micro.content.ContentStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/workflow")
public class WorkflowController {

    @Autowired
    private WorkflowService workflowService;

    @PutMapping("/{id}/transition")
    public ResponseEntity<?> transition(
            @PathVariable UUID id,
            @RequestParam ContentStatus targetStatus) {

        Content updated =
                workflowService.transition(id, targetStatus);

        return ResponseEntity.ok(updated);
    }
}