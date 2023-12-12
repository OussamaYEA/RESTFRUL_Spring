package com.socialnetwork.socialnetwork.api.comment;

import com.socialnetwork.socialnetwork.api.post.Post;
import com.socialnetwork.socialnetwork.api.user.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CommentServiceImp implements CommentService  {

    @Autowired
    private CommentRepository commentRepository;
    @Override
    public void newComment(Comment comment) {
        commentRepository. save(comment);
    }

    @Override
    public void deleteComment(Integer id) {
        commentRepository.deleteById(id);
    }

    @Override
    public List<Comment> getComments(int page, int size) {
        List<Comment> comments = commentRepository.findAll(Sort.by(Sort.Direction.ASC, "publishDate"));
        size = size ==0 ? comments.size() : size;
        PageRequest pageRequest = PageRequest.of(page, size);

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), comments.size());

        List<Comment> pageContent = comments.subList(start, end);
        return pageContent;
    }

    @Override
    public List<Comment> getCommentsByPost(Post post, int page, int size) {
        List<Comment> comments = commentRepository.findByPost(post);
        size = size ==0 ? comments.size() : size;
        PageRequest pageRequest = PageRequest.of(page, size);

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), comments.size());

        List<Comment> pageContent = comments.subList(start, end);
        return pageContent;
    }

    @Override
    public List<Comment> getCommentsByUser(User user, int page, int size) {
        List<Comment> comments = commentRepository.findByOwner(user);
        size = size ==0 ? comments.size() : size;
        PageRequest pageRequest = PageRequest.of(page, size);

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), comments.size());

        List<Comment> pageContent = comments.subList(start, end);
        return pageContent;
    }
}
