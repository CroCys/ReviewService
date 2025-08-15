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
    private Long deviceId; // foreign key к device (внешний сервис)

    @NotNull
    private Long userId;   // foreign key к user (внешний сервис)

    @Min(1)
    @Max(5)
    private int buildQuality;

    @Min(1)
    @Max(5)
    private int ergonomics;

    @Min(1)
    @Max(5)
    private int performance;

    @Min(1)
    @Max(5)
    private int features;

    @Min(1)
    @Max(5)
    private int design;

    @DecimalMin("0.0")
    @DecimalMax("5.0")
    private BigDecimal reviewAverage;

    public void calculateReviewAverage() {
        this.reviewAverage = BigDecimal.valueOf((buildQuality + ergonomics + performance + features + design) / 5.0)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
