package com.techempower.blog.dao;

import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import com.techempower.blog.representations.Blog;
import com.techempower.blog.dao.mappers.BlogMapper;

public interface BlogDAO
{
	@Mapper(BlogMapper.class)
	@SqlQuery("select * from blog where id = :id")
	Blog getBlogById(@Bind("id") int id);

	@GetGeneratedKeys
    @SqlUpdate("insert into blog (id, blogName, blogText) values (NULL, :blogName, :blogText)")
    int createBlog(@Bind("blogName") String blogName, @Bind("blogText") String blogText);

    @SqlUpdate("update blog set blogName = :blogName, blogText = :blogText, where id = :id")
    void updateBlog(@Bind("id") int id, @Bind("blogName") String blogName, @Bind("blogText") String blogText);

    @SqlUpdate("delete blog where id = :id")
    void deleteBlog(@Bind("id") int id);
}
