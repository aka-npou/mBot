package aka_npou.mBot.bot.menu;

import aka_npou.mBot.db.model.Event;
import aka_npou.mBot.db.model.User;
import aka_npou.mBot.db.service.BotService;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@Component
public class DefaultMenu implements Menu{
    @Getter
    private final BotState botState = BotState.DEFAULT_MENU;
    private final BotService botService;

    public DefaultMenu(BotService botService) {
        this.botService = botService;
    }

    @Override
    public SendMessage getBotApiMethod(Update update, SendMessage message) {
        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        KeyboardRow row4 = new KeyboardRow();
        row1.add(new KeyboardButton("вчера"));
        row1.add(new KeyboardButton("сегодня"));
        row2.add(new KeyboardButton("указать дату"));
        row3.add(new KeyboardButton("график"));
        row4.add(new KeyboardButton("редактировать"));

        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        keyboard.add(row4);
        replyKeyboardMarkup.setKeyboard(keyboard);

        message.setReplyMarkup(replyKeyboardMarkup);

        return message;
    }

    @Override
    public BotState Execute(Update update, SendMessage message) {
        BotState botState = BotState.DEFAULT_MENU;
        String text = update.getMessage().getText();

        switch (text) {
            case ("вчера"): {
                message.setText("записано событие");

                Event event = getEvent(update, 1);
                botService.addEvent(event);
                break;
            }
            case ("сегодня"): {
                message.setText("записано событие");

                Event event = getEvent(update, 0);
                botService.addEvent(event);
                break;
            }
            case ("указать дату"): {
                botState = BotState.DATE_MENU;
                break;
            }
            case ("график"): {
                botState = BotState.SCHEDULE_MENU;
                break;
            }
            case ("редактировать"): {
                /*String elements = botService.getEditEvents(update.getMessage().getFrom().getId(), 0);
                message.setText(elements);*/
                botState = BotState.EDIT_MENU;
                break;
            }
            default: {
                message.setText("не распознано");
            }
        }

        return botState;
    }

    private Event getEvent(Update update, int minusDays) {
        User user = botService.getUser(update.getMessage().getFrom());

        Event event = new Event();
        event.setUser(user);

        LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochSecond(update.getMessage().getDate()),
                TimeZone.getDefault().toZoneId());

        event.setDate(date.minusDays(minusDays));

        return event;
    }
}
