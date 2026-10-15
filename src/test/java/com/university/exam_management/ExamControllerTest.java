package com.university.exam_management;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExamControllerTest {
    @Test
    void returnsAllExams() {
        assertEquals(2, new ExamController().all().size());
    }

    @Test
    void unknownExamGivesError() {
        assertTrue(new ExamController().one(99).containsKey("error"));
    }
}