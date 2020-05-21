package dev.smjeon.commerce.user.application;

import dev.smjeon.commerce.security.UserContext;
import dev.smjeon.commerce.user.domain.Email;
import dev.smjeon.commerce.user.domain.User;
import dev.smjeon.commerce.user.domain.UserRole;
import dev.smjeon.commerce.user.dto.UserLoginRequest;
import dev.smjeon.commerce.user.dto.UserResponse;
import dev.smjeon.commerce.user.dto.UserSignUpRequest;
import dev.smjeon.commerce.user.exception.DuplicatedEmailException;
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

    public List<UserResponse> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .collect(Collectors.toList());
    }

    public UserContext findByEmail(Email email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundUserException(email.getEmail()));

        return modelMapper.map(user, UserContext.class);
    }

    public UserResponse save(UserSignUpRequest userSignUpRequest) {
        checkDuplicatedEmail(userSignUpRequest);
        User user = new User(userSignUpRequest.getEmail(), userSignUpRequest.getPassword(),
                userSignUpRequest.getUserName(), userSignUpRequest.getNickName(), UserRole.BUYER);

        userRepository.save(user);

        return modelMapper.map(user, UserResponse.class);
    }

    private void checkDuplicatedEmail(UserSignUpRequest userSignUpRequest) {
        if (userRepository.existsByEmail(userSignUpRequest.getEmail())) {
            throw new DuplicatedEmailException(userSignUpRequest.getEmail());
        }
    }

    public UserResponse login(UserLoginRequest userLoginRequest) {
        User user =
                userRepository.findByEmail(userLoginRequest.getEmail())
                        .orElseThrow(() -> new NotFoundUserException(userLoginRequest.getEmail().getEmail()));

        return modelMapper.map(user, UserResponse.class);
    }
}
