package br.com.sqlScholar.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


@Entity
@Getter
@Setter
//@DiscriminatorValue("T")
public class Teacher extends User {

    @ManyToMany
    @JoinTable(
            name = ("teacher_student"),
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    List<Student> students = new ArrayList<>();
    
    @OneToMany(mappedBy="owner")
    private List<QuestionList> lists = new ArrayList<>();

    @OneToMany(mappedBy="owner")
    private List<Question> ownedQuestions = new ArrayList<>();

    public Teacher() {
        this.setRole(UserRole.TEACHER);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_TEACHER"),
        new SimpleGrantedAuthority("ROLE_STUDENT"));
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
    public boolean isEnabled() {
        return true;
    }

    
}