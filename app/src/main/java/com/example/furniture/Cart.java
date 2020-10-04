package com.example.furniture;


public class Cart {
    private String product_id, pname,price,quantity,discount,time;

    public Cart() {
    }


    public Cart(String product_id, String pname, String price, String quantity, String discount, String time) {
        this.product_id = product_id;
        this.pname = pname;
        this.price = price;
        this.quantity = quantity;
        this.discount = discount;

        this.time=time;

    }


    public String getProduct_id() {
        return product_id;
    }


    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }


    public String getPname() {
        return pname;
    }



    public void setPname(String pname) {
        this.pname = pname;
    }



    public String getPrice() {
        return price;
    }


    public void setPrice(String price) {
        this.price = price;
    }



    public String getQuantity() {
        return quantity;
    }


    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }



    public String getDiscount() {
        return discount;
    }


    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
