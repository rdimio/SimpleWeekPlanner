package ru.mycreation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mycreation.entities.Role;
import ru.mycreation.repository.RoleRepository;

import java.util.List;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    public List<Role> findAll(){ return (List<Role>) roleRepository.findAll();}
}
