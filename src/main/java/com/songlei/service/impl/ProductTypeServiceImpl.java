package com.songlei.service.impl;

import com.songlei.mapper.ProductTypeMapper;
import com.songlei.pojo.ProductType;
import com.songlei.pojo.ProductTypeExample;
import com.songlei.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ProductTypeServiceImpl")
public class ProductTypeServiceImpl implements ProductTypeService {
    //数据层对象
    @Autowired
    ProductTypeMapper productTypeMapper;

    @Override
    public List<ProductType> getAll() {

        List<ProductType> list = productTypeMapper.selectByExample(new ProductTypeExample());


        return list;
    }
}
