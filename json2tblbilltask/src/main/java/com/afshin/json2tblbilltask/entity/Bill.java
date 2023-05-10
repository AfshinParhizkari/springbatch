package com.afshin.json2tblbilltask.entity;

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
@Table(name = "bill_statement")
public class Bill {
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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

 	public Bill(String firstName, String lastName, Integer dataUsage, Integer minutes, Double billAmount, Date createDate) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dataUsage = dataUsage;
		this.minutes = minutes;
		this.billAmount = billAmount;
		this.createDate = createDate;
	}
}