package com.example.demo.service;

import com.example.demo.model.Processo;
import com.example.demo.model.Reu;
import com.example.demo.repository.ProcessoRepository;
import com.example.demo.repository.ReuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProcessoService {

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private ReuRepository reuRepository;

    public List<Processo> listarTodos() {
        return processoRepository.findAll();
    }

    public Optional<Processo> buscarPorNumero(String numeroProcesso) {
        return processoRepository.findByNumeroProcesso(numeroProcesso);
    }

    public Processo criarProcesso(Processo processo) {
        // Verifica se já existe um processo com o mesmo número
        Optional<Processo> processoExistente = processoRepository.findByNumeroProcesso(processo.getNumeroProcesso());

        if (processoExistente.isPresent()) {
            throw new IllegalArgumentException("Já existe um processo com esse número: " + processo.getNumeroProcesso());
        }

        return processoRepository.save(processo);
    }

    public Processo atualizarProcesso(String numeroProcesso, Processo dadosAtualizados) {
        return processoRepository.findByNumeroProcesso(numeroProcesso)
                .map(processo -> {
                    processo.setDescricao(dadosAtualizados.getDescricao());
                    processo.setStatus(dadosAtualizados.getStatus());
                    processo.setDataAbertura(dadosAtualizados.getDataAbertura());
                    return processoRepository.save(processo);
                })
                .orElseThrow(() -> new RuntimeException("Processo não encontrado"));
    }

    public void removerProcesso(String numeroProcesso) {
        Processo processo = processoRepository.findByNumeroProcesso(numeroProcesso)
                .orElseThrow(() -> new RuntimeException("Processo não encontrado"));
        processoRepository.delete(processo);
    }

    // ✅ Associar um réu a um processo existente pelo CPF
    public Processo adicionarReu(String numeroProcesso, String cpf) {
        Processo processo = processoRepository.findByNumeroProcesso(numeroProcesso)
                .orElseThrow(() -> new RuntimeException("Processo não encontrado"));

        Reu reu = reuRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Réu não encontrado"));

        processo.getReus().add(reu);
        return processoRepository.save(processo);
    }

    // ✅ Listar todos os réus de um processo
    public Set<Reu> listarReusDeProcesso(String numeroProcesso) {
        Processo processo = processoRepository.findByNumeroProcesso(numeroProcesso)
                .orElseThrow(() -> new RuntimeException("Processo não encontrado"));

        return processo.getReus();
    }

    // ✅ Listar todos os processos de um réu pelo CPF
    public Set<Processo> listarProcessosDeReu(String cpf) {
        Reu reu = reuRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Réu não encontrado"));

        return reu.getProcessos();
    }

    // Salvar um processo atualizado
    public Processo salvar(Processo processo) {
        return processoRepository.save(processo);
    }

}
