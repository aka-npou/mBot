package aka_npou.mBot.db.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long chatId;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Event> events = new ArrayList<>();

}
