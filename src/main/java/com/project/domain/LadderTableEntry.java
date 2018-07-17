package com.project.domain;

public class LadderTableEntry {
	
	String name;
	String level;
	String experience;
	String challenges;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
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
		return "LadderTableEntry [name=" + name + ", level=" + level + ", experience=" + experience + ", challenges="
				+ challenges + "]";
	}	
	
}
