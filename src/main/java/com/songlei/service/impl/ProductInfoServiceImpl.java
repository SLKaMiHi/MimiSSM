package com.songlei.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.songlei.mapper.ProductInfoMapper;
import com.songlei.pojo.ProductInfo;
import com.songlei.pojo.ProductInfoExample;
import com.songlei.pojo.vo.ProductInfoVo;
import com.songlei.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    //业务逻辑层一定有数据访问层的对象
    @Autowired
    ProductInfoMapper productInfoMapper;

    @Override
    public List<ProductInfo> getAll() {

        return productInfoMapper.selectByExample(new ProductInfoExample());
    }

    @Override
    public PageInfo splitPage(int pageNum, int pageSize) {
        //分页插件使用PageHelper工具类实现分页设置
        PageHelper.startPage(pageNum,pageSize);

        //进行PageInfo的数据封装
        //进行有条件查询必须创建条件封装的，ProductInfoExample对象
        ProductInfoExample example = new ProductInfoExample();

        //设置排序，按主键的降序排序
        //select * from product_info order by p_id desc
        example.setOrderByClause("p_id desc");

        //设置完后可以取集合

        List<ProductInfo> list = productInfoMapper.selectByExample(example);

        //将查到的集合封装进PageInfo中
        PageInfo<ProductInfo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public int save(ProductInfo info) {
        return productInfoMapper.insert(info);

    }

    @Override
    public ProductInfo getByID(int pid) {
        return productInfoMapper.selectByPrimaryKey(pid);
    }

    @Override
    public int update(ProductInfo info) {

        return productInfoMapper.updateByPrimaryKey(info);
    }

    @Override
    public int delete(int pid) {

        return productInfoMapper.deleteByPrimaryKey(pid);
    }

    @Override
    public int deleteBatch(String[] ids) {

        return productInfoMapper.deleteBatch(ids);
    }

    @Override
    public List<ProductInfo> selectCondition(ProductInfoVo vo) {

        return productInfoMapper.selectCondition(vo);
    }

    @Override
    public PageInfo splitPageVo(ProductInfoVo vo, int pageSize) {
        //取出集合之前要设置PageHelper.startPage（）
        PageHelper.startPage(vo.getPage(),pageSize);
        List<ProductInfo> list = productInfoMapper.selectCondition(vo);

        return new PageInfo<>(list);
    }
}
