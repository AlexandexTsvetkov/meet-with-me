package ru.aston.meet.service.impl.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.aston.meet.dto.user.UserDto;
import ru.aston.meet.exception.NotFoundException;
import ru.aston.meet.mapper.user.UserMapper;
import ru.aston.meet.model.user.User;
import ru.aston.meet.repository.user.UserRepository;
import ru.aston.meet.service.user.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl  implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto getUserInfo(String email) {

        Optional<User> user = userRepository.findByEmail(email);

        if(user.isPresent()) {
            return userMapper.toUserDto(user.get());
        } else {
            throw new NotFoundException("User not found, email: " + email);
        }
    }
}
