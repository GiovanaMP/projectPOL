package com.example.demo.controller;

import com.example.demo.model.Processo;
import com.example.demo.model.Reu;
import com.example.demo.service.ProcessoService;
import com.example.demo.service.ReuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/processos-reus") // Verifique se o prefixo está correto
public class ProcessoReuController {

    @Autowired
    private ProcessoService processoService;

    @Autowired
    private ReuService reuService;

    // ✅ Adicionar réu ao processo via CPF (corpo JSON)
    @PostMapping("/{numeroProcesso}/adicionar-reu")
    public ResponseEntity<Processo> adicionarReu(@PathVariable String numeroProcesso, @RequestBody Reu reu) {
        Processo processo = processoService.buscarPorNumero(numeroProcesso)
                .orElseThrow(() -> new RuntimeException("Processo não encontrado"));

        // Buscar o réu pelo CPF no banco de dados
        Reu reuExistente = reuService.buscarPorCpf(reu.getCpf())
                .orElseGet(() -> reuService.criarReu(reu)); // Se não existir, cria um novo

        processo.getReus().add(reuExistente);
        processoService.atualizarProcesso(numeroProcesso, processo); // Aqui pode estar faltando o método correto!

        return ResponseEntity.ok(processo);
    }

    // ✅ Listar todos os réus de um processo
    @GetMapping("/{numeroProcesso}/reus")
    public ResponseEntity<Set<Reu>> listarReusDeProcesso(@PathVariable String numeroProcesso) {
        Processo processo = processoService.buscarPorNumero(numeroProcesso)
                .orElseThrow(() -> new RuntimeException("Processo não encontrado"));
        return ResponseEntity.ok(processo.getReus());
    }

    // ✅ Listar todos os processos de um réu pelo CPF
    @GetMapping("/reu/{cpf}")
    public ResponseEntity<Set<Processo>> listarProcessosDeReu(@PathVariable String cpf) {
        Reu reu = reuService.buscarPorCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Réu não encontrado"));
        return ResponseEntity.ok(reu.getProcessos());
    }
}
