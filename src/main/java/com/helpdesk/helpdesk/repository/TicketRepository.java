package com.helpdesk.helpdesk.repository;
import com.helpdesk.helpdesk.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByStatus(String status);
    List<Ticket> findByCreatedById(Long userId);
}
