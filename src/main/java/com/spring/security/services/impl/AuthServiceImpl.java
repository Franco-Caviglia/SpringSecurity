package com.spring.security.services.impl;

import com.spring.security.persistence.entities.User;
import com.spring.security.persistence.repositories.UserRepository;
import com.spring.security.services.OAuthService;
import com.spring.security.services.OJWTUtilityService;
import com.spring.security.services.models.dtos.LoginDTO;
import com.spring.security.services.models.dtos.ResponseDTO;
import com.spring.security.services.models.validation.UserValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements OAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OJWTUtilityService jwtUtilityService;

    @Autowired
    private UserValidation userValidation;


    public HashMap<String, String> login(LoginDTO loginDTO) throws
            Exception {
        try{
            HashMap<String, String> jwt = new HashMap<>();

            Optional<User> user = userRepository.findByEmail(loginDTO.getEmail());

            if(user.isEmpty()){
                jwt.put("error", "User not registered");
                return jwt;
            }

            //Verificar pass;
            if(verifyPassword(loginDTO.getPassword(), user.get().getPassword())){
                jwt.put("jwt", jwtUtilityService.generateJWT(user.get().getId()));
            } else {
                jwt.put("error", "Authentication failed");
            }

            return jwt;

        } catch (Exception e){
            throw new Exception(e.toString());
        }
    }

    public ResponseDTO register(User user) throws Exception {

        try {
            ResponseDTO responseDTO = userValidation.validate(user);

            if(responseDTO.getNumOfErrors() > 0){
                return responseDTO;
            }

            List<User> getAllUsers = userRepository.findAll();

            for(User repeatFields: getAllUsers){
                if(repeatFields != null) {
                    responseDTO.setNumOfErrors(1);
                    responseDTO.setMessage("User already exists!");
                    return responseDTO;
                }
            }

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
            user.setPassword(encoder.encode(user.getPassword()));

            userRepository.save(user);
            responseDTO.setMessage("User created successfully!");

            return responseDTO;

        } catch (Exception e){
            throw new Exception(e.toString());
        }

    }

    private boolean verifyPassword(String enteredPass, String storagePass){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.matches(enteredPass, storagePass);
    }
}
