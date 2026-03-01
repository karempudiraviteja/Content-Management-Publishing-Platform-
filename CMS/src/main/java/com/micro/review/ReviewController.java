package com.micro.review;



import com.micro.content.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // Get review queue
    @PreAuthorize("hasRole('REVIEWER')")
    @GetMapping("/queue")
    public ResponseEntity<List<Content>> queue(){
        return ResponseEntity.ok(reviewService.getReviewQueue());
    }

    // Approve / Reject
    @PreAuthorize("hasRole('REVIEWER')")
    @PostMapping("/{contentId}")
    public ResponseEntity<Review> review(
            @PathVariable UUID contentId,
            @RequestBody Map<String,String> request){

        ContentStatus decision =
                ContentStatus.valueOf(request.get("decision"));

        Review review = reviewService.reviewContent(
                contentId,
                request.get("feedback"),
                decision
        );

        return ResponseEntity.ok(review);
    }
}
