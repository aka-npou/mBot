package aka_npou.mBot.PingPong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PongController {

    @GetMapping(path = "/ping")
    public String pingPong() {
        return "pong";
    }
}
