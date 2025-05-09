package com.demo.gradle;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.lifecycle.Startables;

public class TestContainerConfig
    implements ApplicationContextInitializer<ConfigurableApplicationContext> {
  // @Container标记的静态字段会被多个test case共享
  @Container
  public static PostgreSQLContainer postgreSQLContainer =
      new PostgreSQLContainer("postgres:16")
          .withDatabaseName("demo")
          .withUsername("postgres")
          .withPassword("postgres");

  static {
    // 这里可以启动多个container
    Startables.deepStart(postgreSQLContainer).join();
  }

  @Override
  public void initialize(ConfigurableApplicationContext applicationContext) {
    // 根据使其启动的container，重新设置api test的datasource。这不会音响业务的数据源
    TestPropertyValues.of(
            "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
            "spring.datasource.username=" + postgreSQLContainer.getUsername(),
            "spring.datasource.password=" + postgreSQLContainer.getPassword())
        .applyTo(applicationContext.getEnvironment());
  }
}
