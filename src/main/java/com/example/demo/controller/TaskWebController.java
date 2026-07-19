package com.example.demo.controller;

import com.example.demo.model.Task;
import com.example.demo.repository.TaskRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TaskWebController {

    private final TaskRepository taskRepository;

    public TaskWebController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("tasks", taskRepository.findAll());
        return "index";
    }

    @PostMapping("/tasks")
    public String addTask(@RequestParam(required = false) String title, @RequestParam String description) {
        Task newTask = new Task();
        newTask.setTitle((title == null || title.trim().isEmpty()) ? "Untitled Task" : title);
        newTask.setDescription(description);
        newTask.setStatus("Pending");
        taskRepository.save(newTask);
        return "redirect:/";
    }

    @GetMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
        return "redirect:/";
    }


    // عرض صفحة التعديل
    @GetMapping("/tasks/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
    model.addAttribute("task", taskRepository.findById(id).orElseThrow());
    return "edit"; // سيبحث عن ملف اسمه edit.html
}

   // حفظ التعديلات
   @PostMapping("/tasks/update/{id}")
   public String updateTask(@PathVariable Long id, @RequestParam String title, @RequestParam String description) {
    Task task = taskRepository.findById(id).orElseThrow();
    task.setTitle(title);
    task.setDescription(description);
    taskRepository.save(task);
    return "redirect:/";
}


   @GetMapping("/tasks/complete/{id}")
   public String completeTask(@PathVariable Long id) {
    Task task = taskRepository.findById(id).orElseThrow();
    task.setStatus("Completed");
    taskRepository.save(task);
    return "redirect:/";
}

    
}