package com.algaworks.algafood.domain.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Builder
public class Grupo {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @ManyToMany
    @JoinTable(name = "grupo_permissao",
            joinColumns = @JoinColumn(name = "grupo_id"),
            inverseJoinColumns = @JoinColumn(name = "permissao_id"))
    private Set<Permissao> permissoes = new HashSet<>();

    public boolean incluirPermissao(Permissao permissao) {
        return getPermissoes().add(permissao);
    }

    public boolean excluirPermissao(Permissao permissao) {
        return getPermissoes().remove(permissao);
    }

}
