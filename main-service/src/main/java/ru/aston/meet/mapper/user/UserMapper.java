package ru.aston.meet.mapper.user;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.aston.meet.dto.auth.RegisterRequest;
import ru.aston.meet.dto.user.UserDto;
import ru.aston.meet.mapper.impl.user.UserMapperDecorator;
import ru.aston.meet.model.user.User;

/**
 * Mapper для преобразования объектов {@link User} и {@link UserDto}.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@DecoratedWith(UserMapperDecorator.class)
public interface UserMapper {

    /**
     * Преобразует объект {@link RegisterRequest} в {@link User}.
     * Поле password игнорируется, а роль устанавливается в "USER".
     *
     * @param registerRequest объект, содержащий данные для регистрации пользователя
     * @return преобразованный объект {@link User}
     */
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", constant = "USER")
    @Mapping(source = "registerRequest.email", target = "email")
    @Mapping(source = "registerRequest.name", target = "name")
    User toUser(RegisterRequest registerRequest);

    /**
     * Преобразует объект {@link User} в {@link UserDto}.
     *
     * @param user объект пользователя, который нужно преобразовать
     * @return преобразованный объект {@link UserDto}
     */
    @Mapping(source = "user.id", target = "id")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.name", target = "name")
    @Mapping(source = "user.role", target = "role")
    UserDto toUserDto(User user);
}