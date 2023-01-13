package com.afshin.csv2dbtask.repository;

import com.afshin.csv2dbtask.entity.Person;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PeopleRep extends JpaRepository<Person, String> {
}
