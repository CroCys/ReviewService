package com.vadim.reviewservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long deviceId;

    @NotNull
    private Long userId;

    @Min(1)
    @Max(5)
    private Integer buildQuality;

    @Min(1)
    @Max(5)
    private Integer ergonomics;

    @Min(1)
    @Max(5)
    private Integer performance;

    @Min(1)
    @Max(5)
    private Integer features;

    @Min(1)
    @Max(5)
    private Integer design;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "5.0")
    @Column(precision = 2, scale = 1)
    private BigDecimal reviewAverage;

    public void calculateReviewAverage() {
        BigDecimal sum = BigDecimal.valueOf(buildQuality)
                .add(BigDecimal.valueOf(ergonomics))
                .add(BigDecimal.valueOf(performance))
                .add(BigDecimal.valueOf(features))
                .add(BigDecimal.valueOf(design));

        this.reviewAverage = sum
                .divide(BigDecimal.valueOf(5), 1, RoundingMode.HALF_UP);
    }
}
