package br.com.sqlScholar.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.sqlScholar.model.Student;
import br.com.sqlScholar.repository.StudentRepository;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    StudentRepository studentRepository;

    @GetMapping("/index")
    public ModelAndView home(HttpSession session) {

        Map<String, Object> template = new HashMap<>();
        Object userLogged = session.getAttribute("userLogged");
        String userType = (String) session.getAttribute("userType");

        if (userType == null) {
            return new ModelAndView("redirect:/");
        }

        if (userType.equals("admin")) {
            template.put("isAdmin", session.getAttribute("isAdmin"));
        } else {
            if (userType.equals("teacher")) {
                template.put("isTeacher", session.getAttribute("isTeacher"));
            } else {
                template.put("isStudent", session.getAttribute("isStudent"));
            }
        }

        List<Student> students = studentRepository.rankingStudents();
        List<String> studentsRanked = new ArrayList<String>();

        for (int i = 0; i < students.size(); i++) {
            if (studentRepository.getRightById(students.get(i).getId()) != 0) {
                if (i == 0) {
                    studentsRanked.add("Nome: " + students.get(i).getFirstName() + " " + students.get(i).getLastName()
                            + "<br> Total de acertos: " + studentRepository.getRightById(students.get(i).getId())
                            + " Ranking: Ouro. Parabéns!");
                } else {
                    if (i == 1) {
                        studentsRanked.add("Nome: " + students.get(i).getFirstName() + " "
                                + students.get(i).getLastName() + "<br> Total de acertos: "
                                + studentRepository.getRightById(students.get(i).getId()) + " Ranking: Prata. Ótimo!");
                    } else {
                        studentsRanked.add("Nome: " + students.get(i).getFirstName() + " "
                                + students.get(i).getLastName() + "<br> Total de acertos: "
                                + studentRepository.getRightById(students.get(i).getId()) + " Ranking: Bronze. Continue assim!");
                    }
                }
            }
        }

        template.put("userLogged", userLogged);
        template.put("userType", userType);
        template.put("students", studentsRanked);
        return new ModelAndView("home/index", template);
    }
}
