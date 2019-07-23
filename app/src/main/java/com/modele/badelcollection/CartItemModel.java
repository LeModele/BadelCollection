package com.modele.badelcollection;

public class CartItemModel {

    public static final int CART_ITEM = 0;
    public static final int TOTAL_AMOUNT = 1;

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    /////////// cart item
    private int productImage;
    private String productTitle;
    private int freeCoupens;
    private String prouductPrice;
    private String cuttedPrice;
    private int productQuantity;
    private int offersApplied;
    private int coupensApplied;

    public CartItemModel(int type, int productImage, String productTitle, int freeCoupens, String prouductPrice, String cuttedPrice, int productQuantity, int offersApplied, int coupensApplied) {
        this.type = type;
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.freeCoupens = freeCoupens;
        this.prouductPrice = prouductPrice;
        this.cuttedPrice = cuttedPrice;
        this.productQuantity = productQuantity;
        this.offersApplied = offersApplied;
        this.coupensApplied = coupensApplied;
    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public int getFreeCoupens() {
        return freeCoupens;
    }

    public void setFreeCoupens(int freeCoupens) {
        this.freeCoupens = freeCoupens;
    }

    public String getProuductPrice() {
        return prouductPrice;
    }

    public void setProuductPrice(String prouductPrice) {
        this.prouductPrice = prouductPrice;
    }

    public String getCuttedPrice() {
        return cuttedPrice;
    }

    public void setCuttedPrice(String cuttedPrice) {
        this.cuttedPrice = cuttedPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getOffersApplied() {
        return offersApplied;
    }

    public void setOffersApplied(int offersApplied) {
        this.offersApplied = offersApplied;
    }

    public int getCoupensApplied() {
        return coupensApplied;
    }

    public void setCoupensApplied(int coupensApplied) {
        this.coupensApplied = coupensApplied;
    }

    /////////// cart item

    ////// cart total
    private String totalItem;
    private String totalItemPrice;
    private String deliveryPrice;
    private String saveAmount;
    private String totalAmount;

    public CartItemModel(int type, String totalItem, String totalItemPrice, String deliveryPrice,String totalAmount,String saveAmount) {
        this.totalAmount = totalAmount;
        this.type = type;
        this.totalItem = totalItem;
        this.totalItemPrice = totalItemPrice;
        this.deliveryPrice = deliveryPrice;
        this.saveAmount = saveAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(String totalItem) {
        this.totalItem = totalItem;
    }

    public String getTotalItemPrice() {
        return totalItemPrice;
    }

    public void setTotalItemPrice(String totalItemPrice) {
        this.totalItemPrice = totalItemPrice;
    }

    public String getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getSaveAmount() {
        return saveAmount;
    }

    public void setSaveAmount(String saveAmount) {
        this.saveAmount = saveAmount;
    }

////// cart total


}
