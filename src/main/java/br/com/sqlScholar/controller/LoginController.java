package br.com.sqlScholar.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.sqlScholar.model.Student;
import br.com.sqlScholar.model.Teacher;
import br.com.sqlScholar.service.StudentService;
import br.com.sqlScholar.service.TeacherService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping
public class LoginController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    // IMPORTANTE!!!! Este user existe somente para a finalidade de criação de
    // outros como super usuário. Ele não será utilizado em nenhum outro momento e
    // será excluido eventualmente, mas é muito necessário para os testes que serão
    // feitos na aplicação
    private static final String ADMIN_EMAIL = "admin@teste.com";
    private static final String ADMIN_PASSWORD = "admin123";

    @GetMapping("/")
    public ModelAndView tela_login(){
        Map<String, Object> template = new HashMap<>();
        return new ModelAndView("login/index", template);
    }

    @PostMapping("/logar")
    public ModelAndView login(@RequestParam String email, @RequestParam String password, HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (ADMIN_EMAIL.equals(email) && ADMIN_PASSWORD.equals(password)) {
            session.setAttribute("userLogged", "Administrador");
            session.setAttribute("userType", "admin");
            return new ModelAndView("redirect:/admin/index");
        }

        Teacher teacher = teacherService.validateCredentials(email, password);

        if (teacher != null) {
            session.setAttribute("userLogged", teacher);
            session.setAttribute("userType", "teacher");
            return new ModelAndView("redirect:/teacher/index");
        }

        Student student = studentService.validateCredentials(email, password);

        if (student != null) {
            session.setAttribute("userLogged", student);
            session.setAttribute("userType", "student");
            return new ModelAndView("redirect:/questionlist/index");
        }

        return new ModelAndView("login/index").addObject("error", "Email ou senha incorretos!");

    }

}