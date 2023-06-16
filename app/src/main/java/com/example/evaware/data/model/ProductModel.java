package com.example.evaware.data.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.PropertyName;
import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;

public class ProductModel implements Serializable {
    public String getName() {
        return name;
    }

    @DocumentId
    public String id;
    public String path;

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public DocumentReference category_ref;
    private String img_url;
    @Exclude
    public Boolean saved = false;
    @ServerTimestamp

    public Timestamp created_at;
    @ServerTimestamp
    public Timestamp updated_at;
    public String desc;
    public DocumentReference discount_ref;
    public int discount;

    @PropertyName("img_url")
    public String image_thumbnail;
    public String name;
    public Double price;
    public DocumentReference room_ref;
    private int review_qty = 0;

    public int getReview_qty() {
        return review_qty;
    }

    public void setReview_qty(int review_qty) {
        this.review_qty = review_qty;
    }

    public String getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public DocumentReference getCategory_ref() {
        return category_ref;
    }

    public String getImg_url() {
        return img_url;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public String getDesc() {
        return desc;
    }

    public DocumentReference getDiscount_ref() {
        return discount_ref;
    }

    public String getImage_thumbnail() {
        return image_thumbnail;
    }

    public Double getPrice() {
        return price;
    }

    public DocumentReference getRoom_ref() {
        return room_ref;
    }

    public ProductModel() {
    }

    public ProductModel(String id, String path, DocumentReference category_ref, String img_url, Timestamp created_at, Timestamp updated_at, String desc, DocumentReference discount_ref, String image_thumbnail, String name, Double price, DocumentReference room_ref) {
        this.id = id;
        this.path = path;
        this.category_ref = category_ref;
        this.img_url = img_url;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.desc = desc;
        this.discount_ref = discount_ref;
        this.image_thumbnail = image_thumbnail;
        this.name = name;
        this.price = price;
        this.room_ref = room_ref;
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "id='" + id + '\'' +
                ", path='" + path + '\'' +
                ", category_ref=" + category_ref +
                ", img_url='" + img_url + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", desc='" + desc + '\'' +
                ", discount_ref=" + discount_ref +
                ", image_thumbnail='" + image_thumbnail + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", room_ref=" + room_ref +
                '}';
    }
}
