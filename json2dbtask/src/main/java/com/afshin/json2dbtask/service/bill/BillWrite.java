package com.afshin.json2dbtask.service.bill;
/**
 * @Project spring-cloud-task-edu-samples
 * @Author Afshin Parhizkari
 * @Date 2022 - 07 - 04
 * @Time 10:05 AM
 * Created by   IntelliJ IDEA
 * Email:       Afshin.Parhizkari@gmail.com
 * Description:
 */

import com.afshin.json2dbtask.entity.Bill;
import com.afshin.json2dbtask.repository.BillRep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class BillWrite implements ItemWriter<Bill> {
    @Autowired
    BillRep dao;

    @Override
    public void write(List<? extends Bill> items) throws Exception {
        for(Bill bill:items)
            dao.save(bill);
    }
}