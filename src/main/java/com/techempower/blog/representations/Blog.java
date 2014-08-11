package com.techempower.blog.representations;

import org.hibernate.validator.constraints.*;

public class Blog
{
	private final int id;

	@NotBlank
	@Length(min=2, max=255)
	private final String blogName;

	@NotBlank
	@Length(min=2, max=255)
	private final String blogText;

	public Blog(int identification, String name, String text)
	{
		id = identification;
		blogName = name;
		blogText = text;
	}

	public int getId()
	{
		return id;
	}

	public String getBlogName()
	{
		return blogName;
	}

	public String getBlogText()
	{
		return blogText;
	}
}
