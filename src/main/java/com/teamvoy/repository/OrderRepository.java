package com.teamvoy.repository;

import com.teamvoy.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByPaidAndOrderTimeBefore(boolean paid, LocalDateTime time);
}
