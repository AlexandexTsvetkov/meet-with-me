package ru.aston.notification.service.notification;

public interface NotificationService {

    /**
     * Отправка уведомления
     */
    void sendEmail(String to, String subject, String text);
}