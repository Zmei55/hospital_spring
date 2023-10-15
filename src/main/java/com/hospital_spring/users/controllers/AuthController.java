package com.hospital_spring.users.controllers;

//import com.hospital_spring.shared.dto.ExceptionDto;
import com.hospital_spring.users.controllers.api.AuthApi;
import com.hospital_spring.users.dto.NewUserDto;
import com.hospital_spring.users.dto.UserDto;
import com.hospital_spring.users.repositories.UsersRepository;
import com.hospital_spring.users.services.AuthService;
import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
//@CrossOrigin("http://localhost:3000/")
public class AuthController implements AuthApi {
    private final AuthService authService;
    private final UsersRepository usersRepository;

    @Override
    public ResponseEntity<UserDto> signUp(NewUserDto newUserDto) {
        return ResponseEntity.status(201)
            .body(authService.signUp(newUserDto));

//        if (usersRepository.findByUsername(newUserDto.getUsername()).isPresent()) {
//            return new ResponseEntity<>(new ExceptionDto("User with this username exists", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
//        }

//        try {
//            return ResponseEntity.status(201)
//                .body(authService.signUp(newUserDto));
//        } catch (BadCredentialsException exception) {
//            return new ResponseEntity<>(new ExceptionDto("Incorrect login or password", HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);
//        }
    }
}
