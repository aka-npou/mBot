package aka_npou.mBot.db.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import aka_npou.mBot.db.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
