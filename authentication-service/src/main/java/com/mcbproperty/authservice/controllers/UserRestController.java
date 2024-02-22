package com.mcbproperty.authservice.controllers;

import com.mcbproperty.authservice.pojo.request.CreateOrUpdateUserDTO;
import com.mcbproperty.authservice.pojo.request.RegisterUserAccountDTO;
import com.mcbproperty.authservice.pojo.response.UserDTO;
import com.mcbproperty.authservice.pojo.response.UserListDTO;
import com.mcbproperty.authservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
@Slf4j
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<UserListDTO> getUserPresentationList() {
        List<UserDTO> list = userService.getUserPresentationList();
        UserListDTO userListDTO = new UserListDTO();
        list.stream().forEach(e -> userListDTO.getUserList().add(e));
        return ResponseEntity.ok(userListDTO);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody CreateOrUpdateUserDTO createOrUpdateUserDTO) {
        log.info("request is  here in auth controller");
        return new ResponseEntity<>(new UserDTO(userService.createUser(createOrUpdateUserDTO)), null, HttpStatus.CREATED);
    }

    // register a new user's account: no all the user information are required
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerNewUserAccount(@RequestBody RegisterUserAccountDTO registerUserAccountDTO) {
        return new ResponseEntity<>(new UserDTO(userService.registerUserAccount(registerUserAccountDTO)), null, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable("id") Long id) {
        return new UserDTO(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") Long id, @RequestBody CreateOrUpdateUserDTO updateUserDTO) {
        return new ResponseEntity<>(new UserDTO(userService.updateUser(id, updateUserDTO)), null, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    // add or remove a Role on a user
    @PostMapping("/{id}/roles/{roleId}")
    public ResponseEntity<UserDTO> addRole(@PathVariable("id") Long id, @PathVariable("roleId") Long roleId) {
        return new ResponseEntity<>(new UserDTO(userService.addRole(id, roleId)), null, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/roles/{roleId}")
    public ResponseEntity<UserDTO> removeRole(@PathVariable("id") Long id, @PathVariable("roleId") Long roleId) {
        return new ResponseEntity<>(new UserDTO(userService.removeRole(id, roleId)), null, HttpStatus.OK);
    }

    @GetMapping("/permissions/{permissionKey}")
    public ResponseEntity<?> getUsersWithPermission(@PathVariable("permissionKey") String permissionKey){
        return new ResponseEntity<>(userService.getUsersWithPermission(permissionKey), HttpStatus.OK);
    }

}
