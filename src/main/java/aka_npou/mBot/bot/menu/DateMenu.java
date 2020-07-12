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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DateMenu implements Menu{
    @Getter
    private final BotState botState = BotState.DATE_MENU;
    private final BotService botService;

    public DateMenu(BotService botService) {
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

        LocalDate localDate = LocalDate.now();

        for (int i = -4; i <= 0; i++) {
            row3.add(new KeyboardButton(String.format("%02d.%02d", localDate.plusDays(i).getDayOfMonth(), localDate.plusDays(i).getMonthValue())));
        }
        for (int i = -9; i <= -5; i++) {
            row2.add(new KeyboardButton(String.format("%02d.%02d", localDate.plusDays(i).getDayOfMonth(), localDate.plusDays(i).getMonthValue())));
        }
        for (int i = -14; i <= -10; i++) {
            row1.add(new KeyboardButton(String.format("%02d.%02d", localDate.plusDays(i).getDayOfMonth(), localDate.plusDays(i).getMonthValue())));
        }
        row4.add(new KeyboardButton("назад"));


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
            case ("назад"): {
                message.setText("выберите действие");
                break;
            }
            default: {
                if (text.matches("[0-9][0-9].[0-9][0-9]")) {
                    try {
                        String[] sDate = text.split("\\.");
                        LocalDateTime date = LocalDateTime.now()
                                                            .withDayOfMonth(Integer.parseInt(sDate[0]))
                                                            .withMonth(Integer.parseInt(sDate[1]));

                        User user = botService.getUser(update.getMessage().getFrom().getId());

                        Event event = new Event();
                        event.setUser(user);

                        event.setDate(date);
                        botService.addEvent(event);

                    } catch (Exception e) {
                        message.setText("не распознано");
                    }
                    message.setText("записано событие");
                } else {
                    message.setText("не распознано");
                }
                break;
            }
        }

        return botState;
    }
}
