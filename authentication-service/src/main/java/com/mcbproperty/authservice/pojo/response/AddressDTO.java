//package com.gloomme.authservice.pojo.response;
//
//import com.gloomme.authservice.entity.Address;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.io.Serializable;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class AddressDTO implements Serializable {
//
//    private String address;
//    private String address2;
//    private String city;
//    private String country;
//    private String zipCode;
//
//    public AddressDTO(Address address) {
//        if (address != null) {
//            this.address = address.getAddress();
//            this.address2 = address.getAddress2();
//            this.city = address.getCity();
//            this.country = address.getCountry();
//            this.zipCode = address.getZipCode();
//        }
//    }
//}
