package com.example.demo.service;

import com.example.demo.model.Reu;
import com.example.demo.model.Processo;
import com.example.demo.repository.ReuRepository;
import com.example.demo.repository.ProcessoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ReuService {

    @Autowired
    private ReuRepository reuRepository;

    @Autowired
    private ProcessoRepository processoRepository;

    // 🔹 Listar todos os réus cadastrados
    public List<Reu> listarTodos() {
        return reuRepository.findAll();
    }

    // 🔹 Criar um novo réu (somente se não existir)
    public Reu criarReu(Reu reu) {
        Optional<Reu> reuExistente = reuRepository.findByCpf(reu.getCpf());
        return reuExistente.orElseGet(() -> reuRepository.save(reu));
    }

    // 🔹 Buscar réu pelo CPF
    public Optional<Reu> buscarPorCpf(String cpf) {
        return reuRepository.findByCpf(cpf);
    }

    // 🔹 Listar todos os réus associados a um processo específico
    public Set<Reu> listarReusDeProcesso(String numeroProcesso) {
        Processo processo = processoRepository.findByNumeroProcesso(numeroProcesso)
                .orElseThrow(() -> new RuntimeException("Processo não encontrado"));

        return processo.getReus();
    }

    // 🔹 Listar todos os processos nos quais um réu está envolvido (busca pelo CPF)
    public Set<Processo> listarProcessosDeReu(String cpf) {
        Reu reu = reuRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Réu não encontrado"));

        return reu.getProcessos();
    }
}
