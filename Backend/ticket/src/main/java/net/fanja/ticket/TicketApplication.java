package net.fanja.ticket;

import net.fanja.ticket.dtos.UserDto;
import net.fanja.ticket.entities.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class TicketApplication {



	public static void main(String[] args) {
		SpringApplication.run(TicketApplication.class, args);

		List<UserDto> dtos = new ArrayList<>();

	}

}
