package com.micro.admin;



import com.micro.audit.*;

import com.micro.content.ContentRepository;
import com.micro.user.entity.User;
import com.micro.user.enums.Role;
import com.micro.user.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private AuditRepository auditRepository;

    // View all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Change user role
    public User changeUserRole(UUID userId, Role newRole) {

        User user = userRepository.findById(userId)
                .orElseThrow();

        user.setRole(newRole);

        return userRepository.save(user);
    }

    // Delete any content
    public void deleteContent(UUID contentId) {
        contentRepository.deleteById(contentId);
    }

    // View all audit logs
    public List<AuditLog> getAllAuditLogs() {
        return auditRepository.findAll();
    }
}