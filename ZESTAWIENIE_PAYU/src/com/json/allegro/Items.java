
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
    "promoted",
    "regular"
})
public class Items {

    @JsonProperty("promoted")
    private List<Promoted> promoted = null;
    @JsonProperty("regular")
    private List<Regular> regular = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("promoted")
    public List<Promoted> getPromoted() {
        return promoted;
    }

    @JsonProperty("promoted")
    public void setPromoted(List<Promoted> promoted) {
        this.promoted = promoted;
    }

    @JsonProperty("regular")
    public List<Regular> getRegular() {
        return regular;
    }

    @JsonProperty("regular")
    public void setRegular(List<Regular> regular) {
        this.regular = regular;
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
