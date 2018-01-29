package ru.benzoback.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.benzoback.library.dao.PersonDAO;

@RestController
@RequestMapping("/api/persons")
public class RestCont {

    @Autowired
    PersonDAO personDAO;

    @GetMapping
    public ResponseEntity<?> getPersons() {
        return ResponseEntity.ok(personDAO.getPersons());
    }
}
