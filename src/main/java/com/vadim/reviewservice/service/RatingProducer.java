package com.vadim.reviewservice.service;

import com.vadim.kafka.dto.RatingUpdateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingProducer {

    private final KafkaTemplate<Long, RatingUpdateEvent> kafkaTemplate;

    public void sendRatingUpdate(RatingUpdateEvent updateEvent) {
        kafkaTemplate.send("device-rating-updates", updateEvent);
    }
}
