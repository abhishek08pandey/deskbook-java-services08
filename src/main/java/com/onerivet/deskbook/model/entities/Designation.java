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
@Table(name = "Designation", schema = "Ref")
public class Designation {

	@Id
	@Column(name = "DesignationId")
	private int id;
	
	@Column(name = "DesignationName")
	private String designationName;
	
}
