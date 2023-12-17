package com.srvTKcdg.controller;

import com.srvTKcdg.models.Question;
import com.srvTKcdg.services.interfaces.IQuestionService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/quizes")
public class QuestionController {
    private final IQuestionService questionService;

    public QuestionController(IQuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/create-new-question")
    public ResponseEntity<Question> createQuestion(@Valid @RequestBody Question question) {
        Question createdQuestion=questionService.createQuestion(question);
        return ResponseEntity.status(CREATED).body(createdQuestion);
    }
    @GetMapping("/all-questions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        List<Question> questions=questionService.getAllQuestion();
        return ResponseEntity.ok(questions);
    }
    @GetMapping("/question/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        Optional<Question> theQuestion=questionService.getQuestionById(id);
        if(theQuestion.isPresent()){
            return ResponseEntity.ok(theQuestion.get());
        }else{
            throw new ChangeSetPersister.NotFoundException();
        }
    }
    @PostMapping("/question-update")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody Question question) throws ChangeSetPersister.NotFoundException {
        Question updateQuestion = questionService.updateQuestion(id, question);
        return ResponseEntity.ok(updateQuestion);
    }
}
