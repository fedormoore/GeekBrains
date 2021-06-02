package ru.moore.market.auth.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.moore.market.auth.entities.User;
import ru.moore.market.auth.repositories.UserRepository;
import ru.moore.market.auth.utils.MailSender;
import ru.moore.market.core.exceptions.MessageError;
import ru.moore.market.core.exceptions.ResourceNotFoundException;
import ru.moore.market.core.models.UserPrincipal;
import ru.moore.market.core.services.JwtTokenUtil;
import ru.moore.market.core.services.mappers.MapperUtils;
import ru.moore.market.routing.dtos.SignUpRequestDto;
import ru.moore.market.routing.dtos.AuthResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final MapperUtils mapperUtils;

    private final MailSender mailSender;

    public ResponseEntity<?> signUp(SignUpRequestDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            return new ResponseEntity<>(new MessageError(HttpStatus.UNAUTHORIZED.value(), "Почтовый ящик уже используется!"), HttpStatus.UNAUTHORIZED);
        }
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setActive(false);
        userDto.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(mapperUtils.map(userDto, User.class));

        String message = String.format(
                "Здравствуйте, %s! \n" +
                        "Добро пожаловать на площадьку для трейдеров. Пожалуйста, активируйте вашу учетную запись перейдя по ссылке: http://localhost:8080/api/v1/auth/activate/%s",
                userDto.getName(),
                userDto.getActivationCode()
        );
        mailSender.send(userDto.getEmail(), "Активация учетной записи", message);

        return new ResponseEntity<>(new MessageError(HttpStatus.OK.value(), "Пользователь успешно зарегистрирован!\nПерейти по ссылке, высланной в письме для ативации учетной записи."), HttpStatus.OK);
    }

    public ResponseEntity<?> findByLoginAndPassword(String login, String password) throws ResourceNotFoundException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>(new MessageError(HttpStatus.UNAUTHORIZED.value(), "Неверный логин или пароль!"), HttpStatus.UNAUTHORIZED);
        }

        User user = userRepository.findByLogin(login).get();

        if (!user.isActive()) {
            throw new ResourceNotFoundException("Учетная запись не активирована!");
        }

                UserPrincipal userPrincipal = UserPrincipal.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();

        String token = jwtTokenUtil.generateToken(userPrincipal);
        return ResponseEntity.ok(new AuthResponseDto(token));
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        List<GrantedAuthority> authorities = new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), authorities);
    }

    private Optional<User> findByUsername(String login) {
        return userRepository.findByLogin(login);
    }

    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActivationCode(null);
        user.setActive(true);

        userRepository.save(user);

        return true;
    }
}