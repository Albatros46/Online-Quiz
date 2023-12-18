package com.srvTKcdg.controller;

import com.srvTKcdg.models.Question;
import com.srvTKcdg.services.interfaces.IQuestionService;
import org.hibernate.annotations.Collate;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.Collections;
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
    @PutMapping("/{id}/question-update")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody Question question) throws ChangeSetPersister.NotFoundException {
        Question updateQuestion = questionService.updateQuestion(id, question);
        return ResponseEntity.ok(updateQuestion);
    }
    @DeleteMapping("/{id}/delete-question")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/subjects")
    public ResponseEntity<List<String>> getAllSubject(){
        List<String> subjects=questionService.getAllSubjects();
        return ResponseEntity.ok(subjects);
    }
    @GetMapping("/fetch-question-for-user")
    public ResponseEntity<List<Question>> getQuestionsForUser(@RequestParam Integer numberOfQuestions,
                                                              @RequestParam String subject){
        List<Question> allQuestions = questionService.getQuestionForUser(numberOfQuestions,subject);

        List<Question> newTableQuestions = new ArrayList<Question>(allQuestions);
        Collections.shuffle(newTableQuestions);

        int availableQuestions=Math.min(numberOfQuestions,newTableQuestions.size());
        List<Question> randomQuestions = newTableQuestions.subList(0,availableQuestions);
        return ResponseEntity.ok(randomQuestions);
    }
}
