package com.pingtiao51.armsmodule.mvp.model.entity.response;

public class ProductPriceResponse {

    /**
     * origPrice : 10
     * discountPrice : 0.11
     */

    private int origPrice;
    private double discountPrice;

    public int getOrigPrice() {
        return origPrice;
    }

    public void setOrigPrice(int origPrice) {
        this.origPrice = origPrice;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }
}
