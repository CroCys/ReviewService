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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    public Page<ReviewResponseDTO> getAllReviews(Pageable pageable) {
        return reviewRepository.findAll(pageable).map(reviewMapper::toDto);
    }

    public ReviewResponseDTO getReviewById(Long id) {
        return reviewRepository.findById(id)
                .map(reviewMapper::toDto)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found with id " + id));
    }

    public BigDecimal getAvgRating(Long deviceId) {
        List<BigDecimal> avg = reviewRepository.getAvgRatingByDeviceId(deviceId);

        if (avg == null || avg.isEmpty()) {
            return BigDecimal.ZERO.setScale(1, RoundingMode.HALF_UP);
        }

        BigDecimal sum = avg.stream().reduce(BigDecimal.ZERO, BigDecimal::add);

        return sum.divide(BigDecimal.valueOf(avg.size()), 1, RoundingMode.HALF_UP);
    }

    public ReviewResponseDTO createReview(ReviewRequestDTO reviewRequestDTO) {
        Review review = reviewMapper.toEntity(reviewRequestDTO);
        review.calculateReviewAverage();
        review = reviewRepository.save(review);
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
