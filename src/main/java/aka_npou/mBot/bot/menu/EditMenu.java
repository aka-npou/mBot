package aka_npou.mBot.bot.menu;

import aka_npou.mBot.db.model.User;
import aka_npou.mBot.db.service.BotService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EditMenu implements Menu {
    @Getter
    private final BotState botState = BotState.EDIT_MENU;
    private Map<Integer, Long> map = new HashMap<>();
    private final BotService botService;
    @Getter
    @Setter
    private int shift;

    public EditMenu(BotService botService) {
        this.botService = botService;
    }

    @Override
    public SendMessage getBotApiMethod(Update update, SendMessage message) {
        String elements = botService.getEditEvents(update.getMessage().getFrom(), shift, map);
        message.setText(elements);

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        row1.add(new KeyboardButton("раньше"));
        row1.add(new KeyboardButton("позже"));
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
            case ("раньше"): {
                botState = BotState.EDIT_MENU;
                shift++;
                break;
            }
            case ("позже"): {
                botState = BotState.EDIT_MENU;
                shift--;
                break;
            }
            default: {
                if (text.matches("/[0-9]+")) {
                    try {
                        message.setText(String.valueOf(map.get(Integer.parseInt(text.replaceAll("/","")))));
                        botState = BotState.EDIT_EVENT_MENU;
                    } catch (Exception e) {
                        message.setText("не распознано");
                    }
                } else {
                    message.setText("не распознано");
                }
            }
        }

        return botState;
    }
}
