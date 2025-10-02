package br.com.winter.testContainers;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;

import java.util.Map;
import java.util.stream.Stream;

@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public abstract class AbstractIntegrationTest {

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        static PostgreSQLContainer<?> postgresql = new PostgreSQLContainer<>("postgres:17.3")
                .withDatabaseName("testdb")
                .withUsername("test")
                .withPassword("test");

        private static void startContainers() {
            Startables.deepStart(Stream.of(postgresql)).join();
            // Log para debug
            System.out.println("🐘 PostgreSQL Container URL: " + postgresql.getJdbcUrl());
        }

        private static Map<String, Object> createConnectionConfiguration() {
            return Map.of(
                    "spring.datasource.url", postgresql.getJdbcUrl(),
                    "spring.datasource.username", postgresql.getUsername(),
                    "spring.datasource.password", postgresql.getPassword(),
                    "spring.jpa.hibernate.ddl-auto", "create-drop",
                    "server.port", "9082",
                    "server.address", "localhost",
                    "spring.jpa.show-sql", "true",
                    "spring.jpa.database-platform", "org.hibernate.dialect.PostgreSQLDialect",
                    "spring.jpa.defer-datasource-initialization", "false"
            );
        }

        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public void initialize(ConfigurableApplicationContext applicationContext) {
            startContainers();
            ConfigurableEnvironment environment = applicationContext.getEnvironment();

            MapPropertySource testContainers = new MapPropertySource(
                    "testContainers",
                    (Map) createConnectionConfiguration()
            );

            environment.getPropertySources().addFirst(testContainers);
            System.out.println("✅ TestContainers properties configured");
        }
    }
}