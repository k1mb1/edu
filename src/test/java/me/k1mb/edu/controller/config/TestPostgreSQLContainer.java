package me.k1mb.edu.controller.config;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class TestPostgreSQLContainer extends PostgreSQLContainer<TestPostgreSQLContainer> {

    static final String IMAGE_VERSION = "postgres:16";
    static TestPostgreSQLContainer container;

    private TestPostgreSQLContainer() {
        super(DockerImageName.parse(IMAGE_VERSION));
    }

    public static TestPostgreSQLContainer getInstance() {
        if (container == null) {
            container = new TestPostgreSQLContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        // Не выключаем контейнер между тестами, чтобы ускорить процесс
    }
}
