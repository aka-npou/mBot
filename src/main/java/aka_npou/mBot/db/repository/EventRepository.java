package aka_npou.mBot.db.repository;

import aka_npou.mBot.db.model.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {

}
