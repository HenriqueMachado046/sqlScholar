package br.com.sqlScholar.student;

import br.com.sqlScholar.utils.AppUserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity(name = "tb_students")

public class StudentModel implements UserDetails{

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column
    private String username;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String password;
    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;
    @Column
    private Boolean locked;
    @Column
    private Boolean enabled;
    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    public StudentModel(LocalDateTime createdAt,
                        Boolean enabled,
                        Boolean locked,
                        AppUserRole appUserRole,
                        String password,
                        String lastName,
                        String firstName, String username) {
        this.createdAt = createdAt;
        this.enabled = enabled;
        this.locked = locked;
        this.appUserRole = appUserRole;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appUserRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}