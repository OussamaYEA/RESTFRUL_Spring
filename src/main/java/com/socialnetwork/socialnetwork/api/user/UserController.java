package com.socialnetwork.socialnetwork.api.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialnetwork.socialnetwork.api.services.uploadFile.uploadFile;
import com.socialnetwork.socialnetwork.api.utils.Converter;
import com.socialnetwork.socialnetwork.api.utils.ResponseHandler;
import com.socialnetwork.socialnetwork.api.utils.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    private Timestamp ts ;



    @PostMapping("/signup")
    public ResponseEntity<Object> newUser(@RequestParam(value = "file",required = false) MultipartFile file, @RequestParam("user") String user) throws Exception {

        try{
            List<String> errors = new ArrayList<>();

            User userJson = new User();
            ObjectMapper objectMapper = new ObjectMapper();
            userJson = objectMapper.readValue(user, User.class);

            if(file != null) {
                String fileName = uploadFile.upload(file, "image", "user");
                if (fileName == null){
                    return new ResponseEntity<>("Image content or size invalid", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
                }
                userJson.setPicture(fileName);
            }else {
                userJson.setPicture("ee");
            }

            if (!Validator.emailValidation(userJson.getEmail())){
                errors.add("Invalid Email");
            }

            if (!errors.isEmpty()){
                return ResponseHandler.errorResponseBuilder(errors);
            }

            userJson.setRegisterDate(Timestamp.from(Instant.now()));


            userService.addUser(userJson);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable int id, @RequestParam(value = "file",required = false) MultipartFile file, @RequestParam("user") String user) throws Exception {

        try{
            User userJson = new User();
            ObjectMapper objectMapper = new ObjectMapper();
            userJson = objectMapper.readValue(user, User.class);

            if(file != null) {
                String fileName = uploadFile.upload(file, "image", "user");
                if (fileName == null){
                    return new ResponseEntity<>("Image content or size invalid", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
                }
                userJson.setPicture(fileName);
            }
            if (userJson.getEmail() != null && !userJson.getEmail().isBlank()){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            userService.updateUser(id, userJson);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable int id) throws Exception {
        
        try{
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable int id) throws Exception{
        Optional<User> user = userService.getUserById(id);
        return new ResponseEntity<>(user.get(),HttpStatus.OK);
    }

    @GetMapping(path = {"/users"})
    public ResponseEntity<List<User>> getUsers(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "0") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy){

        List<User> users = userService.getUsersBySortingAndPagination(sortBy, pageNo, pageSize);

        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }




}
