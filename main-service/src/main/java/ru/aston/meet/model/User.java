package ru.aston.meet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/*
 * Класс, представляющий пользователя в системе.
 * Реализует интерфейс UserDetails для интеграции с Spring Security.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "app_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Электронная почта пользователя.
     * Должна быть уникальной.
     */
    private String email;

    /*
     * Имя пользователя.
     */
    private String name;

    /*
     * Пароль пользователя.
     */
    private String password;

//    /*
//     * Статус пользователя.
//     * Например, может обозначать активность или неактивность.
//     */
//    private String status;

//    /*
//     * Статус блокировки пользователя.
//     * Указывает, заблокирован ли пользователь.
//     */
//    private Boolean blocked = false;

    /*
     * Роль пользователя в системе.
     * Значение по умолчанию: USER.
     */
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPassword() {
        return password;
    }
}

