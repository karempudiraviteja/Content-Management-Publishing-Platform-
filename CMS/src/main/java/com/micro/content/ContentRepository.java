package com.micro.content;




import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ContentRepository extends JpaRepository<Content, UUID> {

    List<Content> findByAuthorUsername(String username);

    List<Content> findByStatus(String status);
}
