package dto;

public record UserDTO(
		int id,
		String firstname,
		String lastname,
		String pin,
		String address,
		String telephoneNumber,
		String email
		) {}
