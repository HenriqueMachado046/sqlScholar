package br.com.sqlScholar.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
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

        if(student != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já registrado");
        }

        student.setUsername();
        student.setFirstName();
        student.setLastName();

        String passwordHashed = passwordEncoder.encode();

        return null;
    }


}
