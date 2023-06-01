package com.example.evaware.data.model;

import java.util.ArrayList;
import java.util.List;

public class VariationModelsDetail {

    private VariationProductModel model;
    private List<String> listImgUrls;
    public VariationModelsDetail() {
        listImgUrls = new ArrayList<>();
    }

    public VariationProductModel getModel() {
        return model;
    }
    public void setModel(VariationProductModel model) {
        this.model = model;
    }

    public List<String> getListImgUrls() {
        return listImgUrls;
    }

    public void setListImgUrls(List<String> listImgUrls) {
        this.listImgUrls = listImgUrls;
    }

    public VariationModelsDetail(VariationProductModel model, List<String> listImgUrls) {
        this.model = model;
        this.listImgUrls = listImgUrls;
    }
}
