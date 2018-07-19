package com.project.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ladder {
	@JsonProperty("total")
	private String total;

	@JsonProperty("entries")
	private Entries[] entries;

	@JsonProperty("cached_since")
	private String cached_since;

	@JsonProperty("total")
	public String getTotal() {
		return total;
	}

	@JsonProperty("total")
	public void setTotal(String total) {
		this.total = total;
	}

	@JsonProperty("entries")
	public Entries[] getEntries() {
		return entries;
	}

	@JsonProperty("entries")
	public void setEntries(Entries[] entries) {
		this.entries = entries;
	}

	@JsonProperty("cached_since")
	public String getCached_since() {
		return cached_since;
	}

	@JsonProperty("cached_since")
	public void setCached_since(String cached_since) {
		this.cached_since = cached_since;
	}

	@Override
	public String toString() {
		return "ClassPojo [total = " + total + ", entries = " + entries + ", cached_since = " + cached_since + "]";
	}
}
