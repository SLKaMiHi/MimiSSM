package com.songlei.controller;

import com.github.pagehelper.PageInfo;
import com.songlei.pojo.ProductInfo;
import com.songlei.pojo.vo.ProductInfoVo;
import com.songlei.service.ProductInfoService;
import com.songlei.utils.FileNameUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/prod")
public class ProductInfoAction {
    //每页显示的记录数
    public static final int PAGE_SIZE = 5;

    //异步上传的图片的名称
    String saveFileName = "";



    @Autowired
    ProductInfoService productInfoService;


    //显示全部商品不分页
    @RequestMapping("/getAll")

    public String getAll(HttpServletRequest request){
        List<ProductInfo> list = productInfoService.getAll();
        request.setAttribute("list",list);
        return "product";
    }

    //显示第一页的五条记录
    @RequestMapping("split")
    public String split(HttpServletRequest request){
        PageInfo info = null;
        Object vo = request.getSession().getAttribute("prodVo");
        if(vo!=null){
            info = productInfoService.splitPageVo((ProductInfoVo) vo,PAGE_SIZE);
            request.getSession().removeAttribute("prodVo");
        }else{
            //获得第一页
            info = productInfoService.splitPage(1,PAGE_SIZE);
        }
        request.setAttribute("info", info);
        return "product";
    }


    //ajax分页的翻页处理
    @ResponseBody
    @RequestMapping("/ajaxSplit")
    public void ajaxSplit(ProductInfoVo vo, HttpSession session){
        //取得当前page参数的信息
        PageInfo info = productInfoService.splitPageVo(vo,PAGE_SIZE);
        //这里为什么用session
        session.setAttribute("info",info);


    }
//    //多条件查询的实现
//    @ResponseBody
//    @RequestMapping("/condition")
//    public  void condition(ProductInfoVo vo, HttpSession session){
//        List<ProductInfo> list  = productInfoService.selectCondition(vo);
//        session.setAttribute("list",list);
//
//    }


    //异步ajax文件上传
    @ResponseBody
    @RequestMapping("/ajaxImg")
    public Object ajaxImage(MultipartFile pimage,HttpServletRequest request){
        //提取生成文件名
        saveFileName = FileNameUtil.getUUIDFileName()+FileNameUtil.getFileType(pimage.getOriginalFilename());

        //得到项目中图片存储的路径
        String path = request.getServletContext().getRealPath("/image_big");


        //转存
        try {
            pimage.transferTo(new File(path+ File.separator+saveFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //返回客户端JSON对象，封装图片的路径，为了在页面上立即回显
        JSONObject object = new JSONObject();
        object.put("imgurl",saveFileName);


        return object.toString();
    }




    @RequestMapping("/save")
    public String save(ProductInfo info, HttpServletRequest request){
        //info中已有气他信息，补齐缺的信息
        info.setpImage(saveFileName);
        info.setpDate(new Date());

        //info对象中有表单提交的五个信息

        int num=-1;
        try {
            num = productInfoService.save(info);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(num>0){
            request.setAttribute("msg","增加成功");
        }else{
            request.setAttribute("msg","增加失败");
        }


        //应清空savefileName清空，方便下次更新
        saveFileName = "";

        //增加成功后应跳转到分页显示的action
        return "forward:/prod/split.action";
    }


    @RequestMapping("/one")
    public String one(int pid,ProductInfoVo vo,HttpSession session, Model model){

        ProductInfo info = productInfoService.getByID(pid);
        //要将info的信息传到update作为展示，所以放在model里
        model.addAttribute("prod", info);
        //将查询条件、页码放入session中，更新处理结束后分页时读取条件和页码进行处理
        session.setAttribute("prodVo",vo);

        //


        return  "update";
    }

    @RequestMapping("/update")
    public String update(ProductInfo info,HttpServletRequest request){
        //因为ajax一步图片上传，如果上传过，则saveFileName力有上传过来的名称，如果没有上传则名称为空
        if(!saveFileName.equals("")){
            info.setpImage(saveFileName);
        }//否则使用原来的图片名称
        //完成更新处理
        int num=-1;
        try {
            num = productInfoService.update(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(num>0){
            request.setAttribute("msg","更新成功");

        }else{
            request.setAttribute("msg","更新失败");
        }
        saveFileName = "";

        return "forward:/prod/split.action";
    }


    //删除单个商品
    @RequestMapping("/delete")
    public String delete(int pid, ProductInfoVo vo,HttpServletRequest request){
        int num=-1;
        try {
            num = productInfoService.delete(pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(num>0){
            request.setAttribute("msg","删除成功");
            request.getSession().setAttribute("deleteProdVo",vo);
        }else{
            request.setAttribute("msg","删除失败");
        }

        //因为需要返回值，所以不能使用ajaxSplit作为返回页面
        return "forward:/prod/deleteAjaxSplit.action";
    }
    @ResponseBody
    @RequestMapping(value = "/deleteAjaxSplit",produces = "text/html;charset=UTF-8")//设置弹框中中文流的编码
    public Object deleteAjaxSplit(HttpServletRequest request){
        PageInfo info = null;
        Object vo = request.getSession().getAttribute("deleteProdVo");
        if(vo!=null){
            info = productInfoService.splitPageVo((ProductInfoVo)vo,PAGE_SIZE);
            request.getSession().removeAttribute("deleteProdVo");
        }else {
            //取得第一页的数据
            info = productInfoService.splitPage(1, PAGE_SIZE);
        }
        request.getSession().setAttribute("info",info);
        return request.getAttribute("msg");

    }


    //批量删除商品
    @RequestMapping("/deleteBatch")
    public String deleteBatch(String pids,HttpServletRequest request, ProductInfoVo vo){
        //将上传的字符串转成字符数组
        String[] ps = pids.split(",");
        int num = -1;
        try {
            num = productInfoService.deleteBatch(ps);
            if(num>0){
                request.setAttribute("msg","批量删除成功");
                request.getSession().setAttribute("deleteProdVo",vo);
            }else{
                request.setAttribute("msg","批量删除失败");
            }
        } catch (Exception e) {
            request.setAttribute("msg","商品不可删除！");
        }

        return "forward:/prod/deleteAjaxSplit.action";

    }







}
