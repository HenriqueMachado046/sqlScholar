package br.com.sqlScholar.service;


import br.com.sqlScholar.model.Student;
import br.com.sqlScholar.repository.StudentRepository;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> pageableStudent(int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Student> questionPage = this.studentRepository.findAll(pageable);
        return questionPage.getContent();
    }

    public Student validateCredentials(String email, String senha){
       String hashed = DigestUtils.md5DigestAsHex(senha.getBytes()).toUpperCase();
       return studentRepository.findByEmailAndPassword(email, hashed);
    }

    public boolean verifySession(HttpSession session) {
        if (!(session.getAttribute("userLogged") == null) && "student".equals(session.getAttribute("userType"))) {
            return true;
        }else{
            if (session.getAttribute("userType") == "admin") {
                return true;                
            }
            return false;
        }
    }

}
