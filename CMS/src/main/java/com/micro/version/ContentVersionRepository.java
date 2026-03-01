package com.micro.version;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ContentVersionRepository
        extends JpaRepository<ContentVersion, UUID> {

    List<ContentVersion> findByContentIdOrderByVersionNumberDesc(UUID contentId);
}
