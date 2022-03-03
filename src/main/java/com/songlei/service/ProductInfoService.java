package com.songlei.service;

import com.github.pagehelper.PageInfo;
import com.songlei.pojo.ProductInfo;
import com.songlei.pojo.vo.ProductInfoVo;

import java.util.List;

public interface ProductInfoService {
    //显示全部商品不分页
    List<ProductInfo> getAll();

    //分页功能实现
    PageInfo splitPage(int pageNum, int pageSize);

    int save(ProductInfo info);

    //按主键id查询商品

    ProductInfo getByID(int pid);


    //更新商品
    int update(ProductInfo productInfo);

    //单个上删除
    int delete(int pid);


    //批量删除商品
    int deleteBatch(String[] ids);

    //多商品查询
    List<ProductInfo> selectCondition(ProductInfoVo vo);


    //多条件查询分页
    public PageInfo splitPageVo(ProductInfoVo vo, int pageSize);
}
