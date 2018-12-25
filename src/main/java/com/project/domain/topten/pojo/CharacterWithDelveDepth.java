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
@JsonPropertyOrder({
	"name",
	"level",
	"class",
	"id",
	"depth"
})
		
public class CharacterWithDelveDepth {

	@JsonProperty("name")
	private String name;
	@JsonProperty("level")
	private Integer level;
	@JsonProperty("class")
	private String ascendancy;
	@JsonProperty("id")
	private String id;
	@JsonProperty("depth")
	private Depth depth;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("name")
	public String getName() {
	return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
	this.name = name;
	}

	@JsonProperty("level")
	public Integer getLevel() {
	return level;
	}

	@JsonProperty("level")
	public void setLevel(Integer level) {
	this.level = level;
	}

	@JsonProperty("id")
	public String getId() {
	return id;
	}

	@JsonProperty("id")
	public void setId(String id) {
	this.id = id;
	}

	@JsonProperty("depth")
	public Depth getDepth() {
	return depth;
	}
	@JsonProperty("class")
	public String getAscendancy() {
		return ascendancy;
	}
	@JsonProperty("class")
	public void setAscendancy(String ascendancy) {
		this.ascendancy = ascendancy;
	}

	@JsonAnySetter
	public void setAdditionalProperties(Map<String, Object> additionalProperties) {
		this.additionalProperties = additionalProperties;
	}

	@JsonProperty("depth")
	public void setDepth(Depth depth) {
	this.depth = depth;
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
