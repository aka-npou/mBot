package aka_npou.mBot.db.repository;

import aka_npou.mBot.db.model.Event;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
    List<Event> findAllByUserId(Long id);

    @Query("select e from Event e where e.date = ?1 and e.enable = true")
    List<Event> findAllByDate(LocalDateTime date);

    List<Event> findTop12ByUserIdAndEnableOrderByDateDesc(Long id, boolean b);

    @Query("select e from Event e where e.user.id = ?1 and e.enable = true order by e.date desc")
    List<Event> getPart(Long id);

    @Modifying
    @Transactional
    @Query("update Event e set e.enable = false where e.id = ?1")
    int disableEvent(Long id);

}
