package ru.aston.meet.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.aston.meet.repository.user.UserRepository;

/**
 * Конфигурационный класс Spring Security для настройки компонентов аутентификации приложения.
 * <p>
 * Определяет бины для сервисов работы с пользователями, менеджера аутентификации,
 * провайдера аутентификации и кодировщика паролей.
 * </p>
 *
 * <ul>
 *   <li>Настраивает сервис {@link UserDetailsService}, получающий данные пользователя из {@link UserRepository} по email</li>
 *   <li>Настраивает {@link AuthenticationProvider} на основе {@link DaoAuthenticationProvider} с привязкой к {@link UserDetailsService} и {@link PasswordEncoder}</li>
 *   <li>Определяет {@link AuthenticationManager} для управления процессом аутентификации</li>
 *   <li>Настраивает {@link PasswordEncoder} для безопасного хранения паролей пользователей (BCrypt)</li>
 * </ul>
 *
 * Использует Lombok-аннотацию {@code @RequiredArgsConstructor} для внедрения {@link UserRepository}.
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    /** Репозиторий пользователей для получения информации о пользователях */
    private final UserRepository userRepository;

    /**
     * Бин {@link UserDetailsService} для получения данных пользователя по email.
     *
     * @return реализация {@link UserDetailsService}, осуществляющая поиск пользователя по email
     * @throws UsernameNotFoundException если пользователь не найден
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Бин {@link AuthenticationProvider}, обеспечивающий аутентификацию пользователей средствами {@link DaoAuthenticationProvider}.
     *
     * @param passwordEncoder используемый кодировщик пароля для проверки хэшей паролей
     * @return настроенный {@link AuthenticationProvider}
     */
    @Bean
    public AuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Бин {@link AuthenticationManager}, управляющий аутентификацией в приложении.
     *
     * @param configuration конфигурация аутентификации
     * @return экземпляр {@link AuthenticationManager}
     * @throws Exception при ошибках инициализации менеджера аутентификации
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Бин {@link PasswordEncoder}, используемый для шифрования паролей пользователей.
     * Реализация основана на {@link BCryptPasswordEncoder}.
     *
     * @return экземпляр {@link BCryptPasswordEncoder}
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
