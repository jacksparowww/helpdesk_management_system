package com.helpdesk.helpdesk.service;

import com.helpdesk.helpdesk.entity.Ticket;
import com.helpdesk.helpdesk.entity.User;
import com.helpdesk.helpdesk.repository.TicketRepository;
import com.helpdesk.helpdesk.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public TicketService(TicketRepository ticketRepository,
                         UserRepository userRepository) {

        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    // CREATE TICKET
    public Ticket createTicket(Ticket ticket, Long userId) {

        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            return null;
        }

        User user = optionalUser.get();
        if (!user.getRole().equals("USER")) {
            return null;
        }



        ticket.setCreatedBy(user);
        ticket.setStatus("OPEN");
        ticket.setCreationDate(LocalDateTime.now());

        return ticketRepository.save(ticket);
    }

    // UPDATE TICKET DETAILS
    public Ticket updateTicket(Long id, Ticket updatedTicket, Long userId) {

        // Find user
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            return null;
        }

        User user = optionalUser.get();

        Optional<Ticket> optionalTicket = ticketRepository.findById(id);

        if (optionalTicket.isEmpty()) {
            return null;
        }

        Ticket existingTicket = optionalTicket.get();


        if (user.getRole().equals("USER")) {

            if (!existingTicket.getCreatedBy().getId().equals(userId)) {
                return null;
            }
        }



        existingTicket.setTitle(updatedTicket.getTitle());
        existingTicket.setDescription(updatedTicket.getDescription());
        existingTicket.setPriority(updatedTicket.getPriority());

        return ticketRepository.save(existingTicket);
    }

    // DELETE TICKET
    public boolean deleteTicket(Long id, Long userId) {

        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            return false;
        }

        User user = optionalUser.get();

        Optional<Ticket> optionalTicket = ticketRepository.findById(id);

        if (optionalTicket.isEmpty()) {
            return false;
        }

        Ticket ticket = optionalTicket.get();

        // USER can delete only their own ticket
        if (user.getRole().equals("USER")) {
            if (!ticket.getCreatedBy().getId().equals(userId)) {
                return false;
            }
        }

        // AGENT can delete any ticket
        ticketRepository.delete(ticket);

        return true;
    }

    // GET TICKET BY ID
    public Optional<Ticket> getTicketById(Long id) {

        return ticketRepository.findById(id);
    }

    // UPDATE TICKET STATUS
    public Ticket updateStatus(Long id, String status, Long userId) {

        // Check whether the user exists
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            return null;
        }

        User user = optionalUser.get();

        // Only AGENT can update status
        if (!user.getRole().equals("AGENT")) {
            return null;
        }

        // Check whether ticket exists
        Optional<Ticket> optionalTicket = ticketRepository.findById(id);

        if (optionalTicket.isEmpty()) {
            return null;
        }

        // Validate status
        if (!status.equals("OPEN") &&
                !status.equals("IN_PROGRESS") &&
                !status.equals("RESOLVED") &&
                !status.equals("CLOSED")) {

            return null;
        }

        Ticket ticket = optionalTicket.get();

        ticket.setStatus(status);

        return ticketRepository.save(ticket);
    }

    // GET TICKETS BY STATUS
    public List<Ticket> getTicketsByStatus(String status) {

        return ticketRepository.findByStatus(status);
    }

    // GET ALL TICKETS
    public List<Ticket> getAllTickets() {

        return ticketRepository.findAll();
    }

    // GET MY TICKETS
    public List<Ticket> getMyTickets(Long userId) {

        return ticketRepository.findByCreatedById(userId);
    }
}