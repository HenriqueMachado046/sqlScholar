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
    private String email;
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

    public StudentModel(String username,
                        String email,
                        String firstName,
                        String lastName,
                        String password, AppUserRole appUserRole, Boolean locked, Boolean enabled, LocalDateTime createdAt) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.appUserRole = appUserRole;
        this.locked = locked;
        this.enabled = enabled;
        this.createdAt = createdAt;
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