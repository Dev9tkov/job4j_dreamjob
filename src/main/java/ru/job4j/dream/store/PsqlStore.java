package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.util.*;

public class PsqlStore implements Store {

    /**
     * database connection pooling
     */
    private static final BasicDataSource pool = new BasicDataSource();

    private static PsqlStore INST = new PsqlStore();

    /**
     * configure the database from db.properties
     * setMinIdle - set minimum number of connections
     * setMaxIdle - set maximum number of connections
     */
    public PsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new FileReader("db.properties")
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);

        createTables();
    }

    /**
     * Create tables Post & Candidate if not exist
     */
    private void createTables() {
        try (BufferedReader br = new BufferedReader(
                new FileReader("db" + File.separator + "schema.sql"))) {
            String line;
            try (Connection cn = pool.getConnection();
                    Statement st = cn.createStatement()) {
                while ((line = br.readLine()) != null) {
                    st.execute(line);
                }
            }
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    public static PsqlStore instOf() {
        return INST;
    }

    @Override
    public Collection<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM post")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(it.getInt("id"), it.getString("name")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM candidate")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(new Candidate(it.getInt("id"), it.getString("name"), it.getString("photoId")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidates;
    }

    @Override
    public void save(Post post) {
        if (post.getId() == 0) {
            create(post);
        } else {
            update(post);
        }
    }

    private Post create(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("INSERT INTO post(name) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, post.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    private void update(Post post) {
        String update = "update post set name = ? where id = ?";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(update)) {
            ps.setString(1, post.getName());
            ps.setInt(2, post.getId());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Post findById(int id) {
        Post post = null;
        String find = "select id, name from post where id = ?";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(find)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            String name = null;
            while (rs.next()) {
                name = rs.getString("name");
            }
            post = new Post(id, name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public void saveCandidate(Candidate candidate) {
        if (candidate.getId() == 0) {
            createCan(candidate);
        } else {
            updateCan(candidate);
        }
    }

    private Candidate createCan(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("INSERT INTO candidate(name, photoId) VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getPhotoId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return candidate;
    }

    private void updateCan(Candidate candidate) {
        String update = "update candidate set name = ?,  photoId = ? where id = ?";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(update)) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getPhotoId());
            ps.setInt(3, candidate.getId());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String delete = "delete from candidate where id = ?";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(delete)) {
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Candidate findByIdCan(int id) {
        Candidate candidate = null;
        String find = "select id, name, photoId from candidate where id = ?";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(find)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            String name = null;
            String photoId = null;
            while (rs.next()) {
                name = rs.getString("name");
                photoId = rs.getString("photoId");
            }
            candidate = new Candidate(id, name, photoId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return candidate;
    }

    @Override
    public User findByEmail(String email) {
        User user = null;
        String find = "select id, name, email, password from client where email = ?";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(find)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            int id = 0;
            String name = null;
            String password = null;
            while (rs.next()) {
                id = rs.getInt(1);
                name = rs.getString("name");
                password = rs.getString("password");
            }
            user = new User(id, name, email, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public Collection<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM client")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    users.add(new User(it.getInt("id"), it.getString("name"), it.getString("email"), it.getString("password")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void saveUser(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("INSERT INTO client(name, email, password) VALUES (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
