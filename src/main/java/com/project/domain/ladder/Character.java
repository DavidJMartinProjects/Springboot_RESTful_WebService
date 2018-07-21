package com.project.domain.ladder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Character {
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("class")
	private String theClass;

	@JsonProperty("level")
	private String level;

	@JsonProperty("name")
	private String name;

	@JsonProperty("experience")
	private String experience;

	@JsonProperty("id")
	public String getId() {
		return id;
	}
	
	@JsonProperty("id")
	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty("class")
	public String getTheClass() {
		return theClass;
	}
	
	@JsonProperty("class")
	public void setTheClass(String theClass) {
		this.theClass = theClass;
	}

	@JsonProperty("level")
	public String getLevel() {
		return level;
	}

	@JsonProperty("level")
	public void setLevel(String level) {
		this.level = level;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("experience")
	public String getExperience() {
		return experience;
	}

	@JsonProperty("experience")
	public void setExperience(String experience) {
		this.experience = experience;
	}

	@Override
	public String toString() {
		return "Character [id=" + id + ", class=" + theClass + ", level=" + level + ", name=" + name
				+ ", experience=" + experience + "]";
	}

}
