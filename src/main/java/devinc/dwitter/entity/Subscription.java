package devinc.dwitter.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "subscription")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Subscription extends AbstractEntity {
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    private User userAccount;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    private User subscriber;

    @Override
    public String toString() {
        return "Subscription{" +
                "user=" + userAccount.getName() +
                ", subscriber=" + subscriber.getName() +
                '}';
    }
}
