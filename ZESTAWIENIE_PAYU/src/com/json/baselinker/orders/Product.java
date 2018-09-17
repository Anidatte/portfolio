
package com.json.baselinker.orders;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "storage_id",
    "product_id",
    "variant_id",
    "name",
    "sku",
    "ean",
    "auction_id",
    "attributes",
    "price_brutto",
    "tax_rate",
    "quantity",
    "weight"
})
public class Product {

    @JsonProperty("storage_id")
    private Long storageId;
    @JsonProperty("product_id")
    private String productId;
    @JsonProperty("variant_id")
    private Long variantId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("sku")
    private String sku;
    @JsonProperty("ean")
    private String ean;
    @JsonProperty("auction_id")
    private String auctionId;
    @JsonProperty("attributes")
    private String attributes;
    @JsonProperty("price_brutto")
    private Double priceBrutto;
    @JsonProperty("tax_rate")
    private Long taxRate;
    @JsonProperty("quantity")
    private Long quantity;
    @JsonProperty("weight")
    private Long weight;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("storage_id")
    public Long getStorageId() {
        return storageId;
    }

    @JsonProperty("storage_id")
    public void setStorageId(Long storageId) {
        this.storageId = storageId;
    }

    @JsonProperty("product_id")
    public String getProductId() {
        return productId;
    }

    @JsonProperty("product_id")
    public void setProductId(String productId) {
        this.productId = productId;
    }

    @JsonProperty("variant_id")
    public Long getVariantId() {
        return variantId;
    }

    @JsonProperty("variant_id")
    public void setVariantId(Long variantId) {
        this.variantId = variantId;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("sku")
    public String getSku() {
        return sku;
    }

    @JsonProperty("sku")
    public void setSku(String sku) {
        this.sku = sku;
    }

    @JsonProperty("ean")
    public String getEan() {
        return ean;
    }

    @JsonProperty("ean")
    public void setEan(String ean) {
        this.ean = ean;
    }

    @JsonProperty("auction_id")
    public String getAuctionId() {
        return auctionId;
    }

    @JsonProperty("auction_id")
    public void setAuctionId(String auctionId) {
        this.auctionId = auctionId;
    }

    @JsonProperty("attributes")
    public String getAttributes() {
        return attributes;
    }

    @JsonProperty("attributes")
    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    @JsonProperty("price_brutto")
    public Double getPriceBrutto() {
        return priceBrutto;
    }

    @JsonProperty("price_brutto")
    public void setPriceBrutto(Double priceBrutto) {
        this.priceBrutto = priceBrutto;
    }

    @JsonProperty("tax_rate")
    public Long getTaxRate() {
        return taxRate;
    }

    @JsonProperty("tax_rate")
    public void setTaxRate(Long taxRate) {
        this.taxRate = taxRate;
    }

    @JsonProperty("quantity")
    public Long getQuantity() {
        return quantity;
    }

    @JsonProperty("quantity")
    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @JsonProperty("weight")
    public Long getWeight() {
        return weight;
    }

    @JsonProperty("weight")
    public void setWeight(Long weight) {
        this.weight = weight;
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
