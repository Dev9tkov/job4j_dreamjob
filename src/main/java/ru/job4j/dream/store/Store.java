package ru.job4j.dream.store;

import ru.job4j.dream.model.Post;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Store {
    private static Store INST = new Store();
    private Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private Store() {
        posts.put(1, new Post(1, "Junior Java Job", "desc for junior", LocalDate.of(2020, Month.JANUARY, 04)));
        posts.put(2, new Post(2, "Middle Java Job", "desc for middle", LocalDate.of(2020, Month.FEBRUARY, 14)));
        posts.put(3, new Post(3, "Senior Java Job", "desc for senior", LocalDate.of(2020, Month.MARCH, 20)));
    }

    public static Store instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }
}
