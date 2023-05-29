package com.onerivet.deskbook.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "[ModeOfWork]", schema = "Ref")
public class ModeOfWork {
	
	@Id
	@Column(name = "ModeOfWorkId")
	private int id;
	
	@Column(name = "ModeOfWork")
	private String modeOfWork;
}
