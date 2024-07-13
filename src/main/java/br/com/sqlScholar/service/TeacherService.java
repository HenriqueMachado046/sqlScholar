package br.com.sqlScholar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.sqlScholar.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.sqlScholar.dto.TeacherDTO;
import br.com.sqlScholar.model.Teacher;
import br.com.sqlScholar.repository.TeacherRepository;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    public List<Teacher> pageableTeacher(int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Teacher> questionPage = this.teacherRepository.findAll(pageable);
        return questionPage.getContent();
    }

    //funcionando
    public List<TeacherDTO> listAvailableTeachers(UUID id, String firstName, String lastName) {

        List<TeacherDTO> arrTeacherDTOs = new ArrayList<TeacherDTO>();
        TeacherDTO owner = new TeacherDTO();

        owner.setId(id);

        owner.setFirstName(firstName);

        owner.setLastName(lastName);

        owner.setOwner(true);

        List<Teacher> vetTeacher = this.teacherRepository.findAll();
        for (int i = 0; i < vetTeacher.size(); i++) {
            TeacherDTO teacherDTO = new TeacherDTO();
            teacherDTO.setId(vetTeacher.get(i).getId());
            teacherDTO.setFirstName(vetTeacher.get(i).getFirstName());
            teacherDTO.setLastName(vetTeacher.get(i).getLastName());
            if (vetTeacher.get(i).getId() != owner.getId()) {
                teacherDTO.setOwner(false);
            } else {
                teacherDTO.setOwner(true);
            }
            arrTeacherDTOs.add(teacherDTO);
        }

        return arrTeacherDTOs;
    }

}
