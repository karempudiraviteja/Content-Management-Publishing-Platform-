package com.micro.version;



import com.micro.content.Content;

import com.micro.user.entity.User;
import com.micro.user.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class VersionService {

    @Autowired
    private ContentVersionRepository versionRepository;

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

    // Save previous version snapshot
    public void saveVersion(Content content) {

        User user = getCurrentUser();

        ContentVersion version = new ContentVersion();
        version.setContentId(content.getId());
        version.setVersionNumber(content.getVersion());
        version.setEditedBy(user.getId());
        version.setSnapshot(content.getBody());
        version.setTimestamp(LocalDateTime.now());

        versionRepository.save(version);
    }

    public List<ContentVersion> getHistory(UUID contentId) {
        return versionRepository
                .findByContentIdOrderByVersionNumberDesc(contentId);
    }
}
