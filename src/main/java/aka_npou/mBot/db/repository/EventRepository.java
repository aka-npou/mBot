package aka_npou.mBot.db.repository;

import aka_npou.mBot.db.model.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
    List<Event> findAllByUserId(Long id);

    @Query("select distinct date from Event where user_id = ?1 order by date desc")
    List<LocalDateTime> findDistinctTop12ByUserId(Long id);

    List<Event> findAllByDate(LocalDateTime date);

}
