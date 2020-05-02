package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.Collection;

public interface Store {
    Collection<Post> findAllPosts();

    Collection<Candidate> findAllCandidates();

    void save(Post post);

    Post findById(int id);

    void saveCandidate(Candidate candidate);

    void delete(int id);

    Candidate findByIdCan(int id);

    User findByEmail(String email);

    Collection<User> findAllUsers();

    void saveUser(User user);
}
