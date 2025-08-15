package com.vadim.reviewservice.service;

import com.vadim.reviewservice.dto.RatingUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class RatingKafkaProducer {

    private final KafkaTemplate<String, RatingUpdateDTO> kafkaTemplate;

    public void sendRating(Long deviceId, BigDecimal newAverageRating) {
        kafkaTemplate.send("rating_updates", new RatingUpdateDTO(deviceId, newAverageRating));
    }
}
