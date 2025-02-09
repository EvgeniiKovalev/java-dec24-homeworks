package ru.otus.java.basic.homeworks.homework17;

import java.util.*;
import java.util.stream.Stream;

public class PhoneBook {
    private final Map<String, Set<String>> mapPhonebook;

    public PhoneBook(int initialCapacity) {
        mapPhonebook = new HashMap<>(initialCapacity);
    }

    @Override
    public String toString() {
        return String.format("PhoneBook{mapPhonebook=%s}", mapPhonebook);
    }

    public void add(String fio, String phoneNumber) {
        if (!mapPhonebook.containsKey(fio)) {
            HashSet<String> set = new HashSet<>();
            if (set.add(phoneNumber)) {
                mapPhonebook.put(fio, set);
            }
        } else {
            mapPhonebook.get(fio).add(phoneNumber);
        }
    }

    /**
     * вернет значение по ключу
     *
     * @param fio - фио(ключ)
     * @return - значение по ключу
     */
    public HashSet<String> find(String fio) {
        return (HashSet<String>) mapPhonebook.get(fio);
    }

    /**
     * проверка наличия по значению(номеру телефона)
     *
     * @param phoneNumber - номер телефона
     */
    public boolean containsPhoneNumber(String phoneNumber) {
        for (Set<String> elem : mapPhonebook.values()) {
            if (elem.contains(phoneNumber)) {
                return true;
            }
        }
        return false;
    }

    /**
     * проверка наличия по ключу(фио)
     *
     * @param fio - фио
     */
    public boolean containsFio(String fio) {
        return mapPhonebook.containsKey(fio);
    }

    /**
     * цель - научиться итерировать и фильтровать пары ключ-значения.
     * напечатает пары ключ значение если в ключе присутствует фраза, заданная как параметр
     *
     * @param fio - фио
     */
    public void getEntryLikeByFio(String fio) {
        //вариант 1 фильтрация при помощи итератора по ключам, можно и с помощью for-each, оставил итератор, т.к. мало изучил итераторы
        Set<String> mapKeys = mapPhonebook.keySet();
        if (!mapKeys.isEmpty()) {
            Iterator<String> iter = mapKeys.iterator();
            while (iter.hasNext()) {
                String iterFio = iter.next();
                if (iterFio.contains(fio)) {
                    System.out.printf("вариант1: %s=%s\n", iterFio, mapPhonebook.get(iterFio));
                }
            }
        }
        //вариант 2 фильтрация по записям при помощи Stream API
        Stream<Map.Entry<String, Set<String>>> stream = mapPhonebook.entrySet().stream();
        stream.filter(entry -> entry.getKey().contains(fio)).forEach((elementHashMap) -> System.out.println("вариант2: " + elementHashMap));
    }
}