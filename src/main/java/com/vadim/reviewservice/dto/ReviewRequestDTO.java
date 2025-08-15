package com.vadim.reviewservice.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Size
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequestDTO {

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
}
