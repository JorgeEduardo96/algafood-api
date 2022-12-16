package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.EstadoModel;
import com.algaworks.algafood.domain.model.Estado;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EstadoModelAssembler {

    private final ModelMapper modelMapper;

    public EstadoModel toModel(Estado estado) {
        return modelMapper.map(estado, EstadoModel.class);
    }

    public List<EstadoModel> toCollecionModel(List<Estado> estados) {
        return estados.stream().map(this::toModel).collect(Collectors.toList());
    }

}
