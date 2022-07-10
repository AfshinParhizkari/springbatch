package com.afshin.h2.entity;

import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "BILL_STATEMENTS")
public class Bill {
	@Id
	@Column(name = "bill_id")
	private Integer billId;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "data_usage")
	private Integer dataUsage;
	@Column(name = "minutes")
	private Integer minutes;
	@Column(name = "bill_amount")
	private Double billAmount;
 	@UpdateTimestamp //automatically updates the changerdate last modified timestamp.
	@Column(name = "create_date")
	private Date createDate;
}