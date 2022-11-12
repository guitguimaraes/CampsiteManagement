package com.campsitemanagement.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Booking entity.
 */
@Document(collection = "Booking")
@Data
public class Booking {

    @Id
    private String id = UUID.randomUUID().toString();

    private LocalDate startDate;

    private LocalDate endDate;

    @Version
    private Long version;

    private String email;

    private String fullName;
}
