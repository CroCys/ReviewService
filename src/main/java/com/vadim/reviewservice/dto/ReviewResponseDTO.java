package com.vadim.reviewservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDTO implements Serializable {
    private Long id;
    private Long userId;
    private Long deviceId;
    private Integer buildQuality;
    private Integer ergonomics;
    private Integer performance;
    private Integer features;
    private Integer design;
    private Double reviewAverage;
}
