package aka_npou.mBot.bot.controller;

import aka_npou.mBot.bot.MBot;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class BotController {
    private final MBot mBot;

    public BotController(MBot mBot) {
        this.mBot = mBot;
    }

    @PostMapping(path = "/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return mBot.onWebhookUpdateReceived(update);
    }

    @GetMapping(path = "/")
    public void get() {

    }
}
