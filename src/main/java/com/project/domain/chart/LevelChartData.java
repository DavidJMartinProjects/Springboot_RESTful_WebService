package com.project.domain.chart;

public class LevelChartData {

	String level;
	String frequency;
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	public String getFrequency() {
		return frequency;
	}
	
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	
	@Override
	public String toString() {
		return "LevelChartData [level=" + level + ", frequency=" + frequency + "]";
	}

}
