package net.fanja.ticket.services;

import net.fanja.ticket.dtos.TicketDto;
import net.fanja.ticket.dtos.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> findAll();

    UserDto create(UserDto userDto);

    UserDto update(UserDto userDto) throws Exception;

    List<TicketDto> getTicketsAssignedToUser(Long userId);

    UserDto getUserById(Long id);
}
