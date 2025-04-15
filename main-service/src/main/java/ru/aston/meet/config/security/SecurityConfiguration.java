package ru.aston.meet.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Конфигурационный класс для настройки безопасности Spring Security в приложении.
 * <p>
 * Устанавливает правила авторизации, политики сессий, интеграцию с JWT-фильтрацией и кастомный {@link AuthenticationProvider}.
 * </p>
 *
 * <ul>
 *     <li>Отключает CSRF-защиту для REST API.</li>
 *     <li>Определяет общедоступные маршруты (например, /auth/*, Swagger UI и корень "/").</li>
 *     <li>Все остальные запросы требуют аутентификации.</li>
 *     <li>Использует статeless-политику сессий (без сохранения состояния на сервере).</li>
 *     <li>Встраивает JWT-фильтр до {@link UsernamePasswordAuthenticationFilter} для обработки JWT-токенов.</li>
 *     <li>Использует кастомный {@link AuthenticationProvider} для логики аутентификации.</li>
 * </ul>
 *
 * Использует аннотацию {@code @RequiredArgsConstructor} для внедрения зависимостей через final-поля:
 * <ul>
 *  <li>{@link JwtAuthenticationFilter}</li>
 *  <li>{@link AuthenticationProvider}</li>
 * </ul>
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    /** Фильтр аутентификации на основе JWT-токена */
    private final JwtAuthenticationFilter jwtAuthFilter;

    /** Компонент, отвечающий за авторизацию и аутентификацию пользователей */
    private final AuthenticationProvider authenticationProvider;

    /**
     * Основная цепочка настройки безопасности HTTP.
     * <p>
     * Определяет правила доступа и конфигурирует фильтрацию и менеджмент сессий.
     * </p>
     *
     * @param http объект конфигурации {@link HttpSecurity}
     * @param authenticationConfiguration объект конфигурации аутентификации (не используется явно)
     * @return настроенная цепочка фильтров {@link SecurityFilterChain}
     * @throws Exception в случае ошибок построения цепочки
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationConfiguration authenticationConfiguration) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/*").permitAll()
                        .requestMatchers("/", "/v3/api-docs/", "/swagger-ui.html", "/swagger-ui/").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
