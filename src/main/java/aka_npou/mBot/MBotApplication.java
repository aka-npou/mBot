package aka_npou.mBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(MBotApplication.class, args);
	}

}
