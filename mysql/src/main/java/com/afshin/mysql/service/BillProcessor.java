package com.afshin.mysql.service;

import com.afshin.mysql.entity.Bill;
import com.afshin.mysql.entity.MysqlInput;
import org.springframework.batch.item.ItemProcessor;
import java.util.Date;

public class BillProcessor implements ItemProcessor<MysqlInput, Bill> {

	@Override
	public Bill process(MysqlInput Input) {
		Double billAmount = Input.getDataUsage() * .001 + Input.getMinutes() * .01;
		return new Bill(Input.getFirstName(), Input.getLastName(),
				Input.getDataUsage(), Input.getMinutes(), billAmount,new Date());
	}
}
