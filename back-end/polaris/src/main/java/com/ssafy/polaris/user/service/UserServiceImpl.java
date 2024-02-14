package com.ssafy.polaris.user.service;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.ssafy.polaris.global.exception.exceptions.category.UnAuthorizedException;
import com.ssafy.polaris.user.api.KakaoApi;
import com.ssafy.polaris.user.dto.KakaoProfile;
import com.ssafy.polaris.user.dto.OAuthToken;
import com.ssafy.polaris.user.dto.UserKakaoJoinRequestDto;
import com.ssafy.polaris.user.exception.CertCodeExpiredException;
import com.ssafy.polaris.user.exception.CertCodeNotMatch;
import com.ssafy.polaris.user.exception.UserKakaoJoined;
import com.ssafy.polaris.user.exception.UserNotAuthorizedException;
import com.ssafy.polaris.user.exception.UserNotFoundException;
import com.ssafy.polaris.user.exception.UserNotKakaoJoined;
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

import jakarta.servlet.http.HttpSession;
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
    private final KakaoApi kakaoApi;

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
        boolean isNicknameInUse = nicknameCheck(userJoinRequestDto.getNickname());

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

    /**
     * nickname과 region과 code만 받는다.
     * @throws UserConflictException 사용중인 이메일, 닉네임 입력이 들어올 때
     * @param userKakaoJoinRequestDto
     */
    @Override
    @Transactional
    public UserResponseDto kakaoJoin(UserKakaoJoinRequestDto userKakaoJoinRequestDto) {
        boolean isEmailInUse = emailCheck(userKakaoJoinRequestDto.getEmail());
        boolean isNicknameInUse = nicknameCheck(userKakaoJoinRequestDto.getNickname());

        if (isEmailInUse || isNicknameInUse)
            throw new UserConflictException("");

        Regcode regcode = regcodeRepository.getReferenceById(userKakaoJoinRequestDto.getRegion());

        User user = User.builder()
            .email(userKakaoJoinRequestDto.getEmail())
            .nickname(userKakaoJoinRequestDto.getNickname())
            .regcode(regcode)
            .regcodeId(regcode.getId())
            .oauth(String.valueOf(userKakaoJoinRequestDto.getKakaoId()))
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
            .orElseThrow(() -> new UserNotFoundException("UserServiceImpl::setPassword"));
        user.setPassword(passwordEncoder.encode(password));
    }

    @Override
    public Map<String, String> kakaoLogin(KakaoProfile kakaoProfile) {
        User user = userRepository.findUserByOauth(String.valueOf(kakaoProfile.getId()))
            .orElseThrow(() -> new UserNotKakaoJoined(""));

        kakaoProfile.setNickname(user.getNickname());

        // TODO : 사용자 권한 설정
        Authentication authentication =
            new UsernamePasswordAuthenticationToken(kakaoProfile.getEmail(), "",
                Collections.singleton(new SimpleGrantedAuthority("AUTHORITY")));

        Map<String, String> tokenMap = jwtTokenProvider.generateToken(user.getId(), user.getNickname(), authentication);

        tokenMap.put("id", Long.toString(user.getId()));
        tokenMap.put("email", kakaoProfile.getEmail());
        tokenMap.put("profileUrl", user.getProfileUrl());
        tokenMap.put("nickname", user.getNickname());

        redisTemplate.opsForValue().set("refresh:"+user.getEmail(), tokenMap.get("refresh"), jwtTokenProvider.getREFRESH_TOKEN_EXPIRE_TIME(), TimeUnit.MILLISECONDS);

        return tokenMap;
    }

    @Override
    public Map<String, Object> kakaoLoginProcess(String code) {
        // 1. 인가코드 받기
        // 2. 토큰 받기
        OAuthToken oAuthToken = kakaoApi.getOAuthToken(code);

        // 3. 사용자 정보 받기
        KakaoProfile kakaoProfile = kakaoApi.getUserInfo(oAuthToken.getAccess_token());
        return Map.of("kakaoProfile", kakaoProfile,
            "oAuthToken", oAuthToken);
    }

    @Override
    public boolean isKakaoUser(Long kakaoProfileId) {
        return userRepository.existsByOauth(kakaoProfileId.toString());
    }

    @Override
    public User getUserByOauth(Long oauth) {
        return userRepository.findUserByOauth(String.valueOf(oauth))
            .orElseThrow(() -> new UserNotKakaoJoined(""));
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
        if (user.getOauth() != null) {
            throw new UserKakaoJoined("");
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
        redisTemplate.delete("refresh:"+securityUser.getEmail());
        redisTemplate.opsForValue().set("blackList:"+accessToken, "logout", jwtTokenProvider.getACCESS_TOKEN_EXPIRE_TIME(), TimeUnit.MILLISECONDS);
    }

    @Override
    public Map<String, String> reissue(String refreshToken, String email) {
        if (!("Bearer " + refreshToken).equals(redisTemplate.opsForValue().get("refresh:"+email))) {
            System.out.println(refreshToken);
            throw new UserNotAuthorizedException("UserService:reissue:저장된 refresh 토큰과 요청의 토큰이 다릅니다.");
        }

        User user = userRepository.findUserByEmail(email)
            .orElseThrow(() -> new UserNotFoundException("UserService:reissue:해당 이메일을 가진 회원이 없습니다."));

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

    @Override
    public void emilCertification(Map<String, String> body) {
        String code = redisTemplate.opsForValue().get("emailCode:" + body.get("email"));
        if (code == null) { // 토큰 만료 예외
            throw new CertCodeExpiredException("");
        }
        if (!code.equals(body.get("code"))) { // 코드가 다르면 에러
            throw new CertCodeNotMatch("");
        }
    }


}
