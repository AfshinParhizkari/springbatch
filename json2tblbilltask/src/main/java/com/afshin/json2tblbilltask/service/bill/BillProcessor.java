package com.afshin.json2tblbilltask.service.bill;

import com.afshin.json2tblbilltask.entity.Bill;
import com.afshin.json2tblbilltask.entity.Billdto;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Configuration;
import java.util.Date;

@Configuration
public class BillProcessor implements ItemProcessor<Billdto, Bill> {

	@Override
	public Bill process(Billdto Input) {
		Double billAmount = Input.getDataUsage() * .001 + Input.getMinutes() * .01;
		return new Bill(Input.getFirstName(), Input.getLastName(),
				Input.getDataUsage(), Input.getMinutes(), billAmount,new Date());
	}
}
