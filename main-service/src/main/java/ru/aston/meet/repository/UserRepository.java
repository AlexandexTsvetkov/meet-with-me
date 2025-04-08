package ru.aston.meet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aston.meet.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
