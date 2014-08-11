package com.techempower.blog.views;

import io.dropwizard.views.View;

import com.techempower.blog.representations.Blog;

public class BlogView extends View
{
	private final Blog blog;
	
	public BlogView(Blog usr)
	{
		super("/views/blog.mustache");
		blog = usr;
	}

	public Blog getBlog()
	{
		return blog;
	}
}
