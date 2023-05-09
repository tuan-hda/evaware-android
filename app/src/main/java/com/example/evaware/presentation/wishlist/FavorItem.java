package com.example.evaware.presentation.wishlist;

import com.example.evaware.R;

public class FavorItem {
    int image;
    Float price;
    String desc;

    public FavorItem(int image, Float price, String desc) {
        this.image = image;
        this.price = price;
        this.desc = desc;
    }

    public FavorItem() {
        this(R.drawable.product_2, 150.00F, "Wooden bedside table featuring a raised design");
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
