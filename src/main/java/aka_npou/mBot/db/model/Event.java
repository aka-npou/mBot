package aka_npou.mBot.db.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Event {

    @Id
    private long id;

    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime date;

}
