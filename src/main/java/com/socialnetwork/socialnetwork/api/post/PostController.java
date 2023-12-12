package com.socialnetwork.socialnetwork.api.post;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.socialnetwork.socialnetwork.api.user.User;
import com.socialnetwork.socialnetwork.api.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;




    @PostMapping("/post")
    public ResponseEntity<Object> newPost(@RequestParam("post") String post) throws Exception {

        Post postJson = new Post();
        ObjectMapper objectMapper = new ObjectMapper();
        postJson = objectMapper.readValue(post, Post.class);

        Optional<User> owner = userService.getUserById(postJson.getOwner().getId());
        postJson.setOwner(owner.get());
        postService.addPost(postJson);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }
    @PutMapping("/post/{id}")
    public ResponseEntity<Object> updatePost(@PathVariable int id, @RequestParam("post") String post) throws Exception{
        Optional<Post> existPost = postService.getPostById(id);
        if (existPost.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Post postJson = new Post();
        ObjectMapper objectMapper = new ObjectMapper();
        postJson = objectMapper.readValue(post, Post.class);
        postService.updatePost(existPost.get(), postJson);
        
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<Object> getPosts(@RequestParam(defaultValue = "0") Integer pageNo,
                                           @RequestParam(defaultValue = "0") Integer pageSize,
                                           @RequestParam(defaultValue = "id") String sortBy){
        List<Post> posts = postService.getPosts(sortBy, pageNo, pageSize);
        return new ResponseEntity<>(posts,HttpStatus.OK);
    }

    @GetMapping("/posts/user/{id}")
    public ResponseEntity<Object> getPostsByUser(@PathVariable int id,
                                            @RequestParam(defaultValue = "0") Integer pageNo,
                                           @RequestParam(defaultValue = "0") Integer pageSize){
        Optional<User> owner = userService.getUserById(id);
        if (owner.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Post> posts = postService.getPostsByUser(owner.get(), pageNo, pageSize);
        return new ResponseEntity<>(posts,HttpStatus.OK);
    }

    @GetMapping("/posts/tag/{tag}")
    public ResponseEntity<Object> getPostsByTag(@PathVariable String tag,
                                                @RequestParam(defaultValue = "id") String sortBy,
                                                 @RequestParam(defaultValue = "0") Integer pageNo,
                                                 @RequestParam(defaultValue = "0") Integer pageSize){


        List<Post> posts = postService.getPosts(sortBy, pageNo, pageSize);
        List<Post> results = posts.stream()
                .filter(a -> a.getTags().stream()
                        .anyMatch(u -> u.getName().equals(tag)))
                .toList();
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<Object> getPostById(@PathVariable int id){
        Optional<Post> post = postService.getPostById(id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<Object> demetePostById(@PathVariable int id){
        postService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
