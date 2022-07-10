package com.afshin.mysql;

import com.afshin.mysql.entity.Bill;
import com.afshin.mysql.repository.BillRep;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import java.util.Date;

@SpringBootTest(classes={MysqlApplication.class})
@ContextConfiguration(classes={Bill.class, BillRep.class})
class MysqlApplicationTests {
    @Autowired(required=true) BillRep dao;
    @Autowired Bill bill;

    @Test
    void contextLoads() {
        bill =new Bill("test","test",1,1,1.5,new Date());
        dao.save(bill);
    }
}
