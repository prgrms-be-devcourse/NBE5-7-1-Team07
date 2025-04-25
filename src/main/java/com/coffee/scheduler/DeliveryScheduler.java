package com.coffee.scheduler;

import com.coffee.domain.Order;
import com.coffee.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeliveryScheduler {

    private final OrderRepository orderRepository;


    @Scheduled(cron = "0 0 14 * * *")//매일 14시에 배송(READY -> SHIPPING)
    @Transactional
    public void updateShippingStatus(){
        LocalDateTime cutoff = LocalDateTime.now().withHour(14).withMinute(0);
        List<Order> orders = orderRepository.findReadyOrdersCreatedBefore(cutoff);
        LocalDate today = LocalDate.now();

        for (Order order : orders) {
            order.startShipping(today);
        }

        log.info("[배송시작] {}건의 주문을 SHIPPING 상태로 전환", orders.size());

    }

    @Scheduled(cron = "0 0 8 * * *") //상품은 매일 오전8시에 배송지에 도착한다고 가정.
    @Transactional
    public void completeArrivedOrders() {

        List<Order> orders = orderRepository.findAllShippingOrders();

        for (Order order : orders) {
            order.markCompleted();
        }

        log.info("[배송완료] {}건의 주문을 COMPLETED 상태로 전환", orders.size());
    }


}
