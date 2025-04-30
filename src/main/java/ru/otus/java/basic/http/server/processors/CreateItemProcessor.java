package ru.otus.java.basic.http.server.processors;

import com.google.gson.Gson;
import ru.otus.java.basic.http.server.HttpRequest;
import ru.otus.java.basic.http.server.application.Item;
import ru.otus.java.basic.http.server.application.ItemsRepository;
import ru.otus.java.basic.http.server.exceptions.BadRequestException;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class CreateItemProcessor implements RequestProcessor {
    private final ItemsRepository itemsRepository;

    public CreateItemProcessor(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        Gson gson = new Gson();
        Item newItem = gson.fromJson(request.getBody(), Item.class);

        if (newItem == null || newItem.getTitle() == null || newItem.getTitle().isEmpty()) {
            throw new BadRequestException(
                    "INCORRECT_REQUEST_PARAMETER",
                    "Название не может быть пустым"
            );
        }
        if (newItem.getPrice().signum() < 0) {
            throw new BadRequestException(
                    "INCORRECT_REQUEST_PARAMETER",
                    "Цена не может быть отрицательной"
            );
        }
        itemsRepository.addNewItem(newItem);
        String response = "HTTP/1.1 201 Created\r\n" +
                "Content-Type: application/json\r\n" +
                "\r\n";
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
