package com.project.domain.topten.pojo;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "default", "solo" })
public class Depth {

	@JsonProperty("default")
	private Integer _default;
	@JsonProperty("solo")
	private Integer solo;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("default")
	public Integer getDefault() {
		return _default;
	}

	@JsonProperty("default")
	public void setDefault(Integer _default) {
		this._default = _default;
	}

	@JsonProperty("solo")
	public Integer getSolo() {
		return solo;
	}

	@JsonProperty("solo")
	public void setSolo(Integer solo) {
		this.solo = solo;
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