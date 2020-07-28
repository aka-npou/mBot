package aka_npou.mBot.bot.menu;

import aka_npou.mBot.db.model.User;
import aka_npou.mBot.db.service.BotService;
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
public class EditEventMenu implements Menu{
    @Getter
    private final BotState botState = BotState.EDIT_EVENT_MENU;
    private final BotService botService;
    private long eventId;

    public EditEventMenu(BotService botService) {
        this.botService = botService;
    }

    @Override
    public SendMessage getBotApiMethod(Update update, SendMessage message) {
        eventId = Long.parseLong(message.getText());
        System.err.println("eventId " + eventId);

        message.setText("выберите действие");

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        row1.add(new KeyboardButton("удалить"));
        //row1.add(new KeyboardButton("изменить"));
        row2.add(new KeyboardButton("назад"));


        keyboard.add(row1);
        keyboard.add(row2);
        replyKeyboardMarkup.setKeyboard(keyboard);

        message.setReplyMarkup(replyKeyboardMarkup);

        return message;
    }

    @Override
    public BotState Execute(Update update, SendMessage message, User user) {
        BotState botState = BotState.DEFAULT_MENU;
        String text = update.getMessage().getText();

        switch (text) {
            case ("назад"): {
                message.setText("выберите действие");
                break;
            }
            case ("удалить"): {
                botService.deleteEvent(eventId);
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
