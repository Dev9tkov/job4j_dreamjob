package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

public class PsqlMain {
    public static void main(String[] args) {
        Store store = PsqlStore.instOf();
        store.save(new Post(0, "Java Job jun"));
        store.save(new Post(0, "Java Job middle"));
        store.save(new Post(0, "Java Job senior"));
        store.save(new Post(2, "Java Job TeamLead"));

        store.saveUser(new User(1, "Max", "loac@local", "qwer"));
        store.saveUser(new User(2, "Nel", "noac@mail", "twer"));
        store.saveUser(new User(3, "Yum", "moatre@yahoo", "oper"));

        System.out.println(store.findByEmail("loac@local"));
        System.out.println(store.findById(2));

        for (User user : store.findAllUsers()) {
            System.out.println(user.getId() + " " + user.getName() + " " + user.getEmail() + " " + user.getPassword());
        }

        for (Post post : store.findAllPosts()) {
            System.out.println(post.getId() + " " + post.getName());
        }
    }
}
