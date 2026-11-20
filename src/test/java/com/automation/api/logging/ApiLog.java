package com.automation.api.logging;

import io.restassured.filter.Filter;
import io.restassured.response.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public final class ApiLog {

    private static final Logger LOGGER =
            Logger.getLogger(ApiLog.class.getName());

    private static FileHandler fileHandler;

    private ApiLog() {
    }

    public static void open() throws IOException {
        Path logFile = Path.of("target", "logs", "api-tests.log");
        Files.createDirectories(logFile.getParent());

        fileHandler = new FileHandler(logFile.toString(), false);
        fileHandler.setFormatter(new SimpleFormatter());
        LOGGER.setUseParentHandlers(false);
        LOGGER.addHandler(fileHandler);
    }

    public static void close() {
        if (fileHandler != null) {
            LOGGER.removeHandler(fileHandler);
            fileHandler.close();
            fileHandler = null;
        }
    }

    public static void info(String message) {
        LOGGER.info(message);
    }

    public static Filter httpFilter() {
        return (request, responseSpec, context) -> {
            long started = System.nanoTime();

            try {
                Response response = context.next(request, responseSpec);
                long elapsedMs = TimeUnit.NANOSECONDS.toMillis(
                        System.nanoTime() - started);

                LOGGER.info(() -> "HTTP %s status=%d durationMs=%d".formatted(
                        request.getMethod(), response.statusCode(), elapsedMs));
                return response;
            } catch (RuntimeException error) {
                long elapsedMs = TimeUnit.NANOSECONDS.toMillis(
                        System.nanoTime() - started);

                LOGGER.warning(() -> "HTTP %s error=%s durationMs=%d".formatted(
                        request.getMethod(),
                        error.getClass().getSimpleName(),
                        elapsedMs));
                throw error;
            }
        };
    }
}