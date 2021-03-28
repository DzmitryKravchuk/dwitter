package devinc.dwitter.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "thread")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Topic extends AbstractEntity {
    @Column(name = "topic")
    private String topic;

    @OneToMany(mappedBy="topic", fetch= FetchType.EAGER)
    private List<Tweet> tweetList;

    @Override
    public String toString() {
        return "Thread{" +
                "topic='" + topic + '\'' +
                '}';
    }
}
