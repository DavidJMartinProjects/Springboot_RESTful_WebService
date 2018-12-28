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

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "rank", "dead", "online", "character", "account" })
public class DelveLadderResponseEntry {

	@JsonProperty("rank")
	private Integer rank;
	@JsonProperty("dead")
	private Boolean dead;
	@JsonProperty("online")
	private Boolean online;
	@JsonProperty("character")
	private CharacterWithDelveDepth character;
	@JsonProperty("account")
	private Account account;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("rank")
	public Integer getRank() {
		return rank;
	}

	@JsonProperty("rank")
	public void setRank(Integer rank) {
		this.rank = rank;
	}

	@JsonProperty("dead")
	public Boolean getDead() {
		return dead;
	}

	@JsonProperty("dead")
	public void setDead(Boolean dead) {
		this.dead = dead;
	}

	@JsonProperty("online")
	public Boolean getOnline() {
		return online;
	}

	@JsonProperty("online")
	public void setOnline(Boolean online) {
		this.online = online;
	}

	@JsonProperty("character")
	public CharacterWithDelveDepth getCharacter() {
		return character;
	}

	@JsonProperty("character")
	public void setCharacter(CharacterWithDelveDepth character) {
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
