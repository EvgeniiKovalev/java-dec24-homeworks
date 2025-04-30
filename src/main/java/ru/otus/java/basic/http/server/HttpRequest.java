package ru.otus.java.basic.http.server;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.java.basic.http.server.exceptions.BadRequestException;

public class HttpRequest {
    @SuppressWarnings("checkstyle:ConstantName")
    private static final Logger logger = LogManager.getLogger(HttpRequest.class);
    private final String rawRequest;
    private final Map<String, String> parameters;
    private HttpMethod method;
    private String uri;
    private String body;

    public HttpRequest(String rawRequest) throws IllegalArgumentException {
        this.rawRequest = rawRequest;
        this.parameters = new HashMap<>();
        this.parse();
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getBody() {
        return body;
    }

    public String getUri() {
        return uri;
    }

    public String getRoutingKey() {
        return method + " " + uri;
    }

    public boolean containsParameter(String key) {
        return parameters.containsKey(key);
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    public <T> T getAndValidateParam(
            String paramName,
            Function<String, T> funcParse,
            String errorFuncMessage
    ) {
        if (!containsParameter(paramName)) {
            throw new BadRequestException(
                    "INCORRECT_REQUEST_DATA",
                    "Отсутствует параметр запроса '" + paramName + "'"
            );
        }

        String paramValue = getParameter(paramName);
        try {
            return funcParse.apply(paramValue);
        } catch (Exception e) {
            throw new BadRequestException(
                    "INCORRECT_REQUEST_PARAMETER",
                    "Параметр '" + paramName + "' " + errorFuncMessage
            );
        }
    }

    private void parse() throws IllegalArgumentException {
        int startIndex = rawRequest.indexOf(' ');
        int endIndex = rawRequest.indexOf(' ', startIndex + 1);
        String httpMethod = rawRequest.substring(0, startIndex);
        try {
            this.method = HttpMethod.valueOf(httpMethod);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Необрабатываемый HTTP метод \"" + httpMethod + "\"", e);
        }

        this.uri = rawRequest.substring(startIndex + 1, endIndex);
        if (uri.contains("?")) {
            String[] elements = uri.split("[?]");
            uri = elements[0];
            String[] keysValues = elements[1].split("[&]");
            for (String o : keysValues) {
                String[] keyValue = o.split("=");
                parameters.put(keyValue[0], keyValue[1]);
            }
        }

        switch (method) {
            case POST -> this.body = rawRequest.substring(rawRequest.indexOf("\r\n\r\n"));
            case DELETE -> {
                String[] elements = uri.split("[/]");
                parameters.put("id", elements[2]);
                this.uri = "/" + elements[1];
            }
        }
    }

    public void info(boolean showRawRequest) {
        if (showRawRequest) {
            logger.info(rawRequest);
        }
        logger.info("METHOD: {}", method);
        logger.info("URI: {}", uri);
        logger.info("PARAMETERS: {}", parameters);
        logger.info("BODY: {}", body);
    }
}