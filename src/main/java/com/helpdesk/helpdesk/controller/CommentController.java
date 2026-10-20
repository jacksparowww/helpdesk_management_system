package com.helpdesk.helpdesk.controller;
import com.helpdesk.helpdesk.entity.Comment;
import com.helpdesk.helpdesk.service.CommentService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/tickets")
public class CommentController {


        private final CommentService commentService;

        public CommentController(CommentService commentService) {
            this.commentService = commentService;
        }

        // ADD COMMENT
        @PostMapping("/{ticketId}/comments")
        public Comment addComment(
                @PathVariable Long ticketId,
                @RequestParam Long userId,
                @RequestBody Comment comment) {

            return commentService.addComment(
                    ticketId,
                    userId,
                    comment.getText()
            );
        }

        // GET COMMENTS
        @GetMapping("/{ticketId}/comments")
        public List<Comment> getComments(
                @PathVariable Long ticketId) {

            return commentService.getCommentsByTicketId(ticketId);
        }

}

