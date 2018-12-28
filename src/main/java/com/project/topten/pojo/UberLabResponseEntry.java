package com.project.topten.pojo;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.project.domain.ladder.Account;
import com.project.domain.ladder.Character;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "online", "rank", "time", "character", "account" })
public class UberLabResponseEntry {

	@JsonProperty("online")
	private Boolean online;
	@JsonProperty("rank")
	private Integer rank;
	@JsonProperty("time")
	private Integer time;
	@JsonProperty("character")
	private Character character;
	@JsonProperty("account")
	private Account account;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("online")
	public Boolean getOnline() {
		return online;
	}

	@JsonProperty("online")
	public void setOnline(Boolean online) {
		this.online = online;
	}

	@JsonProperty("rank")
	public Integer getRank() {
		return rank;
	}

	@JsonProperty("rank")
	public void setRank(Integer rank) {
		this.rank = rank;
	}

	@JsonProperty("time")
	public Integer getTime() {
		return time;
	}

	@JsonProperty("time")
	public void setTime(Integer time) {
		this.time = time;
	}

	@JsonProperty("character")
	public Character getCharacter() {
		return character;
	}

	@JsonProperty("character")
	public void setCharacter(Character character) {
		this.character = character;
	}

	@JsonProperty("account")
	public Account getAccount() {
		return account;
	}

	@JsonProperty("account")
	public void setAccount(Account account) {
		this.account = account;
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