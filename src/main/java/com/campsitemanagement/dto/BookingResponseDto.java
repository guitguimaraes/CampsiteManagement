package com.campsitemanagement.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Managing booking Dto.
 */
@Data
@Builder
public class BookingResponseDto {

    @ApiModelProperty(value = "Booking Id")
    private String id;
}
