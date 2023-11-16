package vn.app.tconnect.models;

public class CartModel {
    String name;
    String price;

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

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getTongtien() {
        return tongtien;
    }

    public void setTongtien(int tongtien) {
        this.tongtien = tongtien;
    }

    String img_url;

    public CartModel(String name, String price, String img_url, int tongtien) {
        this.name = name;
        this.price = price;
        this.img_url = img_url;
        this.tongtien = tongtien;
    }

    int tongtien;

}
