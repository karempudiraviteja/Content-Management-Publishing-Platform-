package com.micro.audit;




import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AuditRepository
        extends JpaRepository<AuditLog, UUID> {

    List<AuditLog> findByContentId(UUID contentId);
}