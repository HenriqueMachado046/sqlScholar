package br.com.sqlScholar.service;

import br.com.sqlScholar.dto.QuestionDTO;
import br.com.sqlScholar.model.Question;
import br.com.sqlScholar.repository.QuestionRepository;
import br.com.sqlScholar.utils.Resultado;
import br.com.sqlScholar.utils.sqlUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public List<Question> pageableQuestion(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Question> questionPage = this.questionRepository.findAll(pageable);
        return questionPage.getContent();
    }

    // funcionando
    public List<QuestionDTO> listAvailableQuestions() {
        List<Question> vetQuestion = this.questionRepository.findAll();
        List<QuestionDTO> vetQuestionDTOs = new ArrayList<QuestionDTO>();
        for (Question question : vetQuestion) {
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setId(question.getId());
            questionDTO.setTitle(question.getTitle());
            questionDTO.setEnabled(false);
            vetQuestionDTOs.add(questionDTO);
        }
        return vetQuestionDTOs;
    }

    public List<String> awnserQuestion(String sql, String sql_questao, String databaseName) {
        // Limitar o aluno a fazer apenas SELECT, com algum tipo de trava.
        // Deve funcionar...
        String sqlAlunoValidado = sqlUtils.validateSQL(sql);
        String sqlQuestaoValidado = sqlUtils.validateSQL(sql_questao);
        Resultado resultadoFinal = new Resultado();
        List<String> resultadoFinalString = new ArrayList<String>();
        List<String> resultadoComparado;
        // Isso funciona!
        // Aprendizado: O resultset até pode ser copiado, mas, aparentemente o MetaData
        // não. Quando escrevi este código, ocorreu um bug em que se o objeto fosse
        // clonado, ele não clonava de maneira correta e retornava null, mesmo que a
        // query estivesse correta.
        try {

            resultadoComparado = compareSQL(sqlAlunoValidado, sqlQuestaoValidado, databaseName);
            resultadoFinal = sqlUtils.executeSQL(resultadoComparado.get(0), databaseName);
            ResultSetMetaData resultadoFinalMetaData = resultadoFinal.getResultSet().getMetaData();
            resultadoFinalString = listResult(resultadoFinal, resultadoFinalMetaData);
            resultadoFinalString.add(resultadoComparado.get(1));
            return resultadoFinalString;

        } catch (SQLException e) {
            return Arrays.asList(resultadoFinal.getException().getMessage());

        }
    }

    private List<String> compareSQL(String sql, String sql_questao, String databaseName) throws SQLException {

        Resultado resultadoQueryAluno = sqlUtils.executeSQL(sql, databaseName);
        Resultado resultadoQueryQuestao = sqlUtils.executeSQL(sql_questao, databaseName);
        ResultSetMetaData metaDataAluno;
        ResultSetMetaData metaDataQuestao;
        List <String> sqlCorreto = new ArrayList<String>();

        if (resultadoQueryAluno.getException() != null) {
            sqlCorreto.add(sql);
            return sqlCorreto;
        }

        if (resultadoQueryQuestao.getException() != null) {
            sqlCorreto.add(sql_questao);
            return sqlCorreto;
        }

        metaDataAluno = resultadoQueryAluno.getResultSet().getMetaData();
        metaDataQuestao = resultadoQueryQuestao.getResultSet().getMetaData();
        if (metaDataAluno.getColumnCount() != metaDataQuestao.getColumnCount()) {
            return null;
        } else {
            List<String> resultadoAlunoList = listResult(resultadoQueryAluno, metaDataAluno);
            List<String> resultadoQuestaoList = listResult(resultadoQueryQuestao, metaDataQuestao);

            if (resultadoAlunoList.containsAll(resultadoQuestaoList)) {
                sqlCorreto.add(sql);
                sqlCorreto.add("Certo.");
                return sqlCorreto;
            }else{
                sqlCorreto.add(sql);
                sqlCorreto.add("Errado.");
                return sqlCorreto;
            }
        }


    }

    private List<String> listResult(Resultado resultado, ResultSetMetaData metaData) throws SQLException {
        int count = 1;

        String resultadoString = "";
        List<String> resultadoList = new ArrayList<String>();
        while (resultado.getResultSet().next()) {
            count = 1;
            resultadoString = "";
            while (count <= metaData.getColumnCount()) {
                resultadoString += "\n" + metaData.getColumnName(count) + ": "
                        + resultado.getResultSet().getString(count).toString();
                count++;
            }
            resultadoList.add(resultadoString);
        }
        return resultadoList;
    }

}