
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
    "emphasized",
    "bold",
    "highlight"
})
public class Promotion {

    @JsonProperty("emphasized")
    private Boolean emphasized;
    @JsonProperty("bold")
    private Boolean bold;
    @JsonProperty("highlight")
    private Boolean highlight;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("emphasized")
    public Boolean getEmphasized() {
        return emphasized;
    }

    @JsonProperty("emphasized")
    public void setEmphasized(Boolean emphasized) {
        this.emphasized = emphasized;
    }

    @JsonProperty("bold")
    public Boolean getBold() {
        return bold;
    }

    @JsonProperty("bold")
    public void setBold(Boolean bold) {
        this.bold = bold;
    }

    @JsonProperty("highlight")
    public Boolean getHighlight() {
        return highlight;
    }

    @JsonProperty("highlight")
    public void setHighlight(Boolean highlight) {
        this.highlight = highlight;
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
