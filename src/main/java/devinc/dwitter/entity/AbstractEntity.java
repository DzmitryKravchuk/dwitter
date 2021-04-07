package devinc.dwitter.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class AbstractEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    //@Column(name = "id", columnDefinition = "VARCHAR(36)")
    @Column(name = "id")
    @Type(type="pg-uuid")
    private UUID id;

    @Column(updatable = false)
    private Date created;

    @Column
    private Date updated;

    public AbstractEntity(UUID id) {
    }
}
