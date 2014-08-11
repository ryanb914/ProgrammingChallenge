package com.techempower.blog.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import com.sun.jersey.api.client.*;

import com.techempower.blog.representations.Blog;
import com.techempower.blog.views.BlogView;

@Produces(MediaType.TEXT_HTML)
@Path("/client/")
public class ClientResource
{
	private Client client;

	public ClientResource(Client clnt)
	{
		client = clnt;
	}

	@GET
	@Path("showBlog")
	public BlogView showBlog(@QueryParam("id") int id)
	{
		WebResource blogResource = client.resource("http://localhost:8080/blog/" + id);
		Blog blog = blogResource.get(Blog.class);
		return new BlogView(blog);
	}

	@GET
    @Path("newBlog")
    public Response newBlog(@QueryParam("blogName") String blogName, @QueryParam("blogText") String blogText)
	{
        WebResource blogResource = client.resource("http://localhost:8080/blog");
        ClientResponse response = blogResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, new Blog(0, blogName, blogText));
        if (response.getStatus() == 201)
		{
            return Response.status(302).entity("The blog was created successfully! The new blog can be found at " + response.getHeaders().getFirst("Location")).build();
        }
		else
		{
            return Response.status(422).entity(response.getEntity(String.class)).build();
        }
    }

	@GET
    @Path("updateBlog")
    public Response updateBlog(@QueryParam("id") int id, @QueryParam("blogName") String blogName, @QueryParam("blogText") String blogText)
	{
        WebResource blogResource = client.resource("http://localhost:8080/blog/" + id);
        ClientResponse response = blogResource.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, new Blog(id, blogName, blogText));
        if (response.getStatus() == 200)
		{
            return Response.status(302).entity("The blog was updated successfully!").build();
        }
		else
		{
            // Status code other than 200 indicates an error has occured
            return Response.status(422).entity(response.getEntity(String.class)).build();
        }
    }

	@GET
    @Path("deleteBlog")
    public Response deleteBlog(@QueryParam("id") int id)
	{
        WebResource blogResource = client.resource("http://localhost:8080/blog/" + id);
        blogResource.delete();
        return Response.noContent().entity("Blog was deleted!").build();
    }
}
