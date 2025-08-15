package com.vadim.reviewservice.controller;

import com.vadim.reviewservice.dto.ReviewRequestDTO;
import com.vadim.reviewservice.dto.ReviewResponseDTO;
import com.vadim.reviewservice.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/getAll")
    public ResponseEntity<Page<ReviewResponseDTO>> getAllReviews(Pageable pageable) {
        Page<ReviewResponseDTO> reviews = reviewService.getAllReviews(pageable);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ReviewResponseDTO> getReviewById(@PathVariable Long id) {
        ReviewResponseDTO review = reviewService.getReviewById(id);
        return ResponseEntity.ok(review);
    }

    @PostMapping("/create")
    public ResponseEntity<ReviewResponseDTO> createReview(@Valid @RequestBody ReviewRequestDTO reviewRequestDTO) {
        ReviewResponseDTO savedReview = reviewService.createReview(reviewRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<ReviewResponseDTO> updateReview(@PathVariable Long id, @Valid @RequestBody ReviewRequestDTO updatedReviewDTO) {
        ReviewResponseDTO savedReview = reviewService.updateReview(id, updatedReviewDTO);
        return ResponseEntity.ok(savedReview);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReviewById(id);
        return ResponseEntity.noContent().build();
    }
}
