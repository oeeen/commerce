package dev.smjeon.commerce.user.application;

import dev.smjeon.commerce.security.UserContext;
import dev.smjeon.commerce.user.converter.UserConverter;
import dev.smjeon.commerce.user.domain.Email;
import dev.smjeon.commerce.user.domain.NickName;
import dev.smjeon.commerce.user.domain.Password;
import dev.smjeon.commerce.user.domain.User;
import dev.smjeon.commerce.user.domain.UserRole;
import dev.smjeon.commerce.user.domain.UserStatus;
import dev.smjeon.commerce.user.dto.UserLoginRequest;
import dev.smjeon.commerce.user.dto.UserResponse;
import dev.smjeon.commerce.user.dto.UserSignUpRequest;
import dev.smjeon.commerce.user.exception.DuplicatedEmailException;
import dev.smjeon.commerce.user.exception.InvalidPasswordException;
import dev.smjeon.commerce.user.exception.NotFoundUserException;
import dev.smjeon.commerce.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    private void addAdmin() {
        User user = new User(new Email("oeeen3@gmail.com"),
                new Password(passwordEncoder.encode("Aa12345!")),
                "Seongmo",
                new NickName("Martin"),
                UserRole.ADMIN,
                UserStatus.ACTIVE);

        userRepository.save(user);
    }

    public List<UserResponse> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserConverter::toDto)
                .collect(Collectors.toList());
    }

    public UserContext findByEmail(Email email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundUserException(email.getEmail()));

        return modelMapper.map(user, UserContext.class);
    }

    @Transactional
    public UserResponse save(UserSignUpRequest userSignUpRequest) {
        checkDuplicatedEmail(userSignUpRequest);
        String encodedPassword = passwordEncoder.encode(userSignUpRequest.getPassword().getValue());

        User user = new User(userSignUpRequest.getEmail(), new Password(encodedPassword),
                userSignUpRequest.getUserName(), userSignUpRequest.getNickName(), UserRole.BUYER, UserStatus.ACTIVE);

        userRepository.save(user);

        return UserConverter.toDto(user);
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

        if (isCorrectPassword(userLoginRequest.getPassword(), user)) {
            return UserConverter.toDto(user);
        }

        throw new InvalidPasswordException(userLoginRequest.getPassword().getValue());
    }

    public boolean isCorrectPassword(Password password, User user) {
        return passwordEncoder.matches(password.getValue(), user.getPassword().getValue());
    }

    @Transactional
    public void withdraw(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundUserException(userId));
        user.deactivate();
    }

    public boolean isActiveUser(User user) {
        return user.isActive();
    }
}
