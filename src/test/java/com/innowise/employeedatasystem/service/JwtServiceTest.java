package com.innowise.employeedatasystem.service;

import com.innowise.employeedatasystem.entity.User;
import com.innowise.employeedatasystem.exception.InvalidTokenException;
import com.innowise.employeedatasystem.security.ApplicationUserDetails;
import com.innowise.employeedatasystem.serviceimpl.JwtServiceImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoSession;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.event.annotation.AfterTestMethod;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Date;
import java.util.HashMap;
import java.util.function.Supplier;

import static com.innowise.employeedatasystem.util.GeneralConstant.Feature.JWT_TOKEN_SECRET_KEY;
import static com.innowise.employeedatasystem.util.GeneralConstant.Feature.JWT_TOKEN_VALIDITY;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private JwtServiceImpl jwtServiceImpl;

    private MockitoSession mockitoSession; // to create mockito session (to init all mocks before each method, like restore cache etc... To control some other working aspects).

    @BeforeTestMethod
    public void initSession() {
        mockitoSession = Mockito.mockitoSession()
                .initMocks(this)
                .startMocking();
    }

    @AfterTestMethod
    public void destroySession() {
        mockitoSession.finishMocking();
    }

    @Test
    @DisplayName(value = "Context loads")
    void contextLoadsTest() {
        Assertions.assertNotNull(jwtService);
        Assertions.assertNotNull(jwtServiceImpl);
    }

    @Test
    @DisplayName(value = "Generate token")
    void generateToken() {
        User user = User.builder().username("user").build();

        String token = jwtServiceImpl.generateToken(new ApplicationUserDetails(user));
        Assertions.assertNotNull(token);
    }

    @Test
    @DisplayName(value = "Extract username")
    void extractUsernameTest() {

        User user = User.builder().username("user").build();

        String token = jwtServiceImpl.generateToken(new ApplicationUserDetails(user));

        try {
            String username = jwtServiceImpl.extractUsername(token);
            Assertions.assertEquals(username, user.getUsername());
        } catch (InvalidTokenException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName(value = "Extract expire date")
    void extractExpireDateTest() {

        String token = jwtServiceImpl.generateToken(new ApplicationUserDetails(new User()));

        try {
            Date expireDate = jwtServiceImpl.extractExpiration(token);
            Assertions.assertNotNull(expireDate);

            Assertions.assertTrue(expireDate.after(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY - 1000 * 5)));  // 5 seconds diff approximately

            Assertions.assertTrue(expireDate.before(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY)));

        } catch (InvalidTokenException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName(value = "Token is valid 'VALID'")
    void validateTokenSuccessTest() {

        User user = User.builder().username("user").build();
        UserDetails userDetails = new ApplicationUserDetails(user);
        String token = jwtServiceImpl.generateToken(userDetails);

        try {
            boolean isValid = jwtServiceImpl.isJwtTokenValid(token, userDetails);
            Assertions.assertTrue(isValid);
        } catch (InvalidTokenException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName(value = "Token is valid 'INVALID', incorrect username")
    void validateTokenFailedUsernameTest() {

        User user = User.builder().username("user").build();
        UserDetails userDetails = new ApplicationUserDetails(user);

        String token = jwtServiceImpl.generateToken(userDetails);

        try {
            boolean isValid = jwtServiceImpl.isJwtTokenValid(token, new ApplicationUserDetails(new User())); // incorrect username
            Assertions.assertFalse(isValid);
        } catch (InvalidTokenException e) {
            System.err.println(e.getMessage());
        }
    }


    @Test
    @DisplayName(value = "Token is valid 'INVALID', token expired")
    void validateTokenFailedExpirationTest() {

        User user = User.builder().username("user").build();
        UserDetails userDetails = new ApplicationUserDetails(user);

        String token = getExpiredToken.get();

        Exception exception = Assertions.assertThrows(InvalidTokenException.class,
                () -> jwtServiceImpl.isJwtTokenValid(token, userDetails)); // token expired

        org.assertj.core.api.Assertions.assertThat(exception.getMessage()).contains("JWT expired at");
    }

    @Test
    @DisplayName(value = "Token is valid 'INVALID', token is null/empty")
    void validateTokenIsNullTest() {

        User user = User.builder().username("user").build();
        UserDetails userDetails = new ApplicationUserDetails(user);

        Exception exception = Assertions.assertThrows(InvalidTokenException.class,
                () -> jwtServiceImpl.isJwtTokenValid(null, userDetails));

        org.assertj.core.api.Assertions.assertThat(exception.getMessage()).contains("JWT String argument");
    }

    @Test
    @DisplayName(value = "Token is valid 'INVALID', malformed exception")
    void validateTokenIsMalformedTest() {

        User user = User.builder().username("user").build();
        UserDetails userDetails = new ApplicationUserDetails(user);

        String token = jwtServiceImpl.generateToken(userDetails);

        Exception exception = Assertions.assertThrows(InvalidTokenException.class,
                () -> jwtServiceImpl.isJwtTokenValid(token.substring(2), userDetails));

        org.assertj.core.api.Assertions.assertThat(exception.getMessage()).contains("Malformed");
    }

    @Test
    @DisplayName(value = "Token is valid 'INVALID', signature exception")
    void validateTokenSignatureErrorTest() {

        User user = User.builder().username("user").build();
        UserDetails userDetails = new ApplicationUserDetails(user);

        String token = jwtServiceImpl.generateToken(userDetails);

        Exception exception = Assertions.assertThrows(InvalidTokenException.class,
                () -> jwtServiceImpl.isJwtTokenValid(token.substring(0, token.length() - 2), userDetails));

        org.assertj.core.api.Assertions.assertThat(exception.getMessage()).contains("JWT signature");
    }

    @Test
    @DisplayName(value = "Token is valid 'INVALID', UnsupportedJwt exception")
    void validateTokenSTest() {

        User user = User.builder().username("user").build();
        UserDetails userDetails = new ApplicationUserDetails(user);

        Exception exception = Assertions.assertThrows(InvalidTokenException.class,
                () -> jwtServiceImpl.isJwtTokenValid(JWT_TOKEN_SECRET_KEY, userDetails));

        org.assertj.core.api.Assertions.assertThat(exception.getMessage()).contains("JWT strings must");
    }

    @Test
    @DisplayName(value = "Expired token")
    void isTokenExpired() {

        User user = User.builder().username("user").build();
        UserDetails userDetails = new ApplicationUserDetails(user);

        String token = jwtServiceImpl.generateToken(userDetails);
        String expiredToken = getExpiredToken.get();

        try {
            boolean isExpired = jwtServiceImpl.isTokenExpired(token);
            Assertions.assertFalse(isExpired);

            Assertions.assertThrows(InvalidTokenException.class,
                    () -> Assertions.assertTrue(jwtServiceImpl.isTokenExpired(expiredToken)));

        } catch (InvalidTokenException e) {
            throw new RuntimeException(e);
        }
    }

    private static final Supplier<String> getExpiredToken = () -> Jwts.builder().
            setClaims(new HashMap<>())
            .setSubject(User.builder()
                    .username("user")
                    .build().getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis() + 1000))
            .setExpiration(new Date(System.currentTimeMillis()))   // expire it after 1 second
            .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_TOKEN_SECRET_KEY)),
                    SignatureAlgorithm.HS256)
            .compact();
}
