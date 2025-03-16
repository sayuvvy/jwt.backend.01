package com.booksample.site.servcices;

import com.booksample.site.controllers.RegisterRequest;
import com.booksample.site.model.*;
import com.booksample.site.repository.RoleRepository;
import com.booksample.site.repository.TokenRepository;
import com.booksample.site.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

    public void register(RegisterRequest request) throws MessagingException {
        var userrole = roleRepository.findByName("USER")
                .orElseThrow(()->new IllegalStateException("role USER was not initialized"));
        var user = User.builder()
                .email(request.getEmail())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userrole))
                .build();
        userRepository.save(user);
        sendValidationEmail(user);
    }
    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);
        emailService.sendEmail(
                user.getEmail(),
                user.fullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account Activation"
        );
    }
    private String generateAndSaveActivationToken(User user) {
        int generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(String.valueOf(generatedToken))
                .tokenType(TokenType.ACTIVATION)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(30))
                .user(user)
                .build();
        tokenRepository.save(token);
        return String.valueOf(generatedToken);
    }
    private int generateActivationCode(int noOfDigits) {
        return new SecureRandom()
                .ints((int)Math.pow(10, noOfDigits-1), (int)Math.pow(10, noOfDigits))
                .findFirst()
                .getAsInt();
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = (User)auth.getPrincipal();
        revokeAllUserTokens(user);
        var claims = new HashMap<String, Object>();
        claims.put("fullName", user.fullName());
        var jwtToken = jwtService.generateToken(claims, user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }
    private void saveUserToken(User savedUser, String jwtToken) {
        var token = Token.builder()
                .user(savedUser)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }
    private void revokeAllUserTokens(User user) {
        var validTokens = tokenRepository.findAllValidtokensByUser(user.getId());
        if (validTokens.isEmpty())
            return;
        validTokens.forEach(token->{
            token.setRevoked(true);
            token.setExpired(true);
        });
        tokenRepository.saveAll(validTokens);
    }
    //@Transactional
    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(()-> new RuntimeException("Invalid Token"));
        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Activation token has expired. New Activation token issued");
        }
        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
        user.setEnabled(true);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }
}