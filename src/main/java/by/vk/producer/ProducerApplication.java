package by.vk.producer;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@SpringBootApplication
public class ProducerApplication {

  public static final String INIT_SCRIPT = "init.sql";

  public static void main(String[] args) {
    SpringApplication.run(ProducerApplication.class, args);
  }

  @Bean
  ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {

    var initializer = new ConnectionFactoryInitializer();
    initializer.setConnectionFactory(connectionFactory);
    initializer.setDatabasePopulator(
        new ResourceDatabasePopulator(new ClassPathResource(INIT_SCRIPT)));

    return initializer;
  }
}
