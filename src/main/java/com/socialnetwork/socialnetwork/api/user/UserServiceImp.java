package com.socialnetwork.socialnetwork.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateUser(Integer id, User updateUser) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        user.setTitle( (updateUser.getTitle() != null && !updateUser.getTitle().isBlank() ) ? updateUser.getTitle() : user.getTitle());

        user.setFirstName( (updateUser.getFirstName() != null & !updateUser.getFirstName().isBlank()) ? updateUser.getFirstName() : user.getFirstName());

        user.setLastName( (updateUser.getLastName() != null &&!updateUser.getLastName().isBlank()) ? updateUser.getLastName() : user.getLastName());

        user.setGender( (updateUser.getGender() != null && !updateUser.getGender().isBlank()) ? updateUser.getGender() : user.getGender());

        //user.setEmail( (updateUser.getEmail() != null && !updateUser.getEmail().isBlank()) ? updateUser.getEmail() : user.getEmail());

        user.setPhone( (updateUser.getPhone() != null && !updateUser.getPhone().isBlank()) ? updateUser.getPhone() : user.getPhone());

        user.setPicture( (updateUser.getPicture() != null && !updateUser.getPicture().isBlank() ) ? updateUser.getPicture() : user.getPicture());


        userRepository.save(user);
    }
    @Override
    public void deleteUser(Integer id ){
        userRepository.deleteById(id);
    }
    @Override
    @Cacheable(cacheNames = "userById", key = "#id")
    public Optional<User> getUserById(Integer id ) {return userRepository.findById(id);};

    @Override
    public List<User> getUsers(){return userRepository.findAll();};

    @Override
    public List<User> getUsersBySorting(String field){
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, field));
    }
    @Override
    public Page<User> getUsersByPagination(int page, int size){
        Page<User> users = userRepository.findAll(PageRequest.of(page,size));
        return users;
    }
    @Override
    public List<User> getUsersBySortingAndPagination(String field, int page, int size){
        /*Pageable paging = PageRequest.of(page, size, Sort.by(field));
        Page<User> pagedResult = userRepository.findAll(paging);
        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<User>();
        }*/

        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.ASC, field));
        size = size ==0 ? users.size() : size;

        PageRequest pageRequest = PageRequest.of(page, size);

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), users.size());

        List<User> pageContent = users.subList(start, end);
        return pageContent;
    }


}
