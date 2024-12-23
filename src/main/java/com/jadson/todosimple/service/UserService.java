package com.jadson.todosimple.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jadson.todosimple.models.User;
import com.jadson.todosimple.repository.TaskRepository;
import com.jadson.todosimple.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;


    public User findById(Integer id) {
        //? O "Optional" é usado para que se a requisição não existir, não terá "null" como retorno. mas sim "Vazío"
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException(
            "Usuário não encontrado! Id:" + id + ", Tipo: " + User.class.getName()
        ));
    }

    @Transactional
    public User create (User obj){
        obj = this.userRepository.save(obj);
        this.taskRepository.saveAll(obj.getTasks());
        return obj;
    }

    @Transactional
    public User update (User obj) {
        User newObj = findById(obj.getId());
        newObj.setPassword(obj.getPassword());
        return this.userRepository.save(newObj);
    }

    public void delete(Integer id){
        findById(id); 
        try {
            this.userRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não é possível excluir pois há tasks realcionadas!");
        }
    }
  
}
