package com.srvTKcdg.services.implementation;

import com.srvTKcdg.models.Question;
import com.srvTKcdg.repositories.QuestionRepository;
import com.srvTKcdg.services.interfaces.IQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;


// @RequiredArgsConstructor //asagidaki constructur yazili buna gerek yok. ikisinden biri tercih edilir.
@Service
public class QuestionService implements IQuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        // @RequiredArgsConstructor anatosyon yazili ise bu constructur yazmaya gerek yok .
        this.questionRepository = questionRepository;
    }

    @Override
    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public List<Question> getAllQuestion() {
        return questionRepository.findAll();
    }

    @Override
    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public List<String> getAllSubjects() {
        return questionRepository.findDisctinctSubject();
    }

    @Override
    public Question updateQuestion(Long id, Question question) throws ChangeSetPersister.NotFoundException {
        Optional<Question> theQuestion = this.getQuestionById(id);
        if (theQuestion.isPresent()) {
            Question updatedQuestion = theQuestion.get();
            updatedQuestion.setQuestion(question.getQuestion());
            updatedQuestion.setChoices(question.getChoices());
            updatedQuestion.setCorrectAnswers(question.getCorrectAnswers());
            return questionRepository.save(updatedQuestion);
        }else{
            throw new ChangeSetPersister.NotFoundException();
        }
    }

     @Override
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    @Override
    public List<Question> getQuestionForUser(Integer numberOfQuestions, String subject) {
        Pageable pageable = PageRequest.of(0,numberOfQuestions);
        return questionRepository.findBySubject(subject,pageable).getContent();

    }
}
