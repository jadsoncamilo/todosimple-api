package com.jadson.todosimple.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jadson.todosimple.models.Task;
import com.jadson.todosimple.service.TaskService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/task")
@Validated
public class TaskController {
    
    @Autowired
    private TaskService taskService;

    @GetMapping("/{id}")
    public ResponseEntity <Task> findById(@PathVariable Integer id){
        
        Task obj = this.taskService.findById(id);
        return ResponseEntity.ok(obj);
    }

    @GetMapping("/user/{id}")
    
    
    public ResponseEntity <List<Task>> findAllByUserId(@PathVariable Integer id){
        List<Task> objs = this.taskService.findAllByUserId(id);
        return ResponseEntity.ok().body(objs);

    }


    @PostMapping
    @Validated
    public ResponseEntity <Void> create(@Valid @RequestBody  Task obj){

        this.taskService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }


    @PutMapping("/{id}")
    @Validated
    public ResponseEntity<Void> update(@Valid @RequestBody Task obj, @PathVariable  Integer id){
        obj.setId(id);
        this.taskService.update(obj);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity <Void> delete(@PathVariable Integer id){
        this.taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
