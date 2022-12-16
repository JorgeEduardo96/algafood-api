package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.api.controller.core.validation.Groups;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Estado {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull(groups = Groups.EstadoId.class)
	private Long id;

	@Column(nullable = false)
	private String nome;
	
}
