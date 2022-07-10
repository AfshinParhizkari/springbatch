package com.afshin.mysql.repository;
/**
 * @Project spring-cloud-task-edu-samples
 * @Author Afshin Parhizkari
 * @Date 2022 - 07 - 04
 * @Time 9:46 AM
 * Created by   IntelliJ IDEA
 * Email:       Afshin.Parhizkari@gmail.com
 * Description:
 */
import com.afshin.mysql.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRep extends JpaRepository<Bill,Integer> {
}