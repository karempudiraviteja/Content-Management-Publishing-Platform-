package com.micro.audit;


import com.micro.user.entity.User;
import com.micro.user.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuditService {

    @Autowired
    private AuditRepository auditRepository;

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

    public void log(String action, UUID contentId) {

        User user = getCurrentUser();

        AuditLog log = new AuditLog();
        log.setUserId(user.getId());
        log.setUsername(user.getUsername());
        log.setRole(user.getRole().name());
        log.setAction(action);
        log.setContentId(contentId);
        log.setTimestamp(LocalDateTime.now());

        auditRepository.save(log);
    }
}