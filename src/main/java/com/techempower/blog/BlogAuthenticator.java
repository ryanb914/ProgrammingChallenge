package com.techempower.blog;

import org.skife.jdbi.v2.DBI;
import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

import com.techempower.blog.dao.UserDAO;

public class BlogAuthenticator implements Authenticator<BasicCredentials, Boolean>
{
	private final UserDAO userDao;

	public BlogAuthenticator(DBI jdbi)
	{
		userDao = jdbi.onDemand(UserDAO.class);
	}

    public Optional<Boolean> authenticate(BasicCredentials credentials) throws AuthenticationException
	{
		boolean validUser = (userDao.getUser(credentials.getUsername(), credentials.getPassword()) == 1);
        if (validUser)
		{
            return Optional.of(true);
        }
        return Optional.absent();
    }
}
