package devinc.dwitter.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tweet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tweet extends AbstractEntity {
    @Column(name = "content")
    private String content;

    @Column(name = "likes")
    private int likesCount;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Topic.class)
    private Topic topic;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Tweet.class)
    private Tweet tweet;

    @OneToMany(mappedBy = "tweet", fetch = FetchType.EAGER)
    private List<Like> likesList;

    @Override
    public String toString() {
        return "Tweet{" +
                "content='" + content + '\'' +
                ", likesCount=" + likesCount +
                ", user=" + user.getName() +
                '}';
    }
}
