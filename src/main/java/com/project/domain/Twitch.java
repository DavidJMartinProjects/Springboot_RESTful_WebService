package com.project.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Twitch
{
	@JsonProperty("name")
    private String name;

	@JsonProperty("name")
    public String getName ()
    {
        return name;
    }

	@JsonProperty("name")
    public void setName (String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [name = "+name+"]";
    }
}
