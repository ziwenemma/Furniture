package com.example.furniture;

/**
 * creating the cart
 * includes product id, product name, price, quantity, discount if any
 */
public class Cart {
    private String product_id, pname,price,quantity,discount;

    public Cart() {
    }

    public Cart(String product_id, String pname, String price, String quantity, String discount) {
        this.product_id = product_id;
        this.pname = pname;
        this.price = price;
        this.quantity = quantity;
        this.discount = discount;
    }

    /**
     *
     * @return getting the id
     */
    public String getProduct_id() {
        return product_id;
    }

    /**
     * returning id
     * @param product_id
     */

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    /**
     *
     * @return getting name
     */

    public String getPname() {
        return pname;
    }

    /**
     *
     * @param pname
     * setting name
     */

    public void setPname(String pname) {
        this.pname = pname;
    }

    /**
     *
     * @return price
      */

    public String getPrice() {
        return price;
    }

    /**
     * set price
     * @param price
     */

    public void setPrice(String price) {
        this.price = price;
    }

    /**
     *
     * @return number of products(quantity)
     */

    public String getQuantity() {
        return quantity;
    }

    /**
     * setting the quantity
     * @param quantity
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    /**
     *
     * @return discount
     */

    public String getDiscount() {
        return discount;
    }

    /**
     * set discount
     * @param discount
     */
    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
