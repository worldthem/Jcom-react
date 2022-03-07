package jcom.cms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
@Entity // This tells Hibernate to make a table out of this class
@Table(name =  "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Users implements Serializable {
    @Id
    //@Column(name = "USER_ID", updatable=false, nullable=false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer userid;

    @Column(length = 150)
    private String privateid ;

    @Column(length = 350)
    private String fcmtoken ;

    @Column(length = 150)
    private String unicid ;

    @DateTimeFormat(pattern="MM/dd/yyyy")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dob ;

    @Column(length = 150, name = "first_name")
    private String firstName;

    @Column(length = 150, name = "last_name")
    private String lastName;

    @Column(length = 150)
    private String email;

    @Column(length = 255)
    private String password;

    @Column(length = 100)
    private String remember_token;

    @UpdateTimestamp
    private LocalDateTime updated_at;

    @CreationTimestamp
    @Column(updatable = false)
    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    private LocalDateTime created_at;

    @Column(length = 10)
    private String status;

    @Column(length = 100)
    private String resetToken;

    private LocalDateTime resetTime;

    @Column(name = "LOCKED")
    private boolean locked;

    @Column(length = 10)
    private String premium;


   @ManyToMany(fetch = FetchType.EAGER)
   @JoinTable( name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userid"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
   private List<Role> roles;

}
