package br.com.sqlScholar.service;

import br.com.sqlScholar.model.Question;
import br.com.sqlScholar.model.TestCase;
import br.com.sqlScholar.repository.TestCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TestCaseService {

    @Autowired
    private TestCaseRepository testCaseRepository;

    public List<TestCase> pageableTestCase(int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<TestCase> testCasePage = this.testCaseRepository.findAll(pageable);
        return testCasePage.getContent();
    }




}
