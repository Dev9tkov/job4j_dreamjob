package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemStore {
    private static MemStore INST = new MemStore();
    private Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private static AtomicInteger POST_ID = new AtomicInteger(4);
    private static AtomicInteger CANDIDATE_ID = new AtomicInteger(4);


    private MemStore() {
        posts.put(1, new Post(1, "Junior Java Job", "desc for junior"));
        posts.put(2, new Post(2, "Middle Java Job", "desc for middle"));
        posts.put(3, new Post(3, "Senior Java Job", "desc for senior"));

        candidates.put(1, new Candidate(1, "Junior Java"));
        candidates.put(2, new Candidate(2, "Middle Java"));
        candidates.put(3, new Candidate(3, "Senior Java"));
    }

    public static MemStore instOf() {
        return INST;
    }

    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    public void save(Post post) {
        if (post.getId() == 0) {
            post.setId(POST_ID.incrementAndGet());
        }
        posts.put(post.getId(), post);
    }

    public Post findById(int id) {
        return posts.get(id);
    }

    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }

    public void saveCandidate(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(CANDIDATE_ID.incrementAndGet());
        }
        candidates.put(candidate.getId(), candidate);
    }
    public Candidate findByIdCan(int id) {
        return candidates.get(id);
    }

    public void delete(int id) throws IOException {
        deletePhoto(id);
        candidates.remove(id);
    }

    private void deletePhoto(int id) throws IOException {
        Candidate candidate = candidates.get(id);
        String photoId = candidate.getPhotoId();
        if (photoId != null) {
            Path pathToDelete = Paths.get("images" + File.separator + photoId);
            Files.delete(pathToDelete);
        }
    }
}
