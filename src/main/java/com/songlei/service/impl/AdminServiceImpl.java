package com.songlei.service.impl;

import com.songlei.mapper.AdminMapper;
import com.songlei.pojo.Admin;
import com.songlei.pojo.AdminExample;
import com.songlei.service.AdminService;
import com.songlei.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//交给Spring创建类
@Service
public class AdminServiceImpl implements AdminService {

    //业务逻辑层中，一定有数据访问层的对象
    @Autowired
    AdminMapper adminMapper;


    @Override
    public Admin login(String name, String pwd) {


        //根据传入的用户名到DB中查询对应用户名
        //如果有条件，则一定要创建AdminExample的对象，用来封装调节
        AdminExample example = new AdminExample();

        /**如何添加条件
         *
         * select * from admin where a_name = 'admin'
         */

        //添加用户名a_name的条件
        example.createCriteria().andANameEqualTo(name);

        List<Admin> list = adminMapper.selectByExample(example);
        if(list.size()>0){
            Admin admin = list.get(0);
            //如果查询到对象，要比较密码
            String miPwd = MD5Util.getMD5(pwd);
            if(miPwd.equals(admin.getaPass())){
                return  admin;
            }
        }
        //如果查询到用户对象，则进行密码比对
        return null;
    }
}
