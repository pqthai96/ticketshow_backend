package com.aptech.ticketshow.data.repositories.specification;

import com.aptech.ticketshow.data.dtos.EventFilterDTO;
import com.aptech.ticketshow.data.entities.Category;
import com.aptech.ticketshow.data.entities.Event;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventSpecification implements Specification<Event> {

    private static final long serialVersionUID = 1L;

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return null;
    }

    public static Specification<Event> filterEvent(EventFilterDTO eventFilterDTO) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Sort
            if (eventFilterDTO.getSort() != null) {
                switch (eventFilterDTO.getSort()) {
                    case "popular":
                        query.orderBy(cb.desc(root.get("id")));
                        break;
                    case "date":
                        query.orderBy(cb.desc(root.get("startedAt")));
                        break;
                }
            }

            // Filter by category
            if (eventFilterDTO.getCategories() != null && !eventFilterDTO.getCategories().isEmpty()) {
                Join<Event, Category> categoryJoin = root.join("category");
                predicates.add(categoryJoin.get("name").in(eventFilterDTO.getCategories()));
            }

            // Filter by location
            if (eventFilterDTO.getLocations() != null && !eventFilterDTO.getLocations().isEmpty()) {
                Predicate locationPredicate = cb.disjunction();
                for (Object location : eventFilterDTO.getLocations()) {
                    locationPredicate = cb.or(locationPredicate, cb.like(root.get("locationProvince"), "%" + location + "%"));
                }
                predicates.add(locationPredicate);
            }

            // Filter by date
            if (eventFilterDTO.getDates() != null && !eventFilterDTO.getDates().isEmpty()) {
                Predicate datePredicate = cb.disjunction();
                for (Object date : eventFilterDTO.getDates()) {
                    if (date instanceof Date) {
                        datePredicate = cb.or(datePredicate, cb.between(root.get("startedAt"), (Date) date, (Date) date));
                    } else if (date instanceof String) {
                        // Handle string date format
                    }
                }
                predicates.add(datePredicate);
            }

            // Filter by price
            if (eventFilterDTO.getPrices() != null && !eventFilterDTO.getPrices().isEmpty()) {
                Predicate pricePredicate = cb.disjunction();
                for (Object price : eventFilterDTO.getPrices()) {
                    pricePredicate = cb.or(pricePredicate, cb.like(root.get("type"), "%" + price + "%"));
                }
                predicates.add(pricePredicate);
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
