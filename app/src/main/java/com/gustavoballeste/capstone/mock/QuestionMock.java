package com.gustavoballeste.capstone.mock;

import com.gustavoballeste.capstone.model.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gustavoballeste on 17/04/17.
 *
 */

public class QuestionMock {

    public static List<Question> getQuestionMock() {

        List<Question> mQuestions = new ArrayList<>();

        mQuestions.add(new Question(1, "1", "Music", "multiple", "medium", "In the Harry Potter universe, what is Cornelius Fudge&#039;s middle name?", "Oswald", "James", "Harold", "Christopher"));
        mQuestions.add(new Question(2, "2", "General", "multiple", "medium", "In the Morse code, which letter is indicated by 3 dots? ", "S", "O", "A", "C"));
        mQuestions.add(new Question(3, "3", "Music", "multiple", "medium", "In the Harry Potter universe, what is Cornelius Fudge&#039;s middle name?", "Oswald", "James", "Harold", "Christopher"));
        mQuestions.add(new Question(4, "4", "General", "multiple", "medium", "In the Morse code, which letter is indicated by 3 dots? ", "S", "O", "A", "C"));
        mQuestions.add(new Question(5, "5", "Music", "multiple", "medium", "In the Harry Potter universe, what is Cornelius Fudge&#039;s middle name?", "Oswald", "James", "Harold", "Christopher"));
        mQuestions.add(new Question(6, "6", "General", "multiple", "medium", "In the Morse code, which letter is indicated by 3 dots? ", "S", "O", "A", "C"));
        mQuestions.add(new Question(7, "7", "Music", "multiple", "medium", "In the Harry Potter universe, what is Cornelius Fudge&#039;s middle name?", "Oswald", "James", "Harold", "Christopher"));
        mQuestions.add(new Question(8, "8", "General", "multiple", "medium", "In the Morse code, which letter is indicated by 3 dots? ", "S", "O", "A", "C"));
        mQuestions.add(new Question(9, "9", "Music", "multiple", "medium", "In the Harry Potter universe, what is Cornelius Fudge&#039;s middle name?", "Oswald", "James", "Harold", "Christopher"));
        mQuestions.add(new Question(10, "10", "General", "multiple", "medium", "In the Morse code, which letter is indicated by 3 dots? ", "S", "O", "A", "C"));

        return mQuestions;
    }

}
