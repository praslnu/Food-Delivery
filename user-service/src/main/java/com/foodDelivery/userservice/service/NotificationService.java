package com.foodDelivery.userservice.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class NotificationService{
    public static final String QUEUE = "message_queue";
    @RabbitListener(queues = NotificationService.QUEUE)
    public void listener(String message) {
        log.info(message);
    }
}
