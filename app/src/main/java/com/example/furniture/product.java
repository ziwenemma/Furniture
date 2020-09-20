package com.example.furniture;
/**
 * product page
 */
public class product {
    String name,image,price,desc,pid;
    public product() {
    }
    public product(String name, String image, String price, String desc,String pid) {
        this.name = name;
        this.image = image;
        this.price=price;
        this.desc=desc;
        this.pid=pid;
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
}
