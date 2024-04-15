package br.com.sqlScholar.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private IStudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity create(@RequestBody StudentModel studentModel){
        StudentModel student = this.studentRepository.findByUsername(studentModel.getUsername());
        student.setUsername(studentModel.getUsername());
        student.setFirstName(studentModel.getFirstName());
        student.setLastName(student.getLastName());
        student.setPassword(passwordEncoder.encode(studentModel.getPassword()));


        return null;
    }


}
