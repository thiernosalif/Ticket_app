package net.fanja.ticket.mapper;

import net.fanja.ticket.dtos.UserDto;
import net.fanja.ticket.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Convert a UserDto object to a User one
     */
    User toEntity(UserDto userDto);

    /**
     * Convert a User object to a UserDto one
     */
    UserDto fromEntity(User user);
}
