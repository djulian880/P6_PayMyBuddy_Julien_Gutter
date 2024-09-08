package com.openclassrooms.paymybuddyjg.DTO;

import lombok.Data;

@Data
public class TransactionDTO {
	public String description;
	public Double amount;
	public String selectedRelation;
}
