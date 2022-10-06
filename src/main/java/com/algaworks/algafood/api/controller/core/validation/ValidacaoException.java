package com.algaworks.algafood.api.controller.core.validation;

import lombok.RequiredArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;

@RequiredArgsConstructor
@Getter
public class ValidacaoException extends RuntimeException {



    private BindingResult bindingResult;

}
