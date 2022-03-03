package com.songlei.test;

import com.songlei.mapper.ProductInfoMapper;
import com.songlei.pojo.ProductInfo;
import com.songlei.pojo.vo.ProductInfoVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext_dao.xml","classpath:applicationContext_service.xml"})
public class MyTest2 {

    @Autowired
    ProductInfoMapper mapper;

    @Test
    public void testSelectCondition(){
        ProductInfoVo vo = new ProductInfoVo();
//        vo.setPname("4");
//        vo.setTypeid(3);
        vo.setLprice(3000);
        vo.setHprice(3999);
        List<ProductInfo> list = mapper.selectCondition(vo);
        list.forEach(productInfo -> System.out.println(productInfo));
    }
}
