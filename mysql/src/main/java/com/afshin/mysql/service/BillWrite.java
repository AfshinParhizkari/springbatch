package com.afshin.mysql.service;
/**
 * @Project spring-cloud-task-edu-samples
 * @Author Afshin Parhizkari
 * @Date 2022 - 07 - 04
 * @Time 10:05 AM
 * Created by   IntelliJ IDEA
 * Email:       Afshin.Parhizkari@gmail.com
 * Description:
 */

import com.afshin.mysql.entity.Bill;
import com.afshin.mysql.repository.BillRep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BillWrite implements ItemWriter<Bill> {
    @Autowired
    BillRep dao;
    @Override
    public void write(List<? extends Bill> items) throws Exception {
        for(Bill bill:items){
            dao.save(bill);
        }
    }
}