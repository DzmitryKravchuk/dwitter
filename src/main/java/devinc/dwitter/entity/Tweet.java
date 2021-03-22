package devinc.dwitter.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tweet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Tweet extends AbstractEntity {
    @Column(name = "content")
    private String content;

    @Column(name = "likes")
    private int likes;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Thread.class)
    private Thread thread;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Tweet.class)
    private Tweet tweet;
}
