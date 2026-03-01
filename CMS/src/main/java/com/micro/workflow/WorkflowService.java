package com.micro.workflow;



import com.micro.audit.AuditService;
import com.micro.content.Content;
import com.micro.content.ContentRepository;
import com.micro.content.ContentStatus;
import com.micro.user.entity.User;
import com.micro.user.repo.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WorkflowService {
	
	@Autowired
	private AuditService auditService;

    @Autowired
    private WorkflowEngine workflowEngine;

    @Autowired
    private ContentRepository contentRepository;
  

    @Autowired
    private UserRepository userRepository;

    private User getCurrentUser() {
        String username =
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName();

        return userRepository.findByUsername(username)
                .orElseThrow();
    }

    public Content transition(UUID contentId,
                              ContentStatus targetStatus) {

        Content content = contentRepository
                .findById(contentId)
                .orElseThrow();

        User user = getCurrentUser();

        boolean valid = workflowEngine.isValidTransition(
                content.getStatus(),
                user.getRole(),
                targetStatus
        );

        if (!valid) {
            throw new RuntimeException(
                    "Invalid transition: " +
                    content.getStatus() +
                    " → " +
                    targetStatus +
                    " for role " +
                    user.getRole()
            );
        }

        content.setStatus(targetStatus);

        
        Content updated = contentRepository.save(content);

        auditService.log("STATE_TRANSITION_" + targetStatus, updated.getId());

        return updated;
    }
}