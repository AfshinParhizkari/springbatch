/*
 * Copyright 2019 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *          https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.afshin.h2.service;

import com.afshin.h2.entity.Bill;
import com.afshin.h2.entity.H2Input;
import org.springframework.batch.item.ItemProcessor;
import java.util.Date;

public class BillProcessor implements ItemProcessor<H2Input, Bill> {

	@Override
	public Bill process(H2Input Input) {
		Double billAmount = Input.getDataUsage() * .001 + Input.getMinutes() * .01;
		return new Bill(Input.getBillId(), Input.getFirstName(), Input.getLastName(),
				Input.getDataUsage(), Input.getMinutes(), billAmount,new Date());
	}
}
