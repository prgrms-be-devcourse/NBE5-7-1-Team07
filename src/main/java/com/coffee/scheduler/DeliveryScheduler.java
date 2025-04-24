package com.coffee.scheduler;

import com.coffee.domain.Order;
import com.coffee.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DeliveryScheduler {

    private final OrderRepository orderRepository;


    @Scheduled(cron = "0 0 14 * * *")
    @Transactional
    public void updateShippingStatus(){
        LocalDateTime cutoff = LocalDateTime.now().withHour(14).withMinute(0);
        List<Order> orders = orderRepository.findReadyOrdersCreatedBefore(cutoff);
        LocalDate today = LocalDate.now();

        for (Order order : orders) {
            order.startShipping(today);
        }

    }

    @Scheduled(cron = "0 0 8 * * *") //상품은 매일 오전8시에 배송지에 도착한다고 가정.
    @Transactional
    public void completeArrivedOrders() {
        LocalDate today = LocalDate.now();
        List<Order> orders = orderRepository.findShippingOrdersArrivingToday(today);

        for (Order order : orders) {
            order.markCompleted();
        }
    }


}
