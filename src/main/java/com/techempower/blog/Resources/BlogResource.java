package com.techempower.blog.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.skife.jdbi.v2.DBI;
import io.dropwizard.auth.Auth;

import com.techempower.blog.representations.Blog;
import com.techempower.blog.dao.BlogDAO;

@Path("/blog")
@Produces(MediaType.APPLICATION_JSON)
public class BlogResource
{
	private final BlogDAO blogDao;
	private final Validator validator;

	public BlogResource(DBI jdbi, Validator valid)
	{
		blogDao = jdbi.onDemand(BlogDAO.class);
		validator = valid;
	}

	@GET
	@Path("/{id}")
	public Response getBlog(@PathParam("id") int id, @Auth Boolean isAuthenticated)
	{
		// Retrieve blog info with the given id
		Blog blog = blogDao.getBlogById(id);
		return Response.ok(blog).build();
	}

	@POST
	public Response createBlog(Blog blog, @Auth Boolean isAuthenticated) throws URISyntaxException
	{
		// Validate the blog info
		Set<ConstraintViolation<Blog>> violations = validator.validate(blog);

		// Are there any constraint violations?
		if (violations.size() > 0)
		{
			// Violation errors have occurred
			ArrayList<String> validationMessages = new ArrayList<String>();
			for (ConstraintViolation<Blog> violation : violations)
			{
				validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
			}
			return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
		}
		else
		{
			// No Violations
			// Store new blog
			int newBlogId = blogDao.createBlog(blog.getBlogName(), blog.getBlogText());
			return Response.created(new URI(String.valueOf(newBlogId))).build();
		}
	}

	@DELETE
	@Path("/{id}")
	public Response deleteBlog(@PathParam("id") int id, @Auth Boolean isAuthenticated)
	{
		
		return Response.noContent().build();
	}

	@PUT
	@Path("/{id}")
	public Response updateBlog(@PathParam("id") int id, Blog blog, @Auth Boolean isAuthenticated)
	{
		// Validate the updated blog info
		Set<ConstraintViolation<Blog>> violations = validator.validate(blog);

		// Are there any constraint violations?
		if (violations.size() > 0)
		{
			// Violation errors have occurred
			ArrayList<String> validationMessages = new ArrayList<String>();
			for (ConstraintViolation<Blog> violation : violations)
			{
				validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
			}
			return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
		}
		else
		{
			// No violations
			// Update the blog info at the provided id
			blogDao.updateBlog(id, blog.getBlogName(), blog.getBlogText());
			return Response.ok(new Blog(id, blog.getBlogName(), blog.getBlogText())).build();
		}
	}
}
