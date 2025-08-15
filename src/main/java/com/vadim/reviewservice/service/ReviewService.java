package com.vadim.reviewservice.service;

import com.vadim.reviewservice.dto.ReviewRequestDTO;
import com.vadim.reviewservice.dto.ReviewResponseDTO;
import com.vadim.reviewservice.entity.Review;
import com.vadim.reviewservice.exception.ReviewNotFoundException;
import com.vadim.reviewservice.mapper.ReviewMapper;
import com.vadim.reviewservice.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final RatingKafkaProducer ratingKafkaProducer;

    public Page<ReviewResponseDTO> getAllReviews(Pageable pageable) {
        return reviewRepository.findAll(pageable).map(reviewMapper::toDto);
    }

    public ReviewResponseDTO getReviewById(Long id) {
        return reviewRepository.findById(id)
                .map(reviewMapper::toDto)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found with id " + id));
    }

    public ReviewResponseDTO createReview(ReviewRequestDTO reviewRequestDTO) {
        Review review = reviewMapper.toEntity(reviewRequestDTO);
        review.calculateReviewAverage();
        review = reviewRepository.save(review);
        ratingKafkaProducer.sendRating(review.getDeviceId(), review.getReviewAverage());
        return reviewMapper.toDto(review);
    }

    public ReviewResponseDTO updateReview(Long id, ReviewRequestDTO updatedReviewDTO) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid review id " + id);
        }

        if (updatedReviewDTO == null) {
            throw new IllegalArgumentException("Invalid review data");
        }

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("No review found with id " + id));

        review.setBuildQuality(updatedReviewDTO.getBuildQuality());
        review.setErgonomics(updatedReviewDTO.getErgonomics());
        review.setPerformance(updatedReviewDTO.getPerformance());
        review.setFeatures(updatedReviewDTO.getFeatures());
        review.setDesign(updatedReviewDTO.getDesign());

        Review savedReview = reviewRepository.save(review);
        return reviewMapper.toDto(savedReview);
    }

    public void deleteReviewById(Long id) {
        try {
            reviewRepository.deleteById(id);
        } catch (ReviewNotFoundException e) {
            throw new ReviewNotFoundException("No review found with id " + id);
        }
    }
}
