package com.ssafy.polaris.user.service;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.ssafy.polaris.global.exception.exceptions.UserNotAuthorizedException;
import com.ssafy.polaris.global.exception.exceptions.UserNotFoundException;
import com.ssafy.polaris.user.exception.WrongEmailOrPasswordException;
import com.ssafy.polaris.global.exception.exceptions.WrongPasswordException;
import com.ssafy.polaris.global.security.SecurityUser;
import com.ssafy.polaris.global.security.provider.JwtTokenProvider;
import com.ssafy.polaris.regcode.domain.Regcode;
import com.ssafy.polaris.regcode.repository.RegcodeRepository;
import com.ssafy.polaris.user.domain.User;
import com.ssafy.polaris.user.dto.UserJoinRequestDto;
import com.ssafy.polaris.user.dto.UserLoginRequestDto;
import com.ssafy.polaris.user.dto.UserResponseDto;
import com.ssafy.polaris.user.dto.UserSetPasswordDto;
import com.ssafy.polaris.user.exception.UserConflictException;
import com.ssafy.polaris.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final RegcodeRepository regcodeRepository;

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate redisTemplate;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(""));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
            .orElseThrow(() -> new UserNotFoundException(""));
    }

    @Override
    public User getUserByNickname(String nickname) {
        return userRepository.findUserByNickname(nickname)
            .orElseThrow(() -> new UserNotFoundException(""));
    }

    @Override
    @Transactional
    public UserResponseDto join(UserJoinRequestDto userJoinRequestDto) {
        boolean isEmailInUse = emailCheck(userJoinRequestDto.getEmail());
        boolean isNicknameInUse = emailCheck(userJoinRequestDto.getEmail());

        if (isEmailInUse || isNicknameInUse)
            throw new UserConflictException("");
        String encodedPassword = passwordEncoder.encode(userJoinRequestDto.getPassword());
        userJoinRequestDto.setPassword("");

        Regcode regcode = regcodeRepository.getReferenceById(userJoinRequestDto.getRegion());

        User user = User.builder()
            .email(userJoinRequestDto.getEmail())
            .password(encodedPassword)
            .nickname(userJoinRequestDto.getNickname())
            .regcode(regcode)
            .regcodeId(regcode.getId())
            .build();

        user = userRepository.save(user);
        return new UserResponseDto(user);
    }

    @Override
    public Boolean emailCheck(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Boolean nicknameCheck(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    @Override
    @Transactional
    public void resignation(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(""));
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public void setPassword(Long id, String password) {
        User user = userRepository.findById(id)
            .orElseThrow();
        user.setPassword(passwordEncoder.encode(password));
    }

    @Override
    public Map<String, String> login(UserLoginRequestDto userLoginRequestDto) {
        // 1. authentication token을 만들어준다 인증 전에는 auth여부가 false, 완료되면 true가 된 객체를 반환할 수 있도록한다.
        // 2. 실제 검증 : db에 저장된 id, 비번과 같으냐?? -> 검증성공시 실제 auth여부가 true인 Authentication 객체 반환

        User user = userRepository.findUserByEmail(userLoginRequestDto.getEmail())
            .orElseThrow(() -> new WrongEmailOrPasswordException("UserServiceImpl"));
        if (!passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword())) {
			throw new WrongPasswordException("UserServiceImpl");
        }

        // TODO : 사용자 권한 설정
        Authentication authentication =
            new UsernamePasswordAuthenticationToken(userLoginRequestDto.getEmail(), userLoginRequestDto.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("AUTHORITY")));

        Map<String, String> tokenMap = jwtTokenProvider.generateToken(user.getId(), user.getNickname(), authentication);

        tokenMap.put("id", Long.toString(user.getId()));
        tokenMap.put("email", user.getEmail());
        tokenMap.put("profileUrl", user.getProfileUrl());
        tokenMap.put("nickname", user.getNickname());

        // redis에 저장
        redisTemplate.opsForValue().set("refresh:"+user.getEmail(), tokenMap.get("refresh"), jwtTokenProvider.getREFRESH_TOKEN_EXPIRE_TIME(), TimeUnit.MILLISECONDS);

        return tokenMap;
    }

    @Override
    public void passwordCorrectionCheck(UserSetPasswordDto passwords, SecurityUser securityUser) {
        if (!passwordEncoder.matches(passwords.getOldPassword(), securityUser.getPassword()))
            throw new WrongPasswordException("UserServiceImpl");
    }

    @Override
    @Transactional
    public void logout(String accessToken, SecurityUser securityUser) {
        if (accessToken == null) {
            throw new UserNotAuthorizedException("logout");
        }

        if (!jwtTokenProvider.validateToken(accessToken)) {
            throw new UserNotAuthorizedException("logout");
        }

        redisTemplate.delete("refresh:"+securityUser.getEmail());
        redisTemplate.opsForValue().set("blackList:"+accessToken, "logout", jwtTokenProvider.getACCESS_TOKEN_EXPIRE_TIME());
    }

    @Override
    public Map<String, String> reissue(String refreshToken, String email) {
        User user = userRepository.findUserByEmail(email)
            .orElseThrow(() -> new UserNotFoundException("reissuance"));

        Authentication authentication =
            new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("AUTHORITY")));

        Map<String, String> tokenMap = jwtTokenProvider.generateToken(user.getId(), user.getNickname(), authentication);

        tokenMap.put("id", Long.toString(user.getId()));
        tokenMap.put("email", user.getEmail());
        tokenMap.put("profileUrl", user.getProfileUrl());
        tokenMap.put("nickname", user.getNickname());

        // redis에 저장
        redisTemplate.opsForValue().set("refresh:"+user.getEmail(), tokenMap.get("refresh"), jwtTokenProvider.getREFRESH_TOKEN_EXPIRE_TIME(), TimeUnit.MILLISECONDS);

        return tokenMap;
    }

}
