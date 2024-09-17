package net.fanja.ticket.services.impl;

import lombok.extern.slf4j.Slf4j;
import net.fanja.ticket.dtos.TicketDto;
import net.fanja.ticket.dtos.UserDto;
import net.fanja.ticket.entities.Ticket;
import net.fanja.ticket.entities.User;
import net.fanja.ticket.exceptions.EntityNotFoundException;
import net.fanja.ticket.exceptions.ErrorCodes;
import net.fanja.ticket.exceptions.InvalidEntityException;
import net.fanja.ticket.exceptions.InvalidOperationException;
import net.fanja.ticket.mapper.TicketMapper;
import net.fanja.ticket.mapper.UserMapper;
import net.fanja.ticket.repository.TicketRepository;
import net.fanja.ticket.repository.UserRepository;
import net.fanja.ticket.services.TicketService;
import net.fanja.ticket.services.UserService;
import net.fanja.ticket.validators.TicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketMapper ticketMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    /*@Override
    public List<TicketDto> findAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();

        List<TicketDto> ticketDtos = new ArrayList<>();
        for (Ticket ticket : tickets) {
            TicketDto ticketDto = findTicketById(ticket.getId());
            ticketDtos.add(ticketDto);
        }

        return ticketDtos;
    }*/

    public List<TicketDto> findAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        // Transformation des entités Ticket en TicketDto
        return tickets.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private TicketDto convertToDto(Ticket ticket) {
        return TicketDto.builder()
                .id(ticket.getId())
                .titre(ticket.getTitre())
                .description(ticket.getDescription())
                .statutTicket(ticket.getStatutTicket())
                .userId(ticket.getUser() != null ? ticket.getUser().getId() : null)
                .build();
    }

    private Ticket convertToEntity(TicketDto ticketDto, User user) {
        Ticket ticket = new Ticket();
        ticket.setId(ticketDto.getId());
        ticket.setTitre(ticketDto.getTitre());
        ticket.setDescription(ticketDto.getDescription());
        ticket.setStatutTicket(ticketDto.getStatutTicket());
        ticket.setUser(user);
        return ticket;
    }

    private Ticket convertTicketToEntity(TicketDto ticketDto) {
        if (ticketDto == null) {
            return null;
        }

        Ticket ticket = new Ticket();
        ticket.setId(ticketDto.getId());
        ticket.setTitre(ticketDto.getTitre());
        ticket.setDescription(ticketDto.getDescription());
        ticket.setStatutTicket(ticketDto.getStatutTicket());


        return ticket;
    }

    public User convertToEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());

        if (userDto.getTicketDtoList() != null) {
            user.setTicketList(userDto.getTicketDtoList().stream()
                    .map(this::convertTicketToEntity)
                    .collect(Collectors.toList()));
        }

        return user;
    }



    @Override
    public void deleteTicket(Long id) {
        if (id != null) {
            Ticket ticket = ticketRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Ticket with id " + id + " not found"));
            ticketRepository.delete(ticket);
            log.info("delete() - Facture with id {} deleted successfully", id);
        } else {
            log.error("delete() - Failed to delete Facture. Id is null");
        }
        }

    @Override
    public TicketDto findTicketById(Long id) {
        if (id == null) {
            log.error("Ticket ID is null");
            return null;
        }
        return ticketRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        "no ticket with id" + id + "found",
                        ErrorCodes.TICKET_NOT_FOUND)
                );

    }

    @Override
    public TicketDto updateTicket(TicketDto ticketDto) throws Exception {

        if (ticketDto == null) {
            throw new Exception("Failed to update Ticket : provided TicketDto is null !");
        } else {

            Long ticketDtoId = ticketDto.getId();
            Ticket ticket = ticketRepository.findById(ticketDtoId).orElse(null);

            User user = null;
            if (ticketDto.getUserId() != null) {
                user = userRepository.findById(ticketDto.getUserId())
                        .orElseThrow(() -> new EntityNotFoundException("User not found"));
            }

               Ticket t1 = convertToEntity(ticketDto, user);

                Ticket ticket1 = ticketRepository.save(t1);

                return convertToDto(ticket1);
            }

    }

    @Override
    public TicketDto createTicket(TicketDto ticketDto) throws Exception {
        List<String> errors = TicketValidator.validate(ticketDto);
        if (!errors.isEmpty()) {
            log.error("Ticket is not valid {}", ticketDto);
            throw new InvalidEntityException("Ticket is not valid", ErrorCodes.TICKET_NOT_VALID, errors);
        }

        User user = null;
        if (ticketDto.getUserId() != null) {
            user = userRepository.findById(ticketDto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));
        }

        Ticket t1 = convertToEntity(ticketDto, user);

        Ticket ticket1 = ticketRepository.save(t1);
        return convertToDto(ticket1);
    }

    @Override
    public TicketDto assignTicket(TicketDto ticketDto, Long userId) throws Exception {

            Long ticketDtoId = ticketDto.getId();
            Ticket ticket = ticketRepository.findById(ticketDtoId).orElse(null);

            if (ticket == null) {
                throw new Exception(
                        String.format("Failed to update Ticket : Ticket with id %d not found !", ticketDtoId));
            }
            /*else {
                TicketDto dto = findTicketById(ticketDtoId);
                ticket.setId(dto.getId());
                ticket.setDescription(dto.getDescription());
                ticket.setTitre(dto.getTitre());
                ticket.setStatutTicket(dto.getStatutTicket());*/
                Long idUser = ticketDto.getUserId();
                if (idUser != null) {
                    throw new InvalidOperationException("Ticket is already assigned !", ErrorCodes.TICKET_ALREADY_ASSIGN);
                }
                else {
                    TicketDto dto = findTicketById(ticketDtoId);
                    ticket.setId(dto.getId());
                    ticket.setDescription(dto.getDescription());
                    ticket.setTitre(dto.getTitre());
                    ticket.setStatutTicket(dto.getStatutTicket());
                    UserDto userDto = userService.getUserById(userId);
                    User user1 = convertToEntity(userDto);

                    Ticket t1 = convertToEntity(dto, user1);

                    Ticket ticket1 = ticketRepository.save(t1);

                    return convertToDto(ticket1);




        }
    }

    @Override
    public List<TicketDto> findAllTicketsByUserId(Long userId) {
        List<Ticket> tickets = new ArrayList<>();

    return List.of();}
}



