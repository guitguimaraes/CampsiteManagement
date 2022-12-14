package com.campsitemanagement.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Managing booking Dto.
 */
@Data
public class BookingResponseDto {

    @ApiModelProperty(value = "Booking Id")
    private String id;

    @ApiModelProperty(value = "FullName")
    @NotNull(message = "fullName Can not be Null")
    private String fullName;

    @ApiModelProperty(value = "Email")
    @NotNull(message = "Email Can not be Null")
    private String email;

    @ApiModelProperty(value = "Start Booking Date")
    @NotNull(message = "start date Can not be Null")
    private LocalDate startDate;

    @ApiModelProperty(value = "End Booking Date")
    @NotNull(message = "End date Can not be Null")
    private LocalDate endDate;
}
