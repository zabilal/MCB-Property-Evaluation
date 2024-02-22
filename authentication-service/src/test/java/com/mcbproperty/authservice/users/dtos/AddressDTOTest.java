//package com.gloomme.authservice.users.dtos;
//
//import com.gloomme.authservice.entity.Address;
//import com.gloomme.authservice.pojo.response.AddressDTO;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class AddressDTOTest {
//
//    @Test
//    public void testAddressDTOConstructor1() {
//        AddressDTO addressDTO = new AddressDTO();
//        assertEquals(null, addressDTO.getCity());
//        assertEquals(null, addressDTO.getCountry());
//        assertEquals(null, addressDTO.getZipCode());
//        assertEquals(null, addressDTO.getAddress());
//        assertEquals(null, addressDTO.getAddress2());
//    }
//
//    @Test
//    public void testAddressDTOConstructor2() {
//        Address address = new Address();
//        address.setCity("Abuja");
//        address.setCountry("Nigeria");
//        address.setZipCode("23401");
//
//        AddressDTO addressDTO = new AddressDTO(address);
//        assertEquals("Abuja", addressDTO.getCity());
//        assertEquals("Nigeria", addressDTO.getCountry());
//        assertEquals("23401", addressDTO.getZipCode());
//    }
//
//}
