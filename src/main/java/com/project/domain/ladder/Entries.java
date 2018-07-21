package com.project.domain.ladder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Entries {
	@JsonProperty("rank")
	private String rank;

	@JsonProperty("account")
	private Account account;

	@JsonProperty("dead")
	private String dead;

	@JsonProperty("character")
	private Character character;

	@JsonProperty("online")
	private String online;

	@JsonProperty("rank")
	public String getRank() {
		return rank;
	}

	@JsonProperty("rank")
	public void setRank(String rank) {
		this.rank = rank;
	}

	@JsonProperty("account")
	public Account getAccount() {
		return account;
	}

	@JsonProperty("account")
	public void setAccount(Account account) {
		this.account = account;
	}

	@JsonProperty("dead")
	public String getDead() {
		return dead;
	}

	@JsonProperty("dead")
	public void setDead(String dead) {
		this.dead = dead;
	}

	@JsonProperty("character")
	public Character getCharacter() {
		return character;
	}

	@JsonProperty("character")
	public void setCharacter(Character character) {
		this.character = character;
	}

	@JsonProperty("online")
	public String getOnline() {
		return online;
	}

	@JsonProperty("online")
	public void setOnline(String online) {
		this.online = online;
	}

	@Override
	public String toString() {
		return "ClassPojo [rank = " + rank + ", account = " + account + ", dead = " + dead + ", character = "
				+ character + ", online = " + online + "]";
	}
}
