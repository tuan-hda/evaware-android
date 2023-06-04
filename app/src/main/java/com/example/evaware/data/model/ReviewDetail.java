package com.example.evaware.data.model;

import java.util.List;

public class ReviewDetail {
    @Override
    public String toString() {
        return "ReviewDetail{" +
                "imgUrls=" + imgUrls.size() +
                ", review=" + review +
                '}';
    }

    public ReviewDetail(List<ImageModel> imgUrls, ReviewModel review) {
        this.imgUrls = imgUrls;
        this.review = review;
    }

    public List<ImageModel> getImgUrls() {
        return imgUrls;
    }

    public ReviewModel getReview() {
        return review;
    }

    private List<ImageModel> imgUrls;
    private ReviewModel review;

    public String getDayCreate() {
        return dayCreate;
    }

    public void setDayCreate(String dayCreate) {
        this.dayCreate = dayCreate;
    }

    private String dayCreate;
}
