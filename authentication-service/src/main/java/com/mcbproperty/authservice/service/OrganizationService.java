//package com.gloomme.authservice.service;
//
//import com.gloomme.authservice.entity.Organization;
//import com.gloomme.authservice.entity.User;
//import com.gloomme.authservice.exceptions.InvalidEmailException;
//import com.gloomme.authservice.exceptions.InvalidUserDataException;
//import com.gloomme.authservice.exceptions.OrganizationNotFoundException;
//import com.gloomme.authservice.pojo.request.CreateOrUpdateUserDTO;
//import com.gloomme.authservice.pojo.request.OrganizationSignUpRequest;
//import com.gloomme.authservice.pojo.response.*;
//import com.gloomme.authservice.repository.OrganizationRepository;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.*;
//
//@Service
//@Slf4j
//@AllArgsConstructor
//public class OrganizationService {
//
//    private OrganizationRepository organizationRepository;
//    private UserService userService;
//
//    public OrganizationResponse createOrganizatonAccount(OrganizationSignUpRequest request) {
//
//        log.info("ORG REQUEST :: {}", request);
//
//        Organization org = new Organization();
//        Optional<Organization> orgAccount = organizationRepository.findByEmailAndActiveAndDeleted(request.getEmail(), Boolean.TRUE, Boolean.FALSE);
//        if (orgAccount.isPresent()) {
//            throw new InvalidEmailException("Organization already exist with same email");
//        }
////        String craterId = UUID.randomUUID().toString();
//        org.setCraterId(UUID.randomUUID().toString());
//        org.setName(request.getName());
//        org.setEmail(request.getEmail());
//        org.setPhone(request.getPhone());
//        org.setAddress(request.getAddress());
//        org.setCity(request.getCity());
//        org.setState(request.getState());
//        org.setCountry(request.getCountry());
//        org.setWebsite(request.getWebsite());
//        org.setActive(Boolean.TRUE);
//        org.setDeleted(Boolean.FALSE);
//        org.setDescription(request.getDescription());
//        org.setCreatedDate(LocalDateTime.now().toString());
//        org.setAvatar(request.getAvatar());
//
////        try mapping sub apps to db
////        org.setSubscribedApplicationIds(request.getSubscribedAppIds());
//
//        Organization savedOrganization = organizationRepository.save(org);
//        log.info(String.format("NEW Organization created and saved with data :: {}", savedOrganization));
//
////          create admin user
//        CreateOrUpdateUserDTO user = CreateOrUpdateUserDTO.builder()
//                .username(request.getUsername())
//                .firstName(request.getFirstName())
//                .lastName(request.getLastName())
//                .phone(request.getPhone())
//                .email(request.getEmail())
//                .password(request.getPassword())
//                .organizationCraterId(savedOrganization.getCraterId())
//                .role(1)
//                .build();
//        User adminUser = userService.createUser(user);
//        if (Objects.isNull(adminUser)) {
//            throw new InvalidUserDataException("Error creating admin user");
//        }
//        //return created entities as response
//        return new OrganizationResponse(new UserDTO(adminUser), new OrganizationDTO(savedOrganization));
//    }
//
//    public Map<String, Object> getAllOrganizations(int page, int size) {
//        Pageable paging = PageRequest.of(page, size);
//        Page<Organization> result = organizationRepository.findAll(paging);
//
//        List<Organization> organizationList = result.toList();
//        Map<String, Object> response = new HashMap<>();
//        response.put("organizations", organizationList);
//        response.put("currentPage", result.getNumber());
//        response.put("totalItems", result.getTotalElements());
//        response.put("totalPages", result.getTotalPages());
//
//        return response;
//    }
//
//    public Object getOrganizationByCraterId(String craterId) {
//        Optional<Organization> optionalOrganization = organizationRepository.findByCraterId(craterId);
//        if (!optionalOrganization.isPresent()){
//            throw new OrganizationNotFoundException("No organization with id " + craterId);
//        }
//        return new OrganizationDTO(optionalOrganization.get());
//    }
//}
