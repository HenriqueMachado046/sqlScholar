package br.com.sqlScholar.service;


import br.com.sqlScholar.model.Student;
import br.com.sqlScholar.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

}
