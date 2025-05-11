package bus.model.transaction;

import java.time.LocalDate;


public record TransactionRecord(
		Integer historicID,
		String type,
		Double newBalance,
		Double operation,
		LocalDate date
		) {}
