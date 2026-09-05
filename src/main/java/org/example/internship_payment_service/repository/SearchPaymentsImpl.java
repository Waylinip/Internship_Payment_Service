package org.example.internship_payment_service.repository;


import org.example.internship_payment_service.entity.Payment;
import org.example.internship_payment_service.entity.PaymentStatus;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;


public class SearchPaymentsImpl implements SearchPayments {

    private final MongoTemplate mongoTemplate;

    public SearchPaymentsImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Payment> findBy(Long userId, Long orderId, PaymentStatus status) {
        Query query = new Query();
        List<Criteria> criteriaList = new ArrayList<>();

        if (userId != null) {
            criteriaList.add(Criteria.where("userId").is(userId));
        }
        if (orderId != null) {
            criteriaList.add(Criteria.where("orderId").is(orderId));
        }
        if (status != null) {
            criteriaList.add(Criteria.where("status").is(status));
        }

        if (!criteriaList.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
        }

        return mongoTemplate.find(query, Payment.class);
    }
}
