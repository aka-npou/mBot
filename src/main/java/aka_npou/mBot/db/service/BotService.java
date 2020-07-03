package aka_npou.mBot.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aka_npou.mBot.db.repository.UserRepository;

@Service
public class BotService {
    private UserRepository userRepository;

    @Autowired
    public BotService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


}
