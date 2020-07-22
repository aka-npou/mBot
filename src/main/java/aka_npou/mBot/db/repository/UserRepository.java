package aka_npou.mBot.db.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import aka_npou.mBot.db.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByChatId(Integer id);

    @Modifying
    @Transactional
    @Query("update User u set u.changeDate = ?2, u.firstName = ?3, u.lastName = ?4, u.username = ?5 where u.id = ?1")
    int update(Long id,
               LocalDateTime changeDate,
               String firstName,
               String lastName,
               String username);
}
