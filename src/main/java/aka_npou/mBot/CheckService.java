package aka_npou.mBot;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@Getter
@Setter
public class CheckService {
    @Value("${pingtask.url}")
    private String url;

    @Scheduled(fixedRateString = "${pingtask.period}")
    public void pingMe() {
        try {
            URL url = new URL(getUrl());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
