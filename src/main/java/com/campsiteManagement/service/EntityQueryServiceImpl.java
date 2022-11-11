package com.campsiteManagement.service;

import com.campsiteManagement.entity.Booking;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class EntityQueryServiceImpl implements EntityQueryService{

    private final MongoTemplate mongoTemplate;
    private final MongoOperations operations;

    @Override
    public List<Booking> findByStartDateEndDate(LocalDate startDate, LocalDate endDate) {
        final Query query = Query.query(
                (Criteria.where("startDate").gt(startDate).lte(endDate))
                        );
        //.orOperator(Criteria.where("endDate").gt(startDate).lte(endDate))
        return mongoTemplate.find(query, Booking.class, operations.getCollectionName(Booking.class));
    }
}
