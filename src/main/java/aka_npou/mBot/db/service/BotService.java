package aka_npou.mBot.db.service;

import aka_npou.mBot.db.model.Event;
import aka_npou.mBot.db.model.User;
import aka_npou.mBot.db.repository.EventRepository;
import aka_npou.mBot.db.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        if (eventRepository.findAllByDateAndActiveTrue(event.getDate()).size() == 0)
            eventRepository.save(event);
    }

    public List<Event> getLast12Events(Integer id) {
        User user = getUser(id);

        return eventRepository.findTop12ByUserIdAndActiveTrueOrderByDateDesc(user.getId());

    }

    public String getEvents(Integer id) {

        List<Event> ldtList = getLast12Events(id);

        StringBuilder sb = new StringBuilder();

        int allDays = 0;
        int count = 0;

        LocalDateTime lastDate = null;
        for (Event event:ldtList) {
            if (lastDate != null) {
                int days = (int) ((lastDate.atZone(ZoneId.systemDefault()).toEpochSecond()
                                  - event.getDate().atZone(ZoneId.systemDefault()).toEpochSecond())
                                  / 60 / 60 / 24);
                sb.append("\n").append(String.format("%dд", days));

                allDays += days;
                count++;

            }

            sb.append("\n")
                    .append(String.format("%02d.%02d.%d",
                            event.getDate().getDayOfMonth(),
                            event.getDate().getMonthValue(),
                            event.getDate().getYear()));

            lastDate = event.getDate();
        }

        if (sb.length() == 0) {
            sb.append("пусто");
        } else {
            if (count != 0) {
                sb.append("\n").append("средний цикл: ").append(allDays / count).append("д");
            }
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

    public List<Event> getShiftEvents(Integer id, int shift) {
        User user = getUser(id);

        List<Event> ldtList = eventRepository.getPart(user.getId());

        List<Event> ldtListPart = new ArrayList<>();

        int elementsInPart = 6;
        int currentElementInPart = 0;
        int currentShift = 0;

        for (Event event : ldtList) {
            if (currentShift == shift) {
                ldtListPart.add(event);
            }
            currentElementInPart++;
            if (currentElementInPart >= elementsInPart) {
                currentElementInPart = 0;
                currentShift++;
            }
        }

        return ldtListPart;
    }

    public String getEditEvents(Integer id, int shift, Map<Integer, Long> map) {
        List<Event> ldtList = getShiftEvents(id, shift);

        StringBuilder sb = new StringBuilder();

        map.clear();

        int n = 1;
        for (Event event:ldtList) {
            sb.append("\n")
                    .append(String.format("/%d %02d.%02d.%d",
                            n,
                            event.getDate().getDayOfMonth(),
                            event.getDate().getMonthValue(),
                            event.getDate().getYear()));

            map.put(n, event.getId());

            n++;

        }

        if (sb.length() == 0) {
            sb.append("пусто");
        }

        return sb.toString();
    }

    public void deleteEvent(long eventId) {
        System.err.println("eventId " + eventId);
        eventRepository.disableEvent(eventId);
    }
}
