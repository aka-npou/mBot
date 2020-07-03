package aka_npou.mBot.bot.controller.appconfig;

import aka_npou.mBot.bot.MBot;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "telegrambot")
public class BotConfig {
    private String webHookPath;
    private String botUserName;
    private String botToken;

    @Bean
    public MBot mBot() {
        MBot mBot = new MBot();
        mBot.setWebHookPath(webHookPath);
        mBot.setBotToken(botToken);
        mBot.setBotUserName(botUserName);

        return mBot;
    }

}
