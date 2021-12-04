package uz.teamsupercool.finishit.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import uz.teamsupercool.finishit.model.User;
import uz.teamsupercool.finishit.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public record UserService(UserRepository repository) {

    public void addUser(User user) throws Exception {
        repository.insert(new User(null, user.getUsername(), user.getPassword(), new ArrayList<>()));
    }

    public void addDoc(String userId, String docId) {
        User savedUser = getUserById(userId);

        final List<String> docs = savedUser.getDocuments();

        docs.add(docId);

        savedUser.setDocuments(docs);
        repository.save(savedUser);
    }

    public User getUserById(String id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Cannot Find User by ID %s", id)));
    }

    public User getUserByUsername(String username) {
        return repository.findByUsername(username).orElseThrow(() -> new RuntimeException(String.format("Cannot Find User by Username %s", username)));
    }

    public boolean deleteUsersDoc(String userId, String docId) {
        final User savedUser = getUserById(userId);

        final List<String> docs = savedUser.getDocuments();

        try {
            docs.remove(docId);
            savedUser.setDocuments(docs);
            repository.save(savedUser);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }


    public void updateUser(String username, String newUsername, String password) {
        final User savedUser = getUserByUsername(username);

        savedUser.setUsername(newUsername);

        final String encryptedPassword = new BCryptPasswordEncoder().encode(password);
        savedUser.setPassword(encryptedPassword);

        repository.save(savedUser);
    }

    public void deleteUser(String id) {
        repository.deleteById(id);
    }
}
