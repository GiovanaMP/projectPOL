package com.example.demo.controller;

import com.example.demo.model.Processo;

import com.example.demo.service.ProcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/processos")
public class ProcessoController {

    @Autowired
    private ProcessoService processoService;

    @GetMapping
    public List<Processo> listarTodos() {
        return processoService.listarTodos();
    }

    @GetMapping("/{numeroProcesso}")
    public ResponseEntity<Processo> buscarPorNumero(@PathVariable String numeroProcesso) {
        Optional<Processo> processo = processoService.buscarPorNumero(numeroProcesso);
        return processo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Processo> criarProcesso(@RequestBody Processo processo) {
        return ResponseEntity.ok(processoService.criarProcesso(processo));
    }

    @PutMapping("/{numeroProcesso}")
    public ResponseEntity<Processo> atualizarProcesso(@PathVariable String numeroProcesso, @RequestBody Processo processo) {
        return ResponseEntity.ok(processoService.atualizarProcesso(numeroProcesso, processo));
    }

    @DeleteMapping("/{numeroProcesso}")
    public ResponseEntity<Void> removerProcesso(@PathVariable String numeroProcesso) {
        processoService.removerProcesso(numeroProcesso);
        return ResponseEntity.noContent().build();
    }
}
