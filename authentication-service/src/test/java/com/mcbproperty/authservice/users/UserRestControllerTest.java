//package com.gloomme.authservice.users;
//
//import com.gloomme.authservice.entity.Role;
//import com.gloomme.authservice.entity.User;
//import com.gloomme.authservice.pojo.request.CreateOrUpdateUserDTO;
//import com.gloomme.authservice.pojo.request.RegisterUserAccountDTO;
//import com.gloomme.authservice.pojo.response.AddressDTO;
//import com.gloomme.authservice.pojo.response.ContactDTO;
//import com.gloomme.authservice.pojo.response.UserDTO;
//import com.gloomme.authservice.repository.UserRepository;
//import com.gloomme.authservice.service.UserService;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.net.URI;
//import java.util.List;
//import java.util.Optional;
//
//import static org.hamcrest.Matchers.equalTo;
//import static org.hamcrest.Matchers.is;
//import static org.junit.Assert.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Slf4j
//public class UserRestControllerTest {
//
//    @Autowired
//    TestRestTemplate restTemplate;
//
//    @Autowired
//    UserService userService;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Test
//    public void test_getUserById() {
//        Long userId = 1L;
//        String userURL = "/v1/users/" + userId;
//
//        ResponseEntity<UserDTO> response = restTemplate.getForEntity(userURL, UserDTO.class);
//
//        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
//
//        UserDTO userDTO = response.getBody();
//        assertNotNull(userDTO);
//
//        assertThat(userDTO.getId(), equalTo(1L));
//        assertThat(userDTO.getLastName(), equalTo("Musa"));
//        assertThat(userDTO.getFirstName(), equalTo("Test"));
//        assertThat(userDTO.getContactDTO().getEmail(), equalTo("Musa.test@gmail.com"));
//        assertThat(userDTO.isEnabled(), equalTo(true));
//    }
//
//    @Test
//    public void test_createUser() {
//        CreateOrUpdateUserDTO createOrUpdateUserDTO = CreateOrUpdateUserDTO.builder()
//               .username("frank")
//               .password("Frank!123")
//               .firstName("Frank")
//               .lastName("Blu")
//               .gender("MALE")
//               .enabled(true)
//               .note("created for test")
//               .email("frank.blu@gmail.com")
//               .phone("+3531194334455")
//                .skype("skype").facebook("facebook").linkedin("linkedin").website("www.test.com").contactNote("Test on contact")
//               .address("dark road 1")
//               .address2("salt hill")
//               .city("Dublin")
//               .country("Nigeria")
//               .zipCode("700105").build();
//
//        URI uri = URI.create("/v1/users");
//
//        HttpEntity<CreateOrUpdateUserDTO> request = new HttpEntity<>(createOrUpdateUserDTO);
//        ResponseEntity<UserDTO> response = restTemplate.postForEntity(uri, request, UserDTO.class);
//        log.info("USERDTO :: {}", response.getBody());
//
//        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
//
//        UserDTO userDTO = response.getBody();
//        assertNotNull(userDTO);
//
//        assertNotNull(userDTO);
//        assertEquals("frank", userDTO.getUsername());
//        assertEquals("Frank", userDTO.getLastName());
//        assertEquals("Blu", userDTO.getFirstName());
//        assertEquals("MALE", userDTO.getGender());
//
//        List<String> roles = userDTO.getRoles();
//        assertNotNull(roles);
//        assertTrue(roles.contains("USER"));
//
//        assertEquals(true, userDTO.isEnabled());
//        assertEquals("created for test", userDTO.getNote());
//
//        ContactDTO contactDTO = userDTO.getContactDTO();
//
//        assertEquals("frank.blu@gmail.com", contactDTO.getEmail());
//        assertEquals("+3531194334455", contactDTO.getPhone());
//        assertEquals("skype", contactDTO.getSkype());
//        assertEquals("facebook", contactDTO.getFacebook());
//        assertEquals("linkedin", contactDTO.getLinkedin());
//        assertEquals("www.test.com", contactDTO.getWebsite());
//        assertEquals("Test on contact", contactDTO.getContactNote());
//
//        assertNotNull(userDTO.getAddressDTO());
//        AddressDTO addressDTO = userDTO.getAddressDTO();
//        assertEquals("dark road 1", addressDTO.getAddress());
//        assertEquals("salt hill", addressDTO.getAddress2());
//        assertEquals("Dublin", addressDTO.getCity());
//        assertEquals("Nigeria", addressDTO.getCountry());
//        assertEquals("700105", addressDTO.getZipCode());
//
//        // delete the created user
//        userService.deleteUserById(userDTO.getId());
//    }
//
//    @Test
//    public void test_updateUser() {
//        // create a new user to update
//        RegisterUserAccountDTO quickAccount = RegisterUserAccountDTO.builder()
//                .username("anna")
//                .password("Anna!123")
//                .firstName("Anna")
//                .lastName("Verdi")
//                .gender("FEMALE")
//                .email("anna.verdi@gmail.com")
//                .build();
//
//        String registerAccountURL = "/users/register";
//        HttpEntity<RegisterUserAccountDTO> requestCreate = new HttpEntity<>(quickAccount);
//        ResponseEntity<UserDTO> responseCreate = restTemplate.postForEntity(registerAccountURL, requestCreate, UserDTO.class);
//
//        assertThat(responseCreate.getStatusCode(), equalTo(HttpStatus.CREATED));
//        UserDTO userDTO = responseCreate.getBody();
//
//        assertNotNull(userDTO);
//
//        // test the update
//        Long userId = userDTO.getId();
//        URI uri = URI.create("/users/" + userId);
//
//        CreateOrUpdateUserDTO createOrUpdateUserDTO = CreateOrUpdateUserDTO.builder()
//                .username("anna")
//                .password("Anna!123456")
//                .firstName("Anna")
//                .lastName("Verdi")
//                .gender("FEMALE")
//                .enabled(true)
//                .note("updated for test")
//                .email("anna.verdi@gmail.com")
//                .phone("+3531194334455")
//                .skype("skype").facebook("facebook").linkedin("linkedin").website("www.test.com").contactNote("Test on contact")
//                .address("The sunny road 15")
//                .address2("Sunny valley")
//                .city("Dublin")
//                .country("Nigeria")
//                .zipCode("700105").build();
//
//        HttpEntity<CreateOrUpdateUserDTO> request = new HttpEntity<>(createOrUpdateUserDTO);
//        ResponseEntity<UserDTO> response = restTemplate.exchange(uri, HttpMethod.PUT, request, UserDTO.class);
//
//        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
//
//        UserDTO userUpdatedDTO = response.getBody();
//
//        assertEquals("anna", userUpdatedDTO.getUsername());
//        assertEquals("Anna", userUpdatedDTO.getLastName());
//        assertEquals("Verdi", userUpdatedDTO.getFirstName());
//        assertEquals("FEMALE", userUpdatedDTO.getGender());
//        assertEquals(true, userUpdatedDTO.isEnabled());
//
//        // role
//        assertNotNull(userUpdatedDTO.getRoles());
//        assertTrue(userUpdatedDTO.getRoles().contains( "USER"));
//
//        assertEquals("updated for test", userUpdatedDTO.getNote());
//
//        // contact
//        ContactDTO contactDTO = userUpdatedDTO.getContactDTO();
//        assertNotNull(contactDTO);
//
//        assertEquals("anna.verdi@gmail.com", contactDTO.getEmail());
//        assertEquals("+3531194334455", contactDTO.getPhone());
//        assertEquals("skype", contactDTO.getSkype());
//        assertEquals("facebook", contactDTO.getFacebook());
//        assertEquals("linkedin", contactDTO.getLinkedin());
//        assertEquals("www.test.com", contactDTO.getWebsite());
//        assertEquals("Test on contact", contactDTO.getContactNote());
//
//        // address
//        assertNotNull(userUpdatedDTO.getAddressDTO());
//        assertEquals("The sunny road 15", userUpdatedDTO.getAddressDTO().getAddress());
//        assertEquals("Sunny valley", userUpdatedDTO.getAddressDTO().getAddress2());
//        assertEquals("Dublin", userUpdatedDTO.getAddressDTO().getCity());
//        assertEquals("Nigeria", userUpdatedDTO.getAddressDTO().getCountry());
//        assertEquals("700105", userUpdatedDTO.getAddressDTO().getZipCode());
//
//        // delete the user
//        userService.deleteUserById(userUpdatedDTO.getId());
//    }
//
//    @Test
//    public void test_deleteUser() {
//        // create a new user to test the deletion
//        RegisterUserAccountDTO quickAccount = RegisterUserAccountDTO.builder()
//                .username("anna2")
//                .password("Anna2!123")
//                .firstName("Anna2")
//                .lastName("Verdi")
//                .gender("FEMALE")
//                .email("anna2.verdi@gmail.com")
//                .build();
//
//        String registerAccountURL = "/users/register";
//        HttpEntity<RegisterUserAccountDTO> request = new HttpEntity<>(quickAccount);
//        ResponseEntity<UserDTO> response = restTemplate.postForEntity(registerAccountURL, request, UserDTO.class);
//
//        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
//        UserDTO userDTO = response.getBody();
//
//        assertNotNull(userDTO);
//
//        // call the delete endpoint
//        String deleteUserURL = "/users/" + userDTO.getId();
//        restTemplate.delete(deleteUserURL);
//
//        // retrieve a not existing user must to be empty response
//        Optional<User> userOpt = userRepository.findById(userDTO.getId());
//        assertFalse(userOpt.isPresent());
//    }
//
//    // test add role on User
//    @Test
//    public void test_addRoleOnUser() {
//        // create a new user
//        RegisterUserAccountDTO registerUserAccountDTO = RegisterUserAccountDTO.builder()
//                .username("anna")
//                .password("Anna!123")
//                .firstName("Anna")
//                .lastName("Verdi")
//                .gender("FEMALE")
//                .email("anna.verdi@gmail.com")
//                .build();
//
//        String registerAccountURL = "/users/register";
//        HttpEntity<RegisterUserAccountDTO> requestCreate = new HttpEntity<>(registerUserAccountDTO);
//        ResponseEntity<UserDTO> responseCreate = restTemplate.postForEntity(registerAccountURL, requestCreate, UserDTO.class);
//
//        assertThat(responseCreate.getStatusCode(), equalTo(HttpStatus.CREATED));
//        UserDTO userDTO = responseCreate.getBody();
//
//        assertNotNull(userDTO);
//
//        // test the add role
//        Long userId = userDTO.getId();
//        URI uri = URI.create("/users/" + userId + "/roles/" + Role.ADMINISTRATOR);
//        ResponseEntity<UserDTO> response = restTemplate.exchange(uri, HttpMethod.POST, null, UserDTO.class);
//
//        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
//
//        UserDTO addedRoleOnUserDTO = response.getBody();
//
//        assertEquals("anna", addedRoleOnUserDTO.getUsername());
//        assertEquals("Anna", addedRoleOnUserDTO.getLastName());
//        assertEquals("Verdi", addedRoleOnUserDTO.getFirstName());
//        assertEquals("FEMALE", addedRoleOnUserDTO.getGender());
//        assertEquals(true, addedRoleOnUserDTO.isEnabled());
//
//        // check the role list
//        assertNotNull(addedRoleOnUserDTO.getRoles());
//        assertEquals(2L, addedRoleOnUserDTO.getRoles().size());
//        assertTrue(addedRoleOnUserDTO.getRoles().contains("USER"));
//        assertTrue(addedRoleOnUserDTO.getRoles().contains("ADMINISTRATOR"));
//
//        // delete the user
//        userService.deleteUserById(addedRoleOnUserDTO.getId());
//    }
//
//    @Test
//    public void test_addRoleOnUser_wrongUserId() {
//        // perform add role ADMINISTRATOR on not existing user
//        Long userId = 99L; // not existing user
//        URI uri = URI.create("/users/" + userId + "/roles/" + Role.ADMINISTRATOR);
//        ResponseEntity<UserDTO> removeResponse = restTemplate.exchange(uri, HttpMethod.POST, null, UserDTO.class);
//
//        assertThat(removeResponse.getStatusCode(), is(HttpStatus.NOT_FOUND));
//    }
//
//    @Test
//    public void test_addRoleOnUser_wrongRoleId() {
//        // perform add role with not existing role
//        URI uri = URI.create("/users/" + 1L + "/roles/" + 99L);
//        ResponseEntity<UserDTO> removeResponse = restTemplate.exchange(uri, HttpMethod.POST, null, UserDTO.class);
//
//        assertThat(removeResponse.getStatusCode(), is(HttpStatus.NOT_FOUND));
//    }
//
//    @Test
//    public void test_removeRoleOnUser() {
//        // create a new user
//        RegisterUserAccountDTO registerUserAccountDTO = RegisterUserAccountDTO.builder()
//                .username("anna")
//                .password("Anna!123")
//                .firstName("Anna")
//                .lastName("Verdi")
//                .gender("FEMALE")
//                .email("anna.verdi@gmail.com")
//                .build();
//
//        String registerAccountURL = "/users/register";
//        HttpEntity<RegisterUserAccountDTO> requestCreate = new HttpEntity<>(registerUserAccountDTO);
//        ResponseEntity<UserDTO> responseCreate = restTemplate.postForEntity(registerAccountURL, requestCreate, UserDTO.class);
//
//        assertThat(responseCreate.getStatusCode(), equalTo(HttpStatus.CREATED));
//        UserDTO userDTO = responseCreate.getBody();
//
//        assertNotNull(userDTO);
//
//        // test the add role
//        Long userId = userDTO.getId();
//        URI uri = URI.create("/users/" + userId + "/roles/" + Role.ADMINISTRATOR);
//        ResponseEntity<UserDTO> response = restTemplate.exchange(uri, HttpMethod.POST, null, UserDTO.class);
//
//        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
//
//        UserDTO addedRoleOnUserDTO = response.getBody();
//
//        // check the role list
//        assertNotNull(addedRoleOnUserDTO.getRoles());
//        assertEquals(2L, addedRoleOnUserDTO.getRoles().size());
//        assertTrue(addedRoleOnUserDTO.getRoles().contains("USER"));
//        assertTrue(addedRoleOnUserDTO.getRoles().contains("ADMINISTRATOR"));
//
//        // perform the remove role ADMIN
//        uri = URI.create("/users/" + userId + "/roles/" + Role.ADMINISTRATOR);
//        ResponseEntity<UserDTO> removeResponse = restTemplate.exchange(uri, HttpMethod.DELETE, null, UserDTO.class);
//
//        assertThat(removeResponse.getStatusCode(), is(HttpStatus.OK));
//
//        UserDTO removedRoleOnUserDTO = removeResponse.getBody();
//
//        // check the role list
//        assertNotNull(removedRoleOnUserDTO.getRoles());
//        assertEquals(1L, removedRoleOnUserDTO.getRoles().size());
//        assertTrue(removedRoleOnUserDTO.getRoles().contains("USER"));
//
//        // delete the user
//        userService.deleteUserById(removedRoleOnUserDTO.getId());
//    }
//
//    @Test
//    public void test_removeRoleOnUser_wrongUserId() {
//        // perform the remove role ADMINISTRATOR on not existing user
//        Long userId = 99L; // not existing user
//        URI uri = URI.create("/users/" + userId + "/roles/" + Role.ADMINISTRATOR);
//        ResponseEntity<UserDTO> removeResponse = restTemplate.exchange(uri, HttpMethod.DELETE, null, UserDTO.class);
//
//        assertThat(removeResponse.getStatusCode(), is(HttpStatus.NOT_FOUND));
//    }
//
//    @Test
//    public void test_removeRoleOnUser_wrongRoleId() {
//        // perform the remove not existing role
//        URI uri = URI.create("/users/" + 1L + "/roles/" + 99L);
//        ResponseEntity<UserDTO> removeResponse = restTemplate.exchange(uri, HttpMethod.DELETE, null, UserDTO.class);
//
//        assertThat(removeResponse.getStatusCode(), is(HttpStatus.NOT_FOUND));
//    }
//
//    @Test
//    public void test_delete_securedUser_return_BAD_REQUEST() {
//        // create a new user to test the deletion
//        RegisterUserAccountDTO quickAccount = RegisterUserAccountDTO.builder()
//                .username("anna2")
//                .password("Anna2!123")
//                .firstName("Anna2")
//                .lastName("Verdi")
//                .gender("FEMALE")
//                .email("anna2.verdi@gmail.com")
//                .build();
//
//        String registerAccountURL = "/users/register";
//        HttpEntity<RegisterUserAccountDTO> request = new HttpEntity<>(quickAccount);
//        ResponseEntity<UserDTO> response = restTemplate.postForEntity(registerAccountURL, request, UserDTO.class);
//
//        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
//        UserDTO userDTO = response.getBody();
//
//        assertNotNull(userDTO);
//
//        // call the delete endpoint
//        String deleteUserURL = "/users/" + userDTO.getId();
//        restTemplate.delete(deleteUserURL);
//
//        // retrieve a not existing user must to be empty response
//        Optional<User> userOpt = userRepository.findById(userDTO.getId());
//        assertFalse(userOpt.isPresent());
//    }
//
//}
