package com.example.furniture;

public class product {
    String name,image,price,desc,pid,rate,width;
    public product() {
    }
    public product(String name, String image, String price, String desc,String pid,String rate,String width) {
        this.name = name;
        this.image = image;
        this.price=price;
        this.desc=desc;
        this.pid=pid;
        this.rate=rate;
        this.width=width;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public String getPid() {
        return pid;
    }
    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}
