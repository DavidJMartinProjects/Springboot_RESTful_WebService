package com.project.domain.datatable;

public class LadderTableEntry {

	private String rank;
	private String character;
	private String dead;
	private String account;
	private String level;
	private String theClass;
	private String experience;
	private String challenges;
	private String online;
	private String twitch;
	private String xph;
	private String xphDifference;
	private String timeStamp;

	public LadderTableEntry() {
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getCharacter() {
		return character;
	}

	public void setCharacter(String character) {
		this.character = character;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getTheClass() {
		return theClass;
	}

	public void setTheClass(String theClass) {
		this.theClass = theClass;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getChallenges() {
		return challenges;
	}

	public void setChallenges(String challenges) {
		this.challenges = challenges;
	}

	public String getDead() {
		return dead;
	}

	public void setDead(String dead) {
		this.dead = dead;
	}

	public String getOnline() {
		return online;
	}

	public void setOnline(String online) {
		this.online = online;
	}

	public String getTwitch() {
		return twitch;
	}

	public void setTwitch(String twitch) {
		this.twitch = twitch;
	}

	public String getXph() {
		return xph;
	}

	public void setXph(String xph) {
		this.xph = xph;
	}

	public String getXphDifference() {
		return xphDifference;
	}

	public void setXphDifference(String xphDifference) {
		this.xphDifference = xphDifference;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	@Override
	public String toString() {
		return "LadderTableEntry [rank=" + rank + ", character=" + character + ", dead=" + dead + ", account=" + account + ", level=" + level + ", theClass=" + theClass + ", experience=" + experience
				+ ", challenges=" + challenges + ", online=" + online + ", twitch=" + twitch + ", xph=" + xph + ", xphDifference=" + xphDifference + "]";
	}

}
