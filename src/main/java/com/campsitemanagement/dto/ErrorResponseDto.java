package com.campsitemanagement.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Error return DTO.
 */
@Data
@Builder
public class ErrorResponseDto {
    @ApiModelProperty(value = "message", position = 1)
    private String message;
}
