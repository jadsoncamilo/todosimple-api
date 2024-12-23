package com.jadson.todosimple.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import com.jadson.todosimple.models.Task;
import org.springframework.stereotype.Service;

import com.jadson.todosimple.models.User;
import com.jadson.todosimple.repository.TaskRepository;

import jakarta.transaction.Transactional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task findById(Integer id){

        Optional<Task> task = this.taskRepository.findById(id);
        return task.orElseThrow(() -> new RuntimeException(
            "Tarefa não encontrada ! Id" + id + ", Tipo: " + Task.class.getName() ));
    }

    @Transactional
    public Task create (Task obj){

        User user = this.userService.findById(obj.getUser().getId());
        obj.setId(null);
        obj.setUser(user);
        obj =  this.taskRepository.save(obj);
        return obj;
    }
    
    @Transactional
    public  Task update (Task obj){

        Task newObj = findById(obj.getId());
        newObj.setDescription(obj.getDescription());
        return this.taskRepository.save(newObj);
    }

    public void delete (Integer id){
        findById(id);

        try {
            this.taskRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não é possível excluir pois há entidades realcionadas!");
        }
    }

}
