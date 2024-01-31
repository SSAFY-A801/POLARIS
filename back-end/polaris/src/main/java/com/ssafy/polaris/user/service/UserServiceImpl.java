package com.ssafy.polaris.user.service;

import java.util.Collections;
import java.util.Map;

import com.ssafy.polaris.security.provider.JwtTokenProvider;
import com.ssafy.polaris.user.domain.User;
import com.ssafy.polaris.user.dto.UserLoginRequestDto;
import com.ssafy.polaris.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    @Override
    public User getUserById(Long userId) {
        return userRepository.getReferenceById(userId);
    }

    @Override
    public User getUserByEmail(String email) throws Exception {
        // TODO: 에러 정의
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new Exception());
    }

    @Override
    @Transactional
    public void join(User user) {
        userRepository.save(user);
    }

    @Override
    public Map<String, String> login(UserLoginRequestDto userLoginRequestDto) throws Exception {
        // 1. authentication token을 만들어준다 인증 전에는 auth여부가 false, 완료되면 true가 된 객체를 반환할 수 있도록한다.
        // 2. 실제 검증 : db에 저장된 id, 비번과 같으냐?? -> 검증성공시 실제 auth여부가 true인 Authentication 객체 반환

        // TODO: 에러 정의하기
        User user = userRepository.findByEmail(userLoginRequestDto.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        if (!passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // TODO: 저장 new로 하고 읽어올때도 new? 자세히 보고 고치자
        Authentication authentication =
            new UsernamePasswordAuthenticationToken(userLoginRequestDto.getEmail(), userLoginRequestDto.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("AUTHORITY")));

        // TODO: 여기에 DTO가 들어가면 안되는가?? Long으로 만든 id를 Authentication은 가지지 않는다...!!
        Map<String, String> tokenMap = jwtTokenProvider.generateToken(user.getId(), user.getNickname(), authentication);

        // TODO: Redis에 넣어주기

        return tokenMap;
    }
}
