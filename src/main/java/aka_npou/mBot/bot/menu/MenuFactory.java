package aka_npou.mBot.bot.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MenuFactory {
    Map<BotState, Menu> map = new HashMap<>();

    @Autowired
    public MenuFactory(List<Menu> menuList) {
        menuList.forEach(menu -> map.put(menu.getBotState(), menu));
    }

    public Menu getMenu(BotState state) {
        return map.getOrDefault(state, map.get(BotState.DEFAULT_MENU));
    }
}
