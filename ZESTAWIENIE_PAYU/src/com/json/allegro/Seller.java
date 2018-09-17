
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
    "id",
    "company",
    "superSeller"
})
public class Seller {

    @JsonProperty("id")
    private String id;
    @JsonProperty("company")
    private Boolean company;
    @JsonProperty("superSeller")
    private Boolean superSeller;
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

    @JsonProperty("company")
    public Boolean getCompany() {
        return company;
    }

    @JsonProperty("company")
    public void setCompany(Boolean company) {
        this.company = company;
    }

    @JsonProperty("superSeller")
    public Boolean getSuperSeller() {
        return superSeller;
    }

    @JsonProperty("superSeller")
    public void setSuperSeller(Boolean superSeller) {
        this.superSeller = superSeller;
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
