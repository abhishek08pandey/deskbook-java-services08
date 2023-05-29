package com.onerivet.deskbook.model.entities;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "[EmployeeWorkingDays]", schema = "dbo")
public class EmployeeWorkingDays {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EmployeeWorkingDayId")
	private int id;
	
	@OneToOne
	@JoinColumn(name = "EmployeeId")
	private Employee employeeId;
	
	@OneToOne
	@JoinColumn(name = "WorkingDayId")
	private WorkingDay workingDayId;
	
	@Column(name = "CreatedDate")
	private LocalDateTime createdDate;
	
	@OneToOne
	@JoinColumn(name = "CreatedBy")
	private Employee createdBy;
	
	@Column(name = "ModifiedDate")
	private LocalDateTime modifiedDate;
	
	@OneToOne
	@JoinColumn(name = "ModifiedBy")
	private Employee modifiedBy;
	
	@Column(name = "DeletedDate")
	private LocalDateTime deletedDate;
	
	@OneToOne
	@JoinColumn(name = "DeletedBy")
	private Employee deletedBy;
}
