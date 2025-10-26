package com.vadim.reviewservice.repository;

import com.vadim.reviewservice.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, JpaSpecificationExecutor<Review> {
    @Query("SELECT AVG(r.reviewAverage) FROM Review r WHERE r.deviceId = :deviceId")
    List<BigDecimal> getAvgRatingByDeviceId(@Param("deviceId") Long deviceId);
}
