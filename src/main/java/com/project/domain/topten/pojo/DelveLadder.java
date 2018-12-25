package com.project.domain.topten.pojo;

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
	"total", 
	"entries"
})

public class DelveLadder {

	@JsonProperty("total")
	private Integer total;
	@JsonProperty("entries")
	private List<DelveLadderResponseEntry> entries = null;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("total")
	public Integer getTotal() {
	return total;
	}

	@JsonProperty("total")
	public void setTotal(Integer total) {
	this.total = total;
	}

	@JsonProperty("entries")
	public List<DelveLadderResponseEntry> getEntries() {
	return entries;
	}

	@JsonProperty("entries")
	public void setEntries(List<DelveLadderResponseEntry> entries) {
	this.entries = entries;
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
