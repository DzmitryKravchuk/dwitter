package devinc.dwitter.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractEntity {
    @Column(name = "user_name")
    private String name;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "userAccount", fetch = FetchType.LAZY)
    private List<Tweet> tweetList;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Role.class)
    private Role role;

    @Column(name = "is_active")
    private boolean isActive;

    @OneToMany(mappedBy = "userAccount", fetch = FetchType.LAZY)
    private List<Subscription> subscriptionsList;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role.getName() +
                ", isActive=" + isActive +
                '}';
    }
}
