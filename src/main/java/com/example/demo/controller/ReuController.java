package com.example.demo.controller;

import com.example.demo.model.Reu;
import com.example.demo.service.ReuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reus")
public class ReuController {

    @Autowired
    private ReuService reuService;

    @GetMapping
    public List<Reu> listarTodos() {
        return reuService.listarTodos();
    }

    @PostMapping
    public ResponseEntity<Reu> criarReu(@RequestBody Reu reu) {
        return ResponseEntity.ok(reuService.criarReu(reu));
    }
}
