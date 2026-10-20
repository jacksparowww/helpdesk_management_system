package com.helpdesk.helpdesk.repository;

import com.helpdesk.helpdesk.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByticketId(Long ticketId);
}

