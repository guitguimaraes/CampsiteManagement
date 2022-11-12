package com.campsitemanagement.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Campsite configuration class.
 */
@ConfigurationProperties(prefix = "campsite")
@Configuration
@Data
public class CampsiteConfiguration {
    private int maxDaysBooking;
    private int minDaysAhead;
    private int maxDaysAhead;
}
