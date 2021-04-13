package devinc.dwitter.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
