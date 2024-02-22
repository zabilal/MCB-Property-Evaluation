//package com.gloomme.authservice.users;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.gloomme.authservice.entity.User;
//import com.gloomme.authservice.pojo.response.UserDTO;
//import com.gloomme.authservice.service.UserService;
//import com.gloomme.authservice.users.services.UserTestHelper;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//public class UserRestControllerIntegrationTest {
//
//    final ObjectMapper mapper = new ObjectMapper();
//
//    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    private UserService userService;
//
//    @Test
//    public void getUserPresentationList() throws Exception {
//        UserDTO user1 = new UserDTO(UserTestHelper.getUserTestData(1L, "Musa", "Musa",
//                "John", "Musa.test@gmail.com", "+2348132729144"));
//        UserDTO user2 = new UserDTO(UserTestHelper.getUserTestData(2L, "David", "David",
//                "Verdi", "David.test@gmail.com", "+2348132729144"));
//        UserDTO user3 = new UserDTO(UserTestHelper.getUserTestData(3L, "franco", "Franco",
//                "Rosemary", "franco.test@gmail.com", "+3531122334477"));
//
//        List<UserDTO> userList = Arrays.asList(user1, user2, user3);
//
//        given(userService.getUserPresentationList()).willReturn(userList);
//
//        mvc.perform(MockMvcRequestBuilders.get("/users")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content()
//                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.userList[0].name").value("Musa"))
//                .andExpect(jsonPath("$.userList[0].surname").value("John"))
//                .andExpect(jsonPath("$.userList[0].contactDTO.email").value("Musa.test@gmail.com"))
//                .andExpect(jsonPath("$.userList[0].username").value("Musa"))
//                .andExpect(jsonPath("$.userList[0].contactDTO.phone").value("+2348132729144"))
//                .andExpect(jsonPath("$.userList[1].name").value("David"))
//                .andExpect(jsonPath("$.userList[1].surname").value("Verdi"))
//                .andExpect(jsonPath("$.userList[1].contactDTO.email").value("David.test@gmail.com"))
//                .andExpect(jsonPath("$.userList[1].username").value("David"))
//                .andExpect(jsonPath("$.userList[1].contactDTO.phone").value("+2348132729144"))
//                .andExpect(jsonPath("$.userList[2].name").value("Franco"))
//                .andExpect(jsonPath("$.userList[2].surname").value("Rosemary"))
//                .andExpect(jsonPath("$.userList[2].contactDTO.email").value("franco.test@gmail.com"))
//                .andExpect(jsonPath("$.userList[2].username").value("franco"))
//                .andExpect(jsonPath("$.userList[2].contactDTO.phone").value("+3531122334477"));
//    }
//
//    @Test
//    public void getUserById() throws Exception {
//        User user1 = UserTestHelper.getUserTestData(1L, "Musa", "Musa",
//                "John", "Musa.test@gmail.com", "+2348132729144");
//
//        user1.setBirthDate(LocalDate.of(1977, 8, 14));
//
//        given(userService.getUserById(1L)).willReturn(user1);
//
//        Long userId = 1L;
//
//        mvc.perform(MockMvcRequestBuilders.get("/users/" + userId)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content()
//                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("name").value("Musa"))
//                .andExpect(jsonPath("surname").value("John"))
//                .andExpect(jsonPath("contactDTO.email").value("Musa.test@gmail.com"))
//                .andExpect(jsonPath("username").value("Musa"))
//                .andExpect(jsonPath("birthDate").value("1977-08-14"))
//                .andExpect(jsonPath("contactDTO.phone").value("+2348132729144"));
//    }
//
//}
