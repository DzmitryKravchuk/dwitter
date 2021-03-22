package devinc.dwitter.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User extends AbstractEntity {
    @Column(name = "user_name")
    private String name;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Role.class)
    private Role role;

    @Column(name = "is_active")
    private  boolean isActive;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "author_subscriber", joinColumns = @JoinColumn(name = "author_id"), inverseJoinColumns = @JoinColumn(name = "subscriber_id"))
    private Set<User> subscribedUsersList;
}
