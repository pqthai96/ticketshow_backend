package com.aptech.ticketshow.data.repositories;

import com.aptech.ticketshow.data.dtos.EventDTO;
import com.aptech.ticketshow.data.entities.Event;
import com.aptech.ticketshow.data.entities.Ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value =
                    "SELECT e.* \n" +
                    "  FROM events e\n" +
                    "  JOIN categories c ON c.id = e.category_id \n" +
                    " WHERE 1=1 \n" +
                    "   AND (:categories IS NULL OR (c.name IN (" +
                            "select substring_index(substring_index(:categories,',',numbers.n),',', -1) value\n" +
                            "from (select 1 n  union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7) numbers\n" +
                            "where numbers.n <= 1 + (length(:categories) - length(replace(:categories,',','')))\n" +
                            "))) \n"
            , nativeQuery = true)
    List<Event> findByCondition(@Param("categories") String categories);

}
