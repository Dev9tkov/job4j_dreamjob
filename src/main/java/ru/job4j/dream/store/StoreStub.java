package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is needed for testing
 */
public class StoreStub implements Store {
    private final Map<Integer, Post> storePost = new HashMap<>();
    private final Map<Integer, Candidate> storeCandidate = new HashMap<>();
    private final Map<Integer, User> storeUser = new HashMap<>();

    private int idP = 0;
    private int idC = 0;
    private int idU = 0;

    @Override
    public void save(Post post) {
        post.setId(this.idP++);
        this.storePost.put(post.getId(), post);
    }

    @Override
    public void saveCandidate(Candidate candidate) {
        candidate.setId(this.idC++);
        this.storeCandidate.put(candidate.getId(), candidate);
    }

    @Override
    public void saveUser(User user) {
        user.setId(this.idU++);
        this.storeUser.put(user.getId(), user);
    }

    @Override
    public Collection<Post> findAllPosts() {
        return new ArrayList<>(this.storePost.values());
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        return new ArrayList<>(this.storeCandidate.values());
    }

    @Override
    public Collection<User> findAllUsers() {
        return new ArrayList<>(this.storeUser.values());
    }

    @Override
    public Post findById(int id) {
        return null;
    }

    @Override
    public void delete(int id) {
        this.storeCandidate.remove(id);
        idC--;
    }

    @Override
    public Candidate findByIdCan(int id) {
        return null;
    }

    @Override
    public User findByEmail(String email) {
        return null;
    }

    @Override
    public User findByIdUser(int id) {
        return null;
    }

    @Override
    public Collection<String> getCountry() {
        return null;
    }

    @Override
    public Collection<String> getCity(String country) {
        return null;
    }
}
