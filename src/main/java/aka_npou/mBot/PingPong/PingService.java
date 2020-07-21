package aka_npou.mBot.PingPong;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@Getter
@Setter
public class PingService {
    @Value("https://aka-npou-ping-pong.herokuapp.com/ping")
    private String url;

    //@Scheduled(fixedRateString = "1200000")
    public void pingMe() {
        try {
            URL url = new URL(getUrl());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            System.err.printf("ping %s\n", connection.getResponseCode());
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
