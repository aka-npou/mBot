package aka_npou.mBot.bot;

import aka_npou.mBot.bot.menu.BotState;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Cache {
    Map<Integer, BotState> map = new HashMap<>();

    public BotState getState(Integer id) {
        return map.getOrDefault(id, BotState.DEFAULT_MENU);
    }

    public void setState(Integer id, BotState state) {
        map.put(id, state);
    }
}
