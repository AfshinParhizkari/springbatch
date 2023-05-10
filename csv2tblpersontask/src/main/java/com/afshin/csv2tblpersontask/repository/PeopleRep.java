package com.afshin.csv2tblpersontask.repository;

import com.afshin.csv2tblpersontask.entity.Person;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PeopleRep extends JpaRepository<Person, String> {
}
