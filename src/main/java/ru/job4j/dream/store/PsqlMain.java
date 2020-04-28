package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

public class PsqlMain {
    public static void main(String[] args) {
        Store store = PsqlStore.instOf();
        store.save(new Post(0, "Java Job jun"));
        store.save(new Post(0, "Java Job middle"));
        store.save(new Post(0, "Java Job senior"));
        store.save(new Post(2, "Java Job TeamLead"));
        for (Post post : store.findAllPosts()) {
            System.out.println(post.getId() + " " + post.getName());
        }
        System.out.println(store.findById(2));
    }
}
