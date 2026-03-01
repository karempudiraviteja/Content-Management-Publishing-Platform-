package com.micro.content;


import com.micro.audit.AuditService;
import com.micro.user.entity.User;
import com.micro.user.repo.UserRepository;
import com.micro.version.VersionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ContentService {
	
	@Autowired
	private AuditService auditService;
	
	@Autowired
	private VersionService versionService;

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

    // CREATE DRAFT
    public Content createDraft(String title, String body) {

        User author = getCurrentUser();

        Content content = new Content();
        content.setTitle(title);
        content.setBody(body);
        content.setStatus(ContentStatus.DRAFT);
        content.setAuthor(author);
        content.setVersion(1);

        Content saved = contentRepository.save(content);
        auditService.log("CREATE_DRAFT", saved.getId());
        return saved;
    }

    // GET ALL MY CONTENT
    public List<Content> getMyContent() {
        User user = getCurrentUser();
        return contentRepository.findByAuthorUsername(user.getUsername());
    }

    // GET BY ID
    public Content getById(UUID id) {
        return contentRepository.findById(id)
                .orElseThrow();
    }

    // UPDATE DRAFT (ONLY DRAFT ALLOWED)
    public Content updateDraft(UUID id, String title, String body) {

        Content content = getById(id);

        if (content.getStatus() != ContentStatus.DRAFT) {
            throw new RuntimeException("Only DRAFT can be edited");
        }

        // Save previous version before updating
        versionService.saveVersion(content);

        content.setTitle(title);
        content.setBody(body);
        content.setVersion(content.getVersion() + 1);

        Content updated = contentRepository.save(content);
        auditService.log("UPDATE_DRAFT", updated.getId());
        return updated;
    }

    // DELETE
    public void deleteContent(UUID id) {
        contentRepository.deleteById(id);
    }
    
    public List<Content> getApprovedContent() {
        return contentRepository.findAll()
                .stream()
                .filter(c -> c.getStatus() == ContentStatus.APPROVED)
                .collect(Collectors.toList());
    }
}