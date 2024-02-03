package AdamFaouzi.demo.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Users")
public class AppUser implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "First Name")
    private String firstName;

    @Column(name = "Last Name")
    private String lastName;

    @Column(name = "Email", unique = true)
    private String email;

    @Column(name = "Password")
    private String password;

    @CreationTimestamp
    @Column(name = ("Creation Date"))
    private Timestamp creationDate;

    @Column(name = ("Last login"))
    private Timestamp lastLogin;

    @CreationTimestamp
    @Column(name = "Last update")
    private Timestamp lastUpdate;

    @Column(name = "Valid account")
    private boolean validAccount;

    @Column(name = "active")
    private boolean active;

    @ManyToOne(cascade = CascadeType.ALL)
    private AppRole role;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<AppPost> appPost;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role != null && this.role.getLabel() != null) {
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.role.getLabel()));
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public String getPassword(){
        return this.password;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.active;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }
}
