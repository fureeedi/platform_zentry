package com.edu.sena.zentry;

import com.edu.sena.zentry.config.AsyncSyncConfiguration;
import com.edu.sena.zentry.config.JacksonConfiguration;
import com.edu.sena.zentry.config.MongoDbTestContainer;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { ZentryApp.class, JacksonConfiguration.class, AsyncSyncConfiguration.class })
@ImportTestcontainers(MongoDbTestContainer.class)
public @interface IntegrationTest {}
