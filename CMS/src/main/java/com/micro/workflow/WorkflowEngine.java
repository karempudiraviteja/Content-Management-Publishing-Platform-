package com.micro.workflow;

import com.micro.content.ContentStatus;
import com.micro.user.enums.Role;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class WorkflowEngine {

    private final Map<ContentStatus,
            Map<Role, List<ContentStatus>>> rules = new HashMap<>();

    @PostConstruct
    public void init() {

        // DRAFT → REVIEW (Author)
        rules.put(ContentStatus.DRAFT,
                Map.of(Role.ROLE_AUTHOR,
                        List.of(ContentStatus.REVIEW)));

        // REVIEW → APPROVED / REJECTED (Reviewer)
        rules.put(ContentStatus.REVIEW,
                Map.of(Role.ROLE_REVIEWER,
                        List.of(ContentStatus.APPROVED,
                                ContentStatus.REJECTED)));

        // APPROVED → PUBLISHED (Publisher)
        rules.put(ContentStatus.APPROVED,
                Map.of(Role.ROLE_PUBLISHER,
                        List.of(ContentStatus.PUBLISHED)));
    }

    public boolean isValidTransition(ContentStatus current,
                                     Role role,
                                     ContentStatus target) {

        if (!rules.containsKey(current)) return false;

        Map<Role, List<ContentStatus>> roleMap = rules.get(current);

        if (!roleMap.containsKey(role)) return false;

        return roleMap.get(role).contains(target);
    }
}