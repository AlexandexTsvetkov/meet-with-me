package ru.aston.meet.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aston.meet.model.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Возвращает пользователя по email
     */
    Optional<User> findByEmail(String email);
}
