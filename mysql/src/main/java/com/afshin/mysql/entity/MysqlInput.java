package com.afshin.mysql.entity;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MysqlInput {
	private String firstName;
	private String lastName;
	private Integer minutes;
	private Integer dataUsage;
}
