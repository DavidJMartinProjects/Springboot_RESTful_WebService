package com.project.topten.pojo;

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
@JsonPropertyOrder({ "total", "title", "startTime", "entries" })

public class UberLabLadderResponseLadder {

	@JsonProperty("total")
	private Integer total;
	@JsonProperty("title")
	private String title;
	@JsonProperty("startTime")
	private Integer startTime;
	@JsonProperty("entries")
	private List<UberLabResponseEntry> entries = null;
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

	@JsonProperty("title")
	public String getTitle() {
		return title;
	}

	@JsonProperty("title")
	public void setTitle(String title) {
		this.title = title;
	}

	@JsonProperty("startTime")
	public Integer getStartTime() {
		return startTime;
	}

	@JsonProperty("startTime")
	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}

	@JsonProperty("entries")
	public List<UberLabResponseEntry> getEntries() {
		return entries;
	}

	@JsonProperty("entries")
	public void setEntries(List<UberLabResponseEntry> entries) {
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
