package aka_npou.mBot.bot.menu;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduleMenu implements Menu {
    @Getter
    private final BotState botState = BotState.SCHEDULE_MENU;

    @Override
    public SendMessage getBotApiMethod(Update update, SendMessage message) {
        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("назад"));

        keyboard.add(row1);
        replyKeyboardMarkup.setKeyboard(keyboard);

        message.setReplyMarkup(replyKeyboardMarkup);

        return message;
    }

    @Override
    public BotState Execute(Update update, SendMessage message) {
        BotState botState = BotState.DEFAULT_MENU;
        String text = update.getMessage().getText();

        switch (text) {
            case ("назад"): {
                message.setText("выберите действие");
                break;
            }
            default: {
                message.setText("не распознано");
            }
        }

        return botState;
    }
}
