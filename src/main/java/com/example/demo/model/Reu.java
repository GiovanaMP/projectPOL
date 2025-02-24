package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "reus")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String cpf;

    private String endereco;

    // 1️⃣ Relação Muitos-para-Muitos entre Réus e Processos
    @ManyToMany(mappedBy = "reus")
    private Set<Processo> processos = new HashSet<>();
}
