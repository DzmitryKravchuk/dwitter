package devinc.dwitter.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "topic")
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
