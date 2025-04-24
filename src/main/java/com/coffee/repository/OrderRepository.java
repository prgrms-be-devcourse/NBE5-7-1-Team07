package com.coffee.repository;

import com.coffee.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByEmail(String email);

    @Query("SELECT o FROM Order o WHERE o.deliveryStatus = 'READY' AND o.createdAt <= :cutoff")
    List<Order> findReadyOrdersCreatedBefore(@Param("cutoff") LocalDateTime cutoff);

    @Query("SELECT o FROM Order o WHERE o.deliveryStatus = 'SHIPPING' AND o.arrivalDate = :today")
    List<Order> findShippingOrdersArrivingToday(@Param("today") LocalDate today);

}
