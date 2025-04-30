package ru.otus.java.basic.http.server.processors;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.java.basic.http.server.HttpRequest;
import ru.otus.java.basic.http.server.application.ItemsRepository;


public class DeleteItemProcessor implements RequestProcessor {
    private static final Logger logger = LogManager.getLogger(DeleteItemProcessor.class);
    private final ItemsRepository itemsRepository;

    public DeleteItemProcessor(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        long id = request.getAndValidateParam("id", Long::parseLong,
                "не является числом long");
        String response;
        if (itemsRepository.deleteItem(id)) {
            logger.info("Удален товар, id = {}", id);
            response = "HTTP/1.1 204 No Content\r\n" +
                    "Content-Type: application/json\r\n" +
                    "\r\n";
        } else {
            logger.error("Не удалось удалить товар, id = {}", id);
            response = "HTTP/1.1 404 Not Found\r\n" +
                    "Content-Type: application/json\r\n" +
                    "\r\n";
        }
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
