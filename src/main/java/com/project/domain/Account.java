package com.project.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {
	@JsonProperty("twitch")
	private Twitch twitch;

	@JsonProperty("challenges")
	private Challenges challenges;

	@JsonProperty("name")
	private String name;

	@JsonProperty("twitch")
	public Twitch getTwitch() {
		return twitch;
	}

	@JsonProperty("twitch")
	public void setTwitch(Twitch twitch) {
		this.twitch = twitch;
	}

	@JsonProperty("challenges")
	public Challenges getChallenges() {
		return challenges;
	}

	@JsonProperty("challenges")
	public void setChallenges(Challenges challenges) {
		this.challenges = challenges;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ClassPojo [twitch = " + twitch + ", challenges = " + challenges + ", name = " + name + "]";
	}
}
