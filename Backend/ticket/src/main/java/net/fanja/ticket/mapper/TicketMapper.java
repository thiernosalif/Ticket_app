package net.fanja.ticket.mapper;

import net.fanja.ticket.dtos.TicketDto;
import net.fanja.ticket.dtos.UserDto;
import net.fanja.ticket.entities.Ticket;
import net.fanja.ticket.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    /**
     * Convert a TicketDto object to a Ticket one
     */
    Ticket toEntity(TicketDto ticketDto);

    /**
     * Convert a Ticket object to a TicketDto one
     */
    TicketDto fromEntity(Ticket ticket);
}
