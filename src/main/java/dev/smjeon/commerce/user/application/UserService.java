package dev.smjeon.commerce.user.application;

import dev.smjeon.commerce.security.UserContext;
import dev.smjeon.commerce.user.domain.Email;
import dev.smjeon.commerce.user.domain.User;
import dev.smjeon.commerce.user.dto.UserResponseDto;
import dev.smjeon.commerce.user.exception.NotFoundUserException;
import dev.smjeon.commerce.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<UserResponseDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserResponseDto.class))
                .collect(Collectors.toList());
    }

    public UserContext findByEmail(Email email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundUserException(email.getEmail()));

        return modelMapper.map(user, UserContext.class);
    }
}
