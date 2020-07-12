package aka_npou.mBot.db.service;

import aka_npou.mBot.db.model.Event;
import aka_npou.mBot.db.model.User;
import aka_npou.mBot.db.repository.EventRepository;
import aka_npou.mBot.db.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class BotService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public BotService(UserRepository userRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    public void addEvent(Event event) {
        event.setDate(event.getDate().withHour(0).withMinute(0).withSecond(0).withNano(0));
        if (eventRepository.findAllByDate(event.getDate()).size() == 0)
            eventRepository.save(event);
    }

    public List<LocalDateTime> getEvents(Integer id) {
        User user = getUser(id);

        List<LocalDateTime> ldtList = eventRepository.findDistinctTop12ByUserId(user.getId());

        return ldtList;
    }

    public String getLast12Events(Integer id) {

        List<LocalDateTime> ldtList = getEvents(id);

        StringBuilder sb = new StringBuilder();

        LocalDateTime lastDate = null;
        for (LocalDateTime dateTime:ldtList) {
            if (lastDate != null) {
                int days = (int) ((lastDate.atZone(ZoneId.systemDefault()).toEpochSecond()
                                  - dateTime.atZone(ZoneId.systemDefault()).toEpochSecond())
                                  / 60 / 60 / 24);
                sb.append("\n").append(String.format("%dд", days));
            }

            sb.append("\n")
                    .append(String.format("%02d.%02d.%d",
                            dateTime.getDayOfMonth(),
                            dateTime.getMonthValue(),
                            dateTime.getYear()));

            lastDate = dateTime;
        }

        if (sb.length() == 0) {
            sb.append("пусто");
        }

        return sb.toString();
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public User getUser(Integer id) {
        User user = userRepository.findByChatId(id);

        if (user == null) {
            user = new User();
            user.setChatId(id);
            addUser(user);
        }

        return user;
    }
}
