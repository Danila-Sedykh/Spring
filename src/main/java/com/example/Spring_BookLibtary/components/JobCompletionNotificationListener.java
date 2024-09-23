package com.example.Spring_BookLibtary.components;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private final SimpMessagingTemplate messagingTemplate;


    public JobCompletionNotificationListener(SimpMessagingTemplate messagingTemplate){
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus().isUnsuccessful()) {
            // Уведомляем о неудачной обработке файла
            messagingTemplate.convertAndSend("/topic", "Ошибка при обработке файла.");
        } else {
            // Уведомляем об успешной обработке файла
            messagingTemplate.convertAndSend("/topic", "Файл успешно обработан.");
        }
    }
}
