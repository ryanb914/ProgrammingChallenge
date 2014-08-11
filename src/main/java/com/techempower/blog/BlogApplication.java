package com.techempower.blog;

import org.skife.jdbi.v2.DBI;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.auth.basic.BasicAuthProvider;
import io.dropwizard.views.ViewBundle;
import com.sun.jersey.api.client.Client;

import com.techempower.blog.resources.BlogResource;
import com.techempower.blog.resources.ClientResource;


public class BlogApplication extends Application<BlogConfiguration>
{
    public static void main(String[] args) throws Exception
    {
        new BlogApplication().run(args);
    }

	@Override
    public String getName()
    {
        return "techempower-blog";
    }

    @Override
    public void initialize(Bootstrap<BlogConfiguration> bootstrap)
    {
        bootstrap.addBundle(new ViewBundle());
    }

    @Override
    public void run(BlogConfiguration configuration, Environment environment) throws Exception
    {
		// Create a DBI factory and build a JDBI instance
		final DBIFactory factory = new DBIFactory();
		final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "mysql");

		// Add blog resource to the environment
		environment.jersey().register(new BlogResource(jdbi, environment.getValidator()));

		// Create and add the client resource to the environment
		final Client client = new JerseyClientBuilder(environment).build("REST Client");
		environment.jersey().register(new ClientResource(client));

		// Redister the authenticator with the environmnet
		environment.jersey().register(new BasicAuthProvider<Boolean>(new BlogAuthenticator(jdbi), "Web Service Realm"));
    }
}
