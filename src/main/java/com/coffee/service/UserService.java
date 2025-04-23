package com.coffee.service;

import com.coffee.Exception.DuplicateException;
import com.coffee.Exception.NotEqualException;
import com.coffee.config.Constants;
import com.coffee.domain.User;
import com.coffee.dto.CustomUserDetails;
import com.coffee.dto.UserForm;
import com.coffee.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @PostConstruct
    protected void initialize() {
        createAdminUser(); // 애플리케이션 시작 시 기본 관리자 계정을 생성하는 로직 호출
    }

    public void save(UserForm form) {

        if (!form.getPassword().equals(form.getConfirmPassword())) {
            throw new NotEqualException("비밀번호가 일치하지 않습니다.");
        }

        User user = User.builder()
                .name(form.getUsername())
                .password(passwordEncoder.encode(form.getPassword()))
                .role("USER")
                .build();

        Optional<User> memberOptional = userRepository.findByName(user.getName());

        if (memberOptional.isEmpty()) {
            userRepository.save(user);
        } else {
            throw new DuplicateException("이미 존재하는 사용자 ID입니다. 다른 ID를 사용해주세요!");
        }
    }

    private void createAdminUser() {
        User admin = User.builder()
                .name(Constants.ADMIN_USERNAME)
                .password(passwordEncoder.encode(Constants.ADMIN_PASSWORD))
                .role("ADMIN")
                .build();

        userRepository.save(admin);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByName(username);
        User findUser = userOptional.orElseThrow(() -> new UsernameNotFoundException("username " + username + " not found"));
        return new CustomUserDetails(findUser);
    }
}
