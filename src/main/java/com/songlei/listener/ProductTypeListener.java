package com.songlei.listener;

import com.songlei.pojo.ProductType;
import com.songlei.service.ProductTypeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

@WebListener
//获取所有商品的类型列表，获取当前的Spring容器，从容器中取出所需的类
public class ProductTypeListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //手工从我们当前的Spring容器中取出ProductTypeServiceImpl的对象
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext_*.xml");
        ProductTypeService productTypeService = (ProductTypeService) context.getBean("ProductTypeServiceImpl");
        List<ProductType> typeList = productTypeService.getAll();

        //放入全局应用作用域中，供新增页面，修改页面，前台的查询功能提高全部类别
        servletContextEvent.getServletContext().setAttribute("typelist",typeList);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
