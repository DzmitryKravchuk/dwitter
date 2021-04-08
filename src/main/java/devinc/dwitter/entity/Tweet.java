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

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    private User userAccount;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Topic.class)
    private Topic topic;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Tweet.class)
    private Tweet repostedTweet;

    @OneToMany(mappedBy = "tweet", fetch = FetchType.LAZY)
    private List<Like> likesList;

    @Override
    public String toString() {
        return "Tweet{" +
                "content='" + content + '\'' +
                ", likesCount=" + likesCount +
                ", user=" + userAccount.getName() +
                '}';
    }
}
