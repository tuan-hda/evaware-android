package com.example.evaware.data.model;

import java.util.ArrayList;
import java.util.List;

public class ProductDetail {
    public ProductDetail(ProductModel product, List<VariationModelsDetail> variationModelDetails) {
        this.product = product;
        this.variationModelDetails = variationModelDetails;
    }

    public ProductDetail() {
        variationModelDetails = new ArrayList<>();
    }

    private ProductModel product;

    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }

    public List<VariationModelsDetail> getVariationModelDetails() {
        return variationModelDetails;
    }

    public void setVariationModelDetails(List<VariationModelsDetail> variationModelDetails) {
        this.variationModelDetails = variationModelDetails;
    }

    private List<VariationModelsDetail> variationModelDetails;
}
