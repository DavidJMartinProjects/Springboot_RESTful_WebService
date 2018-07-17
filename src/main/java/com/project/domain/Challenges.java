package com.project.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Challenges {
	@JsonProperty("total")
	private String total;

	@JsonProperty("total")
	public String getTotal() {
		return total;
	}

	@JsonProperty("total")
	public void setTotal(String total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "ClassPojo [total = " + total + "]";
	}
}
