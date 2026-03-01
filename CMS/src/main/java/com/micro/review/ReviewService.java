package com.micro.review;




import com.micro.audit.AuditService;

import com.micro.content.*;


import com.micro.user.entity.User;
import com.micro.user.enums.Role;
import com.micro.user.repo.UserRepository;
import com.micro.workflow.WorkflowEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {
	@Autowired
	private AuditService auditService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkflowEngine workflowEngine;

    private User getCurrentUser() {
        String username =
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName();

        return userRepository.findByUsername(username)
                .orElseThrow();
    }

    // Get review queue
    public List<Content> getReviewQueue() {
        return contentRepository.findAll()
                .stream()
                .filter(c -> c.getStatus() == ContentStatus.REVIEW)
                .toList();
    }

    // Review content
    public Review reviewContent(UUID contentId,
                                String feedback,
                                ContentStatus decisionStatus) {

        Content content = contentRepository
                .findById(contentId)
                .orElseThrow();

        if (content.getStatus() != ContentStatus.REVIEW) {
            throw new RuntimeException("Content is not in REVIEW state");
        }

        User reviewer = getCurrentUser();

        if (reviewer.getRole() != Role.ROLE_REVIEWER) {
            throw new RuntimeException("Only reviewer can review");
        }

        // Validate workflow transition
        boolean valid = workflowEngine.isValidTransition(
                content.getStatus(),
                reviewer.getRole(),
                decisionStatus
        );

        if (!valid) {
            throw new RuntimeException("Invalid review transition");
        }

        // Save review record
        Review review = new Review();
        review.setContentId(contentId);
        review.setReviewerId(reviewer.getId());
        review.setFeedback(feedback);
        review.setDecision(decisionStatus.name());
        review.setTimestamp(LocalDateTime.now());

        reviewRepository.save(review);

        // Update content status
        content.setStatus(decisionStatus);
        contentRepository.save(content);
        auditService.log("REVIEW_" + decisionStatus.name(), contentId);
       

        return review;
    }
}