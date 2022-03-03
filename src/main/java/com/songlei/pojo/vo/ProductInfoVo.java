package com.songlei.pojo.vo;

public class ProductInfoVo {
    //商品名称
    private String pname;

    //商品类型
    private Integer typeid;
    //商品最低价格
    private Integer lprice;
    private Integer page=1;



    //商品最高
    private Integer hprice;

    public ProductInfoVo() {
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }


    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public Integer getLprice() {
        return lprice;
    }

    public void setLprice(Integer lprice) {
        this.lprice = lprice;
    }

    public Integer getHprice() {
        return hprice;
    }

    public void setHprice(Integer hprice) {
        this.hprice = hprice;
    }

    @Override
    public String toString() {
        return "ProductInfoVo{" +
                "pname='" + pname + '\'' +
                ", typeid=" + typeid +
                ", lprice=" + lprice +
                ", page=" + page +
                ", hprice=" + hprice +
                '}';
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public ProductInfoVo(String pname, Integer typeid, Integer lprice, Integer page, Integer hprice) {
        this.pname = pname;
        this.typeid = typeid;
        this.lprice = lprice;
        this.page = page;
        this.hprice = hprice;
    }
}
