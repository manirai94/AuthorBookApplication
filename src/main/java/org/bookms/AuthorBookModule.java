package org.bookms;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;
import org.bookms.configmodule.AuthorBookConfiguration;
import org.bookms.dao.AuthorDAO;
import org.bookms.dao.BookDAO;
import org.bookms.dao.impl.AuthorDAOImpl;
import org.bookms.dao.impl.BookDAOImpl;
import org.bookms.service.AuthorService;
import org.bookms.service.BookService;
import org.bookms.service.impl.AuthorServiceImpl;
import org.bookms.service.impl.BookServiceImpl;
import org.hibernate.SessionFactory;

@Slf4j
public class AuthorBookModule extends AbstractModule {

    private final HibernateBundle hibernateBundle;

    public AuthorBookModule(Bootstrap<AuthorBookConfiguration> bootstrap, HibernateBundle hibernateBundle) {
        this.hibernateBundle = hibernateBundle;
        bootstrap.addBundle(this.hibernateBundle);
    }

    @Override
    protected void configure() {
        bind(AuthorDAO.class).to(AuthorDAOImpl.class);
        bind(BookDAO.class).to(BookDAOImpl.class);

        bind(AuthorService.class).to(AuthorServiceImpl.class);
        bind(BookService.class).to(BookServiceImpl.class);

    }

    @Provides
    public SessionFactory provideSessionFactory(AuthorBookConfiguration configuration, Environment environment) {
        return hibernateBundle.getSessionFactory();
    }
}
