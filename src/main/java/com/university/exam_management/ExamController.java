package com.university.exam_management;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.concurrent.*;

@RestController
@RequestMapping("/api/exams")
public class ExamController {
    private final Map<Integer, Map<String, String>> exams = new ConcurrentHashMap<>();

    public ExamController() {
        exams.put(1, Map.of("course", "Refrigeration and Air Conditioning", "date", "2026-11-20", "hall", "A-102"));
        exams.put(2, Map.of("course", "Thermodynamics", "date", "2026-11-22", "hall", "B-105"));
    }

    @GetMapping
    public Collection<Map<String, String>> all() { return exams.values(); }

    @GetMapping("/{id}")
    public Map<String, String> one(@PathVariable int id) {
        return exams.getOrDefault(id, Map.of("error", "Exam not found"));
    }
}
