package aka_npou.mBot.bot.menu;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Menu {
    BotState getBotState();
    SendMessage getBotApiMethod(Update update, SendMessage message);
    BotState Execute(Update update, SendMessage message);
}
