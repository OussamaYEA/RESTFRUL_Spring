package com.socialnetwork.socialnetwork.api.post;

import com.socialnetwork.socialnetwork.api.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImp implements PostService{
    @Autowired
    private PostRepository postRepository;
    @Override
    public void addPost(Post post){
        postRepository.save(post);
    }
    @Override
    public void updatePost(Post existPost, Post post){
        existPost.setImage( (post.getImage() != null && !post.getImage().isBlank()) ? post.getImage() : existPost.getImage() );
        existPost.setLink( (post.getLink() != null && !post.getLink().isBlank()) ? post.getLink() : existPost.getLink() );
        existPost.setText( (post.getText() != null && !post.getText().isBlank()) ? post.getText() : existPost.getText() );
        existPost.setTags( (post.getTags() != null && !post.getTags().isEmpty()) ? post.getTags() : existPost.getTags() );
        postRepository.save(existPost);

    }
    @Override
    public void deletePost(int id){
        postRepository.deleteById(id);
    }
    @Override
    public Optional<Post> getPostById(int id){
        return postRepository.findById(id);
    }
    @Override
    public List<Post> getPostsByUser(User owner, int page, int size){
        List<Post> posts = postRepository.findByOwner(owner);
        size = size ==0 ? posts.size() : size;

        PageRequest pageRequest = PageRequest.of(page, size);

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), posts.size());

        return posts.subList(start, end);

    }

    @Override
    public List<Post> getPosts(String field, int page, int size){
        List<Post> posts = postRepository.findAll(Sort.by(Sort.Direction.ASC, field));
        size = size ==0 ? posts.size() : size;
        PageRequest pageRequest = PageRequest.of(page, size);

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), posts.size());

        List<Post> pageContent = posts.subList(start, end);
        return pageContent;

    }
}
