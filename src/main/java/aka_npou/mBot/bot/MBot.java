package aka_npou.mBot.bot;

import aka_npou.mBot.bot.menu.BotState;
import aka_npou.mBot.bot.menu.EditMenu;
import aka_npou.mBot.bot.menu.Menu;
import aka_npou.mBot.bot.menu.MenuFactory;
import aka_npou.mBot.db.model.User;
import aka_npou.mBot.db.service.BotService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
@Setter
public class MBot extends TelegramWebhookBot {
    private String webHookPath;
    private String botUserName;
    private String botToken;
    @Autowired
    private Cache cache;
    @Autowired
    private MenuFactory menuFactory;
    @Autowired
    private BotService botService;

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        if (!update.getMessage().hasText()) {
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId());
            message.setText("понимаю только текст");
            return message;
        }

        Menu menu = getMenu(getState(update));
        return getMessage(menu, update);

    }

    private BotApiMethod<?> getMessage(Menu menu, Update update) {
        User user = botService.getUser(update.getMessage().getFrom());

        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId());

        BotState newBotState = menu.Execute(update, message, user);

        cache.setState(update.getMessage().getFrom().getId(), newBotState);

        getMenu(getState(update)).getBotApiMethod(update, message);
        
        if (getState(update) != BotState.EDIT_MENU) {
            EditMenu editMenu = (EditMenu)getMenu(BotState.EDIT_MENU);
            editMenu.setShift(0);
        }
        return message;
    }

    private BotState getState(Update update) {
        return cache.getState(update.getMessage().getFrom().getId());
    }

    private Menu getMenu(BotState state) {
        return menuFactory.getMenu(state);
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotPath() {
        return webHookPath;
    }

}
