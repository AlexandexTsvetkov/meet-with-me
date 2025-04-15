package ru.aston.meet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * Конфигурационный класс приложения для настройки асинхронного выполнения и шедулинга задач.
 * <p>
 * Включает поддержку асинхронных методов ({@link EnableAsync}) и планировщика задач по расписанию ({@link EnableScheduling}).
 * Определяет бин {@link TaskScheduler} на основе {@link ThreadPoolTaskScheduler} с размером пула из 5 потоков.
 * </p>
 *
 * <ul>
 *     <li>Обеспечивает обработку асинхронных задач и задач, выполняемых по расписанию.</li>
 *     <li>Бин {@link TaskScheduler} применяется для управления отложенным и периодическим выполнением задач.</li>
 * </ul>
 */
@Configuration
@EnableAsync
@EnableScheduling
public class AppConfig {

    /**
     * Создаёт и настраивает {@link TaskScheduler} для выполнения задач в пуле потоков.
     *
     * @return экземпляр {@link ThreadPoolTaskScheduler} с пулом из 5 потоков
     */
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(5);
        return threadPoolTaskScheduler;
    }
}
