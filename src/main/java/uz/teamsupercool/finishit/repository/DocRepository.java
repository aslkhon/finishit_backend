package uz.teamsupercool.finishit.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import uz.teamsupercool.finishit.model.Doc;

public interface DocRepository extends MongoRepository<Doc, String> {
}
