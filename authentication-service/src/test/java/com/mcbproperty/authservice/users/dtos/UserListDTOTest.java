package com.mcbproperty.authservice.users.dtos;

import com.mcbproperty.authservice.pojo.response.UserListDTO;
import org.junit.Assert;
import org.junit.Test;

public class UserListDTOTest {

    @Test
    public void userListDTOTest() {
        UserListDTO userListDTO = new UserListDTO();

        Assert.assertNotNull(userListDTO.getUserList().size());
        Assert.assertEquals(0, userListDTO.getUserList().size());
    }

}
