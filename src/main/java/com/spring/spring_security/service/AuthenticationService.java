package com.spring.spring_security.service;

import com.spring.spring_security.dto.AuthenticationRequest;
import com.spring.spring_security.dto.AuthenticationResponse;
import com.spring.spring_security.model.User;
import com.spring.spring_security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    //proceso de login
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    public AuthenticationResponse login(AuthenticationRequest authRequest) {

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(), authRequest.getPassword()
        );

        authenticationManager.authenticate(authToken);

        User user = userRepository.findByUsername(authRequest.getUsername())
                        .orElseThrow(() -> new RuntimeException("User not found"));

        //en el caso de que estyoy seguro de qsurgir una excepcion no se ejcutara
        //User user = userRepository.findByUsername(authRequest.getUsername()).get();

        //se manda el objeto y los claims
        String jwt = jwtService.generateToken(user, generateExtraClaims(user));

        //claims data en https://jwt.io/

        return new AuthenticationResponse(jwt);
    }

    private Map<String, Object> generateExtraClaims(User user) {

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getName()); //claim
        extraClaims.put("role", user.getRole().name()); //claim
        extraClaims.put("permissions", user.getAuthorities()); //EXTRA

        return extraClaims;
    }


}
