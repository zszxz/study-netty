package com.zszxz.netty;

import com.zszxz.netty.mapper.NettyMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author lsc
 * <p> </p>
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Component
public class AppRunTest {

    @Autowired
    NettyMapper nettyMapper;


    @Test
    public void test(){
        System.out.println("5555555");
        //System.out.println(nettyMapper.getUser());
    }


}
