package com.vadim.reviewservice.dto;


import java.io.Serializable;
import java.math.BigDecimal;

public class ReviewResponseDTO implements Serializable {
    private Long id;
    private Long userId;
    private Long deviceId;
    private int buildQuality;
    private int ergonomics;
    private int performance;
    private int features;
    private int design;
    private BigDecimal reviewAverage;
}
