package com.mcbproperty.authservice.service;

import com.mcbproperty.authservice.config.JwtUtil;
import com.mcbproperty.authservice.entity.*;

import java.time.Instant;
import java.util.*;

import com.mcbproperty.authservice.exceptions.*;
import com.mcbproperty.authservice.pojo.request.CreateOrUpdateUserDTO;
import com.mcbproperty.authservice.pojo.request.RegisterUserAccountDTO;
import com.mcbproperty.authservice.pojo.response.LoginResponse;
import com.mcbproperty.authservice.pojo.response.UserDTO;
import com.mcbproperty.authservice.repository.PermissionRepository;
import com.mcbproperty.authservice.repository.RoleRepository;
import com.mcbproperty.authservice.repository.UserRepository;
import com.mcbproperty.authservice.service.validation.EmailValidator;
import com.mcbproperty.authservice.service.validation.PasswordValidator;
import com.mcbproperty.authservice.service.validation.PhoneValidator;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.GrantedAuthority;

import static java.util.stream.Collectors.joining;


@Service
@Slf4j
public class UserService  {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Value("${microservice.security.salt}")
    private String salt;

    private PasswordValidator passwordValidator;
    private EmailValidator emailValidator;
    private PhoneValidator phoneValidator;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder encoder;

    public UserService() {
        passwordValidator = new PasswordValidator();
        emailValidator = new EmailValidator();
        phoneValidator = new PhoneValidator();
    }

    public List<UserDTO> getUserPresentationList() {
        ArrayList<UserDTO> listDto = new ArrayList<>();
        Iterable<User> list = getUserList();
        list.forEach(e -> listDto.add(new UserDTO(e)));
        return listDto;
    }

    public User getUserById(Long id) {
        if (id == null) {
            throw new InvalidUserIdentifierException("User Id cannot be null");
        }
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            return userOpt.get();
        }
        throw new UserNotFoundException(String.format("User not found for Id = %s", id));
    }

    public User getUserByUsername(String username) {
        if (username == null) {
            throw new InvalidUsernameException("username cannot be null");
        }
        return userRepository.findByUsername(username);
    }

    public User getUserByEmail(String email) {
        if (email == null) {
            throw new InvalidEmailException("email cannot be null");
        }
        return userRepository.findByEmail(email);
    }

    @Transactional
    public User registerUserAccount(RegisterUserAccountDTO registerUserAccountDTO) {
        if (registerUserAccountDTO == null) {
            throw new InvalidUserDataException("User account data cannot be null");
        }

        checkIfUsernameNotUsed(registerUserAccountDTO.getUsername());
        passwordValidator.checkPassword(registerUserAccountDTO.getPassword());
        emailValidator.checkEmail(registerUserAccountDTO.getEmail());

        checkIfEmailNotUsed(registerUserAccountDTO.getEmail());

        // create the new user account: not all the user information required
        User user = new User();
        user.setUsername(registerUserAccountDTO.getUsername());
        user.setPassword(encoder.encode(registerUserAccountDTO.getPassword()));

        user.setFirstName(registerUserAccountDTO.getFirstName());
        user.setLastName(registerUserAccountDTO.getLastName());
        user.setEnabled(true);
        user.setSecured(false);

        User userCreated = userRepository.save(user);

        log.info(String.format("User %s has been created.", userCreated.getId()));
        return userCreated;
    }

    // check if the username has not been registered
    public void checkIfUsernameNotUsed(String username) {
        User userByUsername = getUserByUsername(username);
        if (userByUsername != null) {
            String msg = String.format("The username %s it's already in use from another user",
                    userByUsername.getUsername());
            log.error(msg);
            throw new InvalidUserDataException(msg);
        }
    }

    // check if the email has not been registered
    public void checkIfEmailNotUsed(String email) {
        User userByEmail = getUserByEmail(email);
        if (userByEmail != null) {
            String msg = String.format("The email %s it's already in use from another user with ID = %s",
                    userByEmail.getEmail(), userByEmail.getId());
            log.error(msg);
            throw new InvalidUserDataException(String.format("This email %s it's already in use.",
                    userByEmail.getEmail()));
        }
    }

    @Transactional
    public User createUser(CreateOrUpdateUserDTO createUserDTO) {
        log.info("request is  here in service");
        if (createUserDTO == null) {
            throw new InvalidUserDataException("User account data cannot be null");
        }

        checkIfUsernameNotUsed(createUserDTO.getUsername());
        checkIfEmailNotUsed(createUserDTO.getEmail());
        passwordValidator.checkPassword(createUserDTO.getPassword());
        emailValidator.checkEmail(createUserDTO.getEmail());
        phoneValidator.checkPhone(createUserDTO.getPhone());

        // create the user
        User user = new User();
        user.setUsername(createUserDTO.getUsername());
        user.setEmail(createUserDTO.getEmail());
        user.setPassword(encoder.encode(createUserDTO.getPassword()));

        user.setFirstName(createUserDTO.getFirstName());
        user.setOtherName(createUserDTO.getOtherName());
        user.setLastName(createUserDTO.getLastName());


        user.setEnabled(true);
        user.setSecured(createUserDTO.isSecured());

        user.setUserId(UUID.randomUUID().toString());
        user.setCreatedBy(user);
        user.setLastModifiedBy(user);

        // set default user the role
        log.info("DTO data before :: {}", createUserDTO);
        log.info("User data before :: {}", user);
        addUserRole(user, createUserDTO.getRole());

        log.info("User data after :: {}", user);

        User userCreated = userRepository.save(user);

        log.info(String.format("User %s has been created.", userCreated.getId()));
        return userCreated;
    }

    public void addUserRole(User user, long roleId) {
        Optional<Role> roleOpt = roleRepository.findById(roleId);
        if (roleOpt.isEmpty()) {
            throw new RoleNotFoundException("Role cannot be null");
        }
        user.getRoles().add(roleOpt.get());
    }

    @Transactional
    public User updateUser(Long id, CreateOrUpdateUserDTO updateUserDTO) {
        if (id == null) {
            throw new InvalidUserIdentifierException("Id cannot be null");
        }
        if (updateUserDTO == null) {
            throw new InvalidUserDataException("User account data cannot be null");
        }

        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            throw new UserNotFoundException(String.format("The user with Id = %s doesn't exists", id));
        }
        User user = userOpt.get();

        // check if the username has not been registered
        User userByUsername = getUserByUsername(updateUserDTO.getUsername());
        if (Objects.nonNull(userByUsername)) {
            // check if the user's id is different than the actual user
            if (!user.getId().equals(userByUsername.getId())) {
                String msg = String.format("The username %s it's already in use from another user",
                        updateUserDTO.getUsername());
                log.error(msg);
                throw new InvalidUserDataException(msg);
            }
        }

        passwordValidator.checkPassword(updateUserDTO.getPassword());
        emailValidator.checkEmail(updateUserDTO.getEmail());
        phoneValidator.checkPhone(updateUserDTO.getPhone());

        // check if the new email has not been registered yet
        User userByEmail = getUserByEmail(updateUserDTO.getEmail());
        if (Objects.nonNull(userByEmail)) {
            // check if the user's email is different than the actual user
            if (!user.getId().equals(userByEmail.getId())) {
                String msg = String.format("The email %s it's already in use from another user with ID = %s",
                        updateUserDTO.getEmail(), userByEmail.getId());
                log.error(msg);
                throw new InvalidUserDataException(msg);
            }
        }

        // update the user
        user.setUsername(updateUserDTO.getUsername());

        // using the user's salt to secure the new validated password
        user.setPassword(EncryptionService.encrypt(updateUserDTO.getPassword(), salt));
        user.setFirstName(updateUserDTO.getFirstName());
        user.setOtherName(updateUserDTO.getOtherName());
        user.setLastName(updateUserDTO.getLastName());


        User userUpdated = userRepository.save(user);
        log.info(String.format("User %s has been updated.", user.getId()));

        return userUpdated;
    }

    public Iterable<User> getUserList() {
        return userRepository.findAll();
    }

    @Transactional
    public void deleteUserById(Long id) {
        if (id == null) {
            throw new InvalidUserIdentifierException("Id cannot be null");
        }

        Optional<User> userOpt = userRepository.findById(id);
        if (!userOpt.isPresent()) {
            throw new UserNotFoundException(String.format("User not found with Id = %s", id));
        }

        // only not secured users can be deleted
        User user = userOpt.get();
        if (user.isSecured()) {
            throw new UserIsSecuredException(String.format("User %s is secured and cannot be deleted.", id));
        }

        userRepository.deleteById(id);
        log.info(String.format("User %s has been deleted.", id));
    }

    @Transactional
    public LoginResponse login(String username, String password) {
        if ((Strings.isNullOrEmpty(username)) || (Strings.isNullOrEmpty(password))) {
            throw new InvalidLoginException("Username or Password cannot be null or empty");
        }

        User optionalUser = getUserByUsername(username);
        if (optionalUser == null) {
            // invalid username
            throw new InvalidLoginException("Invalid username or password");
        }

        log.info(String.format("Login request from user %s", optionalUser.getEmail()));

        try {
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(username, password));
            log.info(String.format("Login Auth from user %s", authentication.getAuthorities().toArray()));

            UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();

            Instant now = Instant.now();
            long expiry = 36000L;

            String roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(joining(" "));

            String jwt = JwtUtil.generateJwt(user.getEmail(), roles);

            return new LoginResponse(
                    jwt,
                    "Bearer",
                    user.getEmail(),
                    optionalUser.getUserId());
        } catch (BadCredentialsException ex) {
            throw new AuthenticationServiceException("Authentication failed");
        }
    }

    // add or remove a role on user

    @Transactional
    public User addRole(Long id, Long roleId) {
        // check user
        Optional<User> userOpt = userRepository.findById(id);
        if (!userOpt.isPresent()) {
            throw new UserNotFoundException(String.format("User not found with Id = %s", id));
        }
        User user = userOpt.get();

        // check role
        Optional<Role> roleOpt = roleRepository.findById(roleId);
        if (!roleOpt.isPresent()) {
            throw new RoleNotFoundException(String.format("Role not found with Id = %s", roleId));
        }

        Role role = roleOpt.get();

        user.getRoles().add(role);

        userRepository.save(user);
        log.info(String.format("Added role %s on user id = %s", role.getRole(), user.getId()));

        return user;
    }

    @Transactional
    public User removeRole(Long id, Long roleId) {
        // check user
        Optional<User> userOpt = userRepository.findById(id);
        if (!userOpt.isPresent()) {
            throw new UserNotFoundException(String.format("User not found with Id = %s", id));
        }
        User user = userOpt.get();

        // check role
        Optional<Role> roleOpt = roleRepository.findById(roleId);
        if (!roleOpt.isPresent()) {
            throw new RoleNotFoundException(String.format("Role not found with Id = %s", roleId));
        }

        Role role = roleOpt.get();

        user.getRoles().remove(role);

        userRepository.save(user);
        log.info(String.format("Removed role %s on user id = %s", role.getRole(), user.getId()));

        return user;
    }

    public String getUsersWithPermission(String permissionKey) {
        Optional<Permission> byPermission = permissionRepository.findByPermission(permissionKey);

        if(byPermission.isEmpty()){
            throw new PermissionNotFoundException("No such permission on the System");
        }

        Long roleId = roleRepository.findByPermission(byPermission.get().getId());

        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if (optionalRole.isEmpty()){
            throw new RoleNotFoundException("Role not found");
        }

        List<Long> userIdList = userRepository.findByRoleId(optionalRole.get().getId());

        Random rand = new Random();
        Optional<User> optionalUser = userRepository.findById(userIdList.get(rand.nextInt(userIdList.size())));

        if (optionalUser.isEmpty()){
            throw new UserNotFoundException("User not found");
        }

        return optionalUser.get().getUserId();
    }
}
