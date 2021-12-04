package uz.teamsupercool.finishit.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import uz.teamsupercool.finishit.model.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    @Query("{'username': ?0}")
    Optional<User> findByUsername(String username);
}
