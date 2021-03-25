package devinc.dwitter.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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

    @OneToMany (mappedBy="user", fetch=FetchType.EAGER)
    private List<Tweet> tweetList;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Role.class)
    private Role role;

    @Column(name = "is_active")
    private boolean isActive;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "user_subscriber", joinColumns = @JoinColumn(name = "subscriber_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> usersSubscribedToList;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "user_subscriber", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "subscriber_id"))
    private Set<User> subscribersList;

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
