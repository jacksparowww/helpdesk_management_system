package com.helpdesk.helpdesk.service;
import com.helpdesk.helpdesk.entity.*;
import com.helpdesk.helpdesk.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, TicketRepository ticketRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
    }

    public Comment addComment(Long ticketId, Long UserId,String text) {
        Optional<Ticket> optionalTicket = ticketRepository.findById(ticketId);
        if(optionalTicket.isEmpty()){
            return null;
        }
        Optional<User> optionalUser = userRepository.findById(UserId);
        if(optionalUser.isEmpty()){
            return null;
        }
        Comment comment = new Comment();
        comment.setText(text);
        comment.setTicket(optionalTicket.get());
        comment.setUser(optionalUser.get());
        comment.setCreatedAt(LocalDateTime.now());

        return commentRepository.save(comment);
    }
    public List<Comment> getCommentsByTicketId(Long ticketId) {
        return commentRepository.findByticketId(ticketId);
    }
}

