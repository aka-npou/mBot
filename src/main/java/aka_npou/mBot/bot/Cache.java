package aka_npou.mBot.bot;

import aka_npou.mBot.bot.menu.BotState;
import aka_npou.mBot.db.model.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Cache {
    Map<Integer, BotState> map = new HashMap<>();
    Map<Integer, User> userMap = new HashMap<>();

    public BotState getState(Integer id) {
        return map.getOrDefault(id, BotState.DEFAULT_MENU);
    }

    public void setState(Integer id, BotState state) {
        map.put(id, state);
    }

    public User getUser(Integer id) {
        return userMap.get(id);
    }

    public void setUser(Integer id, User user) {
        userMap.put(id, user);
    }
}
