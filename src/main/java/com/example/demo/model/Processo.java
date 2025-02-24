package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "processos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Processo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String numeroProcesso;
    
    @Column(nullable = false)
    private String descricao;
    
    @Column(nullable = false)
    private String status;
    
    @Column(nullable = false)
    private LocalDate dataAbertura;

    // Relação Muitos-para-Muitos entre Processos e Réus
    @ManyToMany
    @JoinTable(
        name = "processo_reu",  // Nome da tabela intermediária
        joinColumns = @JoinColumn(name = "processo_id"),
        inverseJoinColumns = @JoinColumn(name = "reu_id")
    )
    private Set<Reu> reus = new HashSet<>();
}
