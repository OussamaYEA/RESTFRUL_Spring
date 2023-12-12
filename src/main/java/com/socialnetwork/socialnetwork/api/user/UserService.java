package com.socialnetwork.socialnetwork.api.user;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void addUser(User user);
    void updateUser(Integer id, User user);
    void deleteUser(Integer id);

    List<User> getUsers();
    List<User> getUsersBySorting(String field);
    Page<User> getUsersByPagination(int page, int size);
    List<User> getUsersBySortingAndPagination(String field, int page, int size);


    Optional<User> getUserById(Integer id);
}
