package ru.otus.java.basic.http.server.application;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ItemsRepository {
    private final List<Item> items;

    public ItemsRepository() {
        this.items = new CopyOnWriteArrayList<>(Arrays.asList(
                new Item(1L, "Milk", BigDecimal.valueOf(92)),
                new Item(2L, "Bread", BigDecimal.valueOf(40)),
                new Item(3L, "Cheese", BigDecimal.valueOf(400))
        ));
    }

    public boolean deleteItem(long id) {
        return items.removeIf(e -> e.equals(findById(id)));
    }

    private Long nextId() {
        if (items == null || items.isEmpty()) {
            return 1L;
        }
        return Collections.max(items, Comparator.comparingLong(Item::getId)).getId() + 1;
    }

    public Item findById(Long id) {
        for (Item o : items) {
            if (o.getId().equals(id)) {
                return o;
            }
        }
        return null;
    }

    public List<Item> getAllItems() {
        return Collections.unmodifiableList(items);
    }

    public void addNewItem(Item item) {
        if (item.getId() == null) {
            item.setId(nextId());
        }
        items.add(item);
    }
}
