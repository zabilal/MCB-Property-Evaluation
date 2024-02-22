//package com.gloomme.authservice.users;
//
//import com.gloomme.authservice.pojo.request.RegisterUserAccountDTO;
//import com.gloomme.authservice.pojo.response.UserDTO;
//import com.gloomme.authservice.service.UserService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import static org.hamcrest.Matchers.equalTo;
//import static org.junit.Assert.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class RegisterRestControllerTest {
//
//    @Autowired
//    TestRestTemplate restTemplate;
//
//    @Autowired
//    UserService userService;
//
//    @Test
//    public void test_createNewUserAccount() {
//        // create a new user using the quick account endpoint
//        RegisterUserAccountDTO quickAccount = RegisterUserAccountDTO.builder()
//                .username("violet")
//                .password("Violet!123")
//                .firstName("Violet")
//                .lastName("David")
//                .gender("MALE")
//                .email("David.violet@gmail.com")
//                .build();
//
//        String userQuickAccountURL = "/v1/users/register";
//
//        HttpEntity<RegisterUserAccountDTO> request = new HttpEntity<>(quickAccount);
//        ResponseEntity<UserDTO> response = restTemplate.postForEntity(userQuickAccountURL, request, UserDTO.class);
//
//        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
//
//        UserDTO userDTO = response.getBody();
//
//        assertNotNull(userDTO);
//        assertEquals("violet", userDTO.getUsername());
//        assertEquals("David", userDTO.getLastName());
//        assertEquals("Violet", userDTO.getFirstName());
//        assertEquals("MALE", userDTO.getGender());
//
//        assertNotNull(userDTO.getContactDTO());
//        assertEquals("David.violet@gmail.com", userDTO.getContactDTO().getEmail());
//
//        // delete the created user
//        userService.deleteUserById(userDTO.getId());
//    }
//
//}
