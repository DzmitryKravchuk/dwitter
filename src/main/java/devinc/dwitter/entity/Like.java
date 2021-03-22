package devinc.dwitter.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "like")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Like extends AbstractEntity{

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Tweet.class)
    private Tweet tweet;
}
