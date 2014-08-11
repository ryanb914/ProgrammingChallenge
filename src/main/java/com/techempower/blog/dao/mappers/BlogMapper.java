package com.techempower.blog.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.techempower.blog.representations.Blog;

public class BlogMapper implements ResultSetMapper<Blog>
{
	public Blog map(int index, ResultSet resultSet, StatementContext context) throws SQLException
	{
		return new Blog(resultSet.getInt("id"), resultSet.getString("blogName"), resultSet.getString("blogText"));
	}
}
