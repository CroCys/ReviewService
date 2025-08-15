package com.vadim.reviewservice.mapper;

import com.vadim.reviewservice.dto.ReviewRequestDTO;
import com.vadim.reviewservice.dto.ReviewResponseDTO;
import com.vadim.reviewservice.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(target = "id", ignore = true)
    Review toEntity(ReviewRequestDTO dto);

    ReviewResponseDTO toDto(Review review);
}
