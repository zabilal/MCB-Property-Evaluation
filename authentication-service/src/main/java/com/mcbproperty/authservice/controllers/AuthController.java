package com.mcbproperty.authservice.controllers;

import com.mcbproperty.authservice.pojo.request.AuthenticationRequest;
import com.mcbproperty.authservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;


@AllArgsConstructor
@RestController
public class AuthController {

    private UserService userService;
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn (@RequestBody @Valid AuthenticationRequest request) {
        return new ResponseEntity<>(userService.login(request.getUsername(), request.getPassword()), HttpStatus.OK);
    }

//    @PutMapping("/change-password")
//    public ResponseEntity<?> updatePassword (Principal principal, @RequestBody ChangePasswordRequest request) {
//        Optional<User> applicationUser = userRepository.findByEmailAndActiveAndDeleted(principal.getName(), Boolean.TRUE, Boolean.FALSE);
//        if (applicationUser.isPresent()) {
//            try {
//                Authentication authentication = authenticationManager.authenticate(
//                        new UsernamePasswordAuthenticationToken(principal.getName(), request.getOldPassword())
//                );
//                if (request.getNewPassword().equals(request.getConfirmPassword())) {
//                    ApplicationUser user = applicationUser.get();
//                    user.setPassword(passwordEncoder.encode(request.getNewPassword()));
//                    return new ResponseEntity<>("Password reset successful", HttpStatus.OK);
//                } else {
//                    return new ResponseEntity<>("Password do not match", HttpStatus.OK);
//                }
//            } catch (BadCredentialsException exception) {
//                return new ResponseEntity<>(exception.toString(), HttpStatus.OK);
//            }
//        } else {
//            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
//        }
//    }
}
