package com.project.domain;

public class LadderTableEntry {
	
	private String rank;
	private String character;
	private String account;
	private String level;
	private String theClass;
	private String skill;
	private String experience;
	private String challenges;
	
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
	
	public String getSkill() {
		return skill;
	}
	
	public void setSkill(String skill) {
		this.skill = skill;
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
	
	@Override
	public String toString() {
		return "LadderTableEntry [rank=" + rank + ", character=" + character + ", account=" + account + ", level="
				+ level + ", theClass=" + theClass + ", skill=" + skill + ", experience=" + experience + ", challenges="
				+ challenges + "]";
	}
	
}
