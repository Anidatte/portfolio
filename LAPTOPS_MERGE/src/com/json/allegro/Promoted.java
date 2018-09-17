
package com.json.allegro;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "seller",
    "promotion",
    "delivery",
    "images",
    "sellingMode",
    "stock",
    "category",
    "publication"
})
public class Promoted {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("seller")
    private Seller seller;
    @JsonProperty("promotion")
    private Promotion promotion;
    @JsonProperty("delivery")
    private Delivery delivery;
    @JsonProperty("images")
    private List<Image> images = null;
    @JsonProperty("sellingMode")
    private SellingMode sellingMode;
    @JsonProperty("stock")
    private Stock stock;
    @JsonProperty("category")
    private Category category;
    @JsonProperty("publication")
    private Publication publication;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("seller")
    public Seller getSeller() {
        return seller;
    }

    @JsonProperty("seller")
    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    @JsonProperty("promotion")
    public Promotion getPromotion() {
        return promotion;
    }

    @JsonProperty("promotion")
    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    @JsonProperty("delivery")
    public Delivery getDelivery() {
        return delivery;
    }

    @JsonProperty("delivery")
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    @JsonProperty("images")
    public List<Image> getImages() {
        return images;
    }

    @JsonProperty("images")
    public void setImages(List<Image> images) {
        this.images = images;
    }

    @JsonProperty("sellingMode")
    public SellingMode getSellingMode() {
        return sellingMode;
    }

    @JsonProperty("sellingMode")
    public void setSellingMode(SellingMode sellingMode) {
        this.sellingMode = sellingMode;
    }

    @JsonProperty("stock")
    public Stock getStock() {
        return stock;
    }

    @JsonProperty("stock")
    public void setStock(Stock stock) {
        this.stock = stock;
    }

    @JsonProperty("category")
    public Category getCategory() {
        return category;
    }

    @JsonProperty("category")
    public void setCategory(Category category) {
        this.category = category;
    }

    @JsonProperty("publication")
    public Publication getPublication() {
        return publication;
    }

    @JsonProperty("publication")
    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
