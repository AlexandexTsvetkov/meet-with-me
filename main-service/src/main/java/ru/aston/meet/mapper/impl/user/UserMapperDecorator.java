package ru.aston.meet.mapper.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.aston.meet.dto.auth.RegisterRequest;
import ru.aston.meet.dto.user.UserDto;
import ru.aston.meet.mapper.user.UserMapper;
import ru.aston.meet.model.user.User;

import javax.annotation.processing.Generated;

/**
 * Декоратор для {@link UserMapper}, который добавляет дополнительную логику преобразования.
 */
@Generated(value = "org.mapstruct.ap.MappingProcessor")
public abstract class UserMapperDecorator implements UserMapper {

    @Autowired
    private UserMapper delegate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Преобразует объект {@link RegisterRequest} в {@link User} и шифрует пароль.
     *
     * @param registerRequest объект, содержащий данные для регистрации пользователя
     * @return преобразованный объект {@link User} с зашифрованным паролем
     */
    @Override
    public User toUser(RegisterRequest registerRequest) {
        User user = delegate.toUser(registerRequest);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        return user;
    }

    /**
     * Преобразует объект {@link User} в {@link UserDto}.
     *
     * @param user объект пользователя, который нужно преобразовать
     * @return преобразованный объект {@link UserDto}
     */
    @Override
    public UserDto toUserDto(User user) {
        return delegate.toUserDto(user);
    }
}