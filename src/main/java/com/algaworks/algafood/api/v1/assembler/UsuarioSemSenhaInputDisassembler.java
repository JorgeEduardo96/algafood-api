package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.model.input.UsuarioSemSenhaInput;
import com.algaworks.algafood.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioSemSenhaInputDisassembler {

    private final ModelMapper modelMapper;

    public Usuario toDomainObject(UsuarioSemSenhaInput usuarioSemSenhaInput) {
        return modelMapper.map(usuarioSemSenhaInput, Usuario.class);
    }

    public void copyToDomainObject(UsuarioSemSenhaInput usuarioSemSenhaInput, Usuario usuario) {
        modelMapper.map(usuarioSemSenhaInput, usuario);
    }

}
