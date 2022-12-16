package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.input.CozinhaInput;
import com.algaworks.algafood.domain.model.Cozinha;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CozinhaInputDisassembler {

    private final ModelMapper modelMapper;

    public Cozinha toDomainObject(CozinhaInput cozinhaInput) { return modelMapper.map(cozinhaInput, Cozinha.class); }

    public void copyToDomainProperties(CozinhaInput cozinhaInput, Cozinha cozinha) {
        modelMapper.map(cozinhaInput, cozinha);
    }

}
