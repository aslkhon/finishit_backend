/*
 * FINISH.IT Task Manager
 * Final project of Application Programming in Java Course | Fall 2021
 *
 * Developed by TeamSuperCool:
 *
 * Aslkhon Khoshimkhujaev U2010145
 * Dilmurod Sagatov U2010235
 * Saidamalkhon Inoyatov U2010093
 * David Suleymanov U2010271
 * */

package uz.teamsupercool.finishit.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import uz.teamsupercool.finishit.model.Doc;

/*
* Doc Repository Interface extends Mongo DB Repository Interface with build in methods to mutate the DB.
* */

public interface DocRepository extends MongoRepository<Doc, String> {
}
