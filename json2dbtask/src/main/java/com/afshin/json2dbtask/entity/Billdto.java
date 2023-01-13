package com.afshin.json2dbtask.entity;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Billdto {
	private String firstName;
	private String lastName;
	private Integer minutes;
	private Integer dataUsage;
}
