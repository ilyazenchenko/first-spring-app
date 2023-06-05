package ru.zenchenko.springprojectone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zenchenko.springprojectone.model.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
}
