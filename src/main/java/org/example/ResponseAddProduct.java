package org.example;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "id",
            "name",
            "measures",
            "usages",
            "usageRecipeIds",
            "pantryItem",
            "aisle",
            "cost",
            "ingredientId"
    })

@Data
    public class ResponseAddProduct {

        @JsonProperty("id")
        public Integer id;
        @JsonProperty("name")
        public String name;
        @JsonProperty("measures")
        public Measures measures;
        @JsonProperty("usages")
        public List<Object> usages = null;
        @JsonProperty("usageRecipeIds")
        public List<Object> usageRecipeIds = null;
        @JsonProperty("pantryItem")
        public Boolean pantryItem;
        @JsonProperty("aisle")
        public String aisle;
        @JsonProperty("cost")
        public Double cost;
        @JsonProperty("ingredientId")
        public Integer ingredientId;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonAnyGetter
        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        @JsonAnySetter
        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

