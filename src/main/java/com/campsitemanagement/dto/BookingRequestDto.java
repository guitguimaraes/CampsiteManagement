package com.campsitemanagement.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Managing booking Dto.
 */
@Data
public class BookingRequestDto {

    @ApiModelProperty(value = "FullName")
    @NotNull(message = "FullName Can not be Null")
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
