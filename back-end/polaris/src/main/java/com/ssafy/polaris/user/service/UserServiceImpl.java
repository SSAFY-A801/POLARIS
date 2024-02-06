package com.ssafy.polaris.user.service;

import java.util.Collections;
import java.util.Map;

import com.ssafy.polaris.security.SecurityUser;
import com.ssafy.polaris.security.provider.JwtTokenProvider;
import com.ssafy.polaris.user.domain.User;
import com.ssafy.polaris.user.dto.UserLoginRequestDto;
import com.ssafy.polaris.user.repository.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
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
    private final EntityManager em;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    @Override
    public User getUserById(Long userId) {
        return userRepository.getReferenceById(userId);
    }

    @Override
    public User getUserByEmail(String email) throws Exception {
        // TODO: 에러 정의
        return userRepository.findUserByEmail(email)
            .orElseThrow(() -> new Exception());
    }

    @Override
    public User getUserByNickname(String nickname) throws Exception {
        // TODO: 에러 정의
        return userRepository.findUserByNickname(nickname)
            .orElseThrow(() -> new Exception());
    }

    @Override
    @Transactional
    public void join(User user) {
        userRepository.save(user);
    }

    @Override
    public Boolean emailCheck(String email) {
        try {
            getUserByEmail(email);
            return true;
            // TODO: 2개의 값이 반환될 때도 exception을 반환하긴 한다.
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean nicknameCheck(String nickname) {
        try {
            getUserByNickname(nickname);
            return true;
            // TODO: 2개의 값이 반환될 때도 exception을 반환하긴 한다.
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional
    public void resignation(Long id) throws Exception {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void setPassword(Long id, String password) {
        User user = em.find(User.class, id);
        user.setPassword(passwordEncoder.encode(password));
    }

    @Override
    public Map<String, String> login(UserLoginRequestDto userLoginRequestDto) throws Exception {
        // 1. authentication token을 만들어준다 인증 전에는 auth여부가 false, 완료되면 true가 된 객체를 반환할 수 있도록한다.
        // 2. 실제 검증 : db에 저장된 id, 비번과 같으냐?? -> 검증성공시 실제 auth여부가 true인 Authentication 객체 반환

        // TODO: 에러 정의하기
        User user = userRepository.findUserByEmail(userLoginRequestDto.getEmail())
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

        tokenMap.put("id", Long.toString(user.getId().longValue()));
        tokenMap.put("email", user.getEmail());
        tokenMap.put("profileUrl", user.getProfileUrl());
        tokenMap.put("nickname", user.getNickname());

        // TODO: Redis에 넣어주기

        return tokenMap;
    }


}