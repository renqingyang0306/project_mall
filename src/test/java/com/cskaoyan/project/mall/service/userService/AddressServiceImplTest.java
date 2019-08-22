package com.cskaoyan.project.mall.service.userService;

import com.cskaoyan.project.mall.domain.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static java.lang.System.out;
import static org.junit.Assert.*;

/**
 * Created by IntelliJ IDEA
 *
 * @auther XXX
 * @date 2019/8/22
 * @time 20:47
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressServiceImplTest {
    @Autowired
    AddressService addressService;

    @Test
    public void mytest1(){
        Address address = addressService.queryAddressById(1);
        out.println(address);
    }
}