package com.aptech.ticketshow.data.repositories.specification;

import com.aptech.ticketshow.data.dtos.EventFilterDTO;
import com.aptech.ticketshow.data.entities.Category;
import com.aptech.ticketshow.data.entities.Event;
import jakarta.persistence.criteria.*;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Calendar;
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

            // Always add status filter
            predicates.add(cb.equal(root.get("status").get("id"), eventFilterDTO.getStatusId()));

            if (eventFilterDTO.getSort() == null) {
                // Default: sort by creation date (newest first)
                query.orderBy(cb.desc(root.get("createdAt")));
            } else if ("New".equals(eventFilterDTO.getSort())) {
                // Sort by event start date
                query.orderBy(cb.desc(root.get("createdAt")));
            } else if ("Popular".equals(eventFilterDTO.getSort())) {
                // Subquery to get order count for each event
                Subquery<Long> orderCountSubquery = query.subquery(Long.class);
                Root<?> orderRoot = null;
                try {
                    orderRoot = orderCountSubquery.from(Class.forName("com.aptech.ticketshow.data.entities.Order"));
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

                // Count orders where order.event.id = current event id
                orderCountSubquery.select(cb.count(orderRoot.get("id")))
                        .where(cb.equal(orderRoot.get("event").get("id"), root.get("id")));

                // Sort by this count
                query.orderBy(cb.desc(orderCountSubquery));
            }

            // Filter by category
            if (eventFilterDTO.getCategories() != null && !eventFilterDTO.getCategories().isEmpty()) {
                Join<Event, Category> categoryJoin = root.join("category");
                predicates.add(categoryJoin.get("name").in(eventFilterDTO.getCategories()));
            }

            // Filter by location
            if (eventFilterDTO.getLocations() != null && !eventFilterDTO.getLocations().isEmpty()) {
                List<Predicate> locationPredicates = new ArrayList<>();
                List<Predicate> otherLocationPredicates = new ArrayList<>();
                boolean hasOtherLocations = false;

                for (Object location : eventFilterDTO.getLocations()) {
                    if ("Other Locations".equals(location)) {
                        hasOtherLocations = true;
                    } else {
                        locationPredicates.add(cb.like(root.get("locationProvince"), "%" + location + "%"));
                    }
                }

                if (hasOtherLocations) {
                    otherLocationPredicates.add(cb.not(cb.like(root.get("locationProvince"), "%Ha Noi%")));
                    otherLocationPredicates.add(cb.not(cb.like(root.get("locationProvince"), "%Ho Chi Minh%")));
                }

                if (!locationPredicates.isEmpty() || !otherLocationPredicates.isEmpty()) {
                    Predicate finalPredicate = null;
                    if (!locationPredicates.isEmpty()) {
                        finalPredicate = cb.or(locationPredicates.toArray(new Predicate[0]));
                    }

                    if (hasOtherLocations) {
                        Predicate otherLocationsFilter = cb.and(otherLocationPredicates.toArray(new Predicate[0]));
                        if (finalPredicate != null) {
                            finalPredicate = cb.or(finalPredicate, otherLocationsFilter);
                        } else {
                            finalPredicate = otherLocationsFilter;
                        }
                    }

                    predicates.add(finalPredicate);
                }
            }

            // Filter by date
            if (eventFilterDTO.getDates() != null && !eventFilterDTO.getDates().isEmpty()) {
                Predicate datePredicate = cb.disjunction();
                for (Object date : eventFilterDTO.getDates()) {
                    if (date instanceof Date) {
                        datePredicate = cb.or(datePredicate, cb.between(root.get("endedAt"), (Date) date, (Date) date));
                    } else if (date instanceof String) {
                        if (date.equals("Upcoming Dates")) {
                            datePredicate = cb.or(datePredicate, cb.greaterThanOrEqualTo(root.get("endedAt"), new Date()));
                        } else if (date.equals("Today")) {
                            datePredicate = cb.or(datePredicate, cb.between(root.get("endedAt"), DateUtils.truncate(new Date(), Calendar.DATE), DateUtils.truncate(DateUtils.addDays(new Date(), 1), Calendar.DATE)));
                        } else if (date.equals("Tomorrow")) {
                            datePredicate = cb.or(datePredicate, cb.between(root.get("endedAt"), DateUtils.truncate(DateUtils.addDays(new Date(), 1), Calendar.DATE), DateUtils.truncate(DateUtils.addDays(new Date(), 2), Calendar.DATE)));
                        } else if (date.equals("This week")) {
                            datePredicate = cb.or(datePredicate, cb.between(root.get("endedAt"), new Date(), DateUtils.addDays(new Date(), 7)));
                        }
                    }
                }
                predicates.add(datePredicate);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Event> filterEventWithStatus(EventFilterDTO eventFilterDTO, Long statusId) {
        return (root, query, cb) -> {
            eventFilterDTO.setStatusId(statusId);
            Specification<Event> baseFilter = filterEvent(eventFilterDTO);
            Predicate basePredicates = baseFilter.toPredicate(root, query, cb);

            Predicate statusPredicate = cb.equal(root.get("status").get("id"), statusId);

            return cb.and(basePredicates, statusPredicate);
        };
    }
}
