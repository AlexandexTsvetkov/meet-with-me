package ru.aston.meet.mapper.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.aston.meet.dto.auth.RegisterRequest;
import ru.aston.meet.dto.user.UserDto;
import ru.aston.meet.mapper.user.UserMapper;
import ru.aston.meet.model.user.User;

import javax.annotation.processing.Generated;

@Generated(value = "org.mapstruct.ap.MappingProcessor")
public abstract class UserMapperDecorator implements UserMapper {

    @Autowired
    private UserMapper delegate;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User toUser(RegisterRequest registerRequest) {
        User user = delegate.toUser(registerRequest);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        return user;
    }

    @Override
    public UserDto toUserDto(User user) {
        return delegate.toUserDto(user);
    }
}
