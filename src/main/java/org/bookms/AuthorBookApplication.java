package org.bookms;

import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;
import org.bookms.configmodule.AuthorBookConfiguration;
import org.bookms.healthcheck.HealthCheckController;
import org.bookms.healthcheck.SampleApplicationHealthCheck;
import org.bookms.model.Author;
import org.bookms.model.Book;
import io.dropwizard.client.JerseyClientBuilder;



import javax.ws.rs.client.Client;


@Slf4j
public class AuthorBookApplication extends Application<AuthorBookConfiguration> {

    private GuiceBundle<AuthorBookConfiguration> guiceBundle;

    private final HibernateBundle<AuthorBookConfiguration> hibernateBundle = new HibernateBundle<AuthorBookConfiguration>(Author.class, Book.class) {

        @Override
                public DataSourceFactory getDataSourceFactory(AuthorBookConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };

    public static void main(String[] args) throws Exception {
        new AuthorBookApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<AuthorBookConfiguration> bootstrap) {
        log.info("Guice App initializing... " + this.getClass().getPackage().getName());

        guiceBundle = GuiceBundle.<AuthorBookConfiguration>newBuilder()
                .addModule(new AuthorBookModule(bootstrap, hibernateBundle))
                .setConfigClass(AuthorBookConfiguration.class)
                .enableAutoConfig(this.getClass().getPackage().getName())
                .build();

        bootstrap.addBundle(guiceBundle);
    }

    @Override
    public String getName() {
        return "::: Author-Book-MicroService";
    }

    @Override
    public void run(AuthorBookConfiguration authorBookConfiguration, Environment environment) {
        final Client client = new JerseyClientBuilder(environment).build("RestClientController");
        environment.healthChecks().register("API_HealthCheck_Status",new SampleApplicationHealthCheck(client));
        environment.jersey().register(new HealthCheckController(environment.healthChecks()));
    }
}
