package com.campsiteManagement.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.UUID;

@Document(collection = "Booking")
@Data
@Builder
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
