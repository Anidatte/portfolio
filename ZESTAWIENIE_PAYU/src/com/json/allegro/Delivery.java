
package com.json.allegro;

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
    "availableForFree",
    "lowestPrice"
})
public class Delivery {

    @JsonProperty("availableForFree")
    private Boolean availableForFree;
    @JsonProperty("lowestPrice")
    private LowestPrice lowestPrice;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("availableForFree")
    public Boolean getAvailableForFree() {
        return availableForFree;
    }

    @JsonProperty("availableForFree")
    public void setAvailableForFree(Boolean availableForFree) {
        this.availableForFree = availableForFree;
    }

    @JsonProperty("lowestPrice")
    public LowestPrice getLowestPrice() {
        return lowestPrice;
    }

    @JsonProperty("lowestPrice")
    public void setLowestPrice(LowestPrice lowestPrice) {
        this.lowestPrice = lowestPrice;
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
