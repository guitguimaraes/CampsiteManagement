package com.campsiteManagement.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Managing booking Dto
 */
@Data
@Builder
public class BookingResponseDto {

    @ApiModelProperty(value = "Booking Id")
    private String id;
}
