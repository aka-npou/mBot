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

    List<Event> findAllByDateAndActiveTrue(LocalDateTime date);

    List<Event> findTop12ByUserIdAndActiveTrueOrderByDateDesc(Long id);

    @Query("select e from Event e where e.user.id = ?1 and e.active = true order by e.date desc")
    List<Event> getPart(Long id);

    @Modifying
    @Transactional
    @Query("update Event e set e.active = false where e.id = ?1")
    int disableEvent(Long id);

}
