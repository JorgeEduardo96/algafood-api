package com.algaworks.algafood.api.controller.core.validation.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;

@RequiredArgsConstructor
@Getter
public class ValidacaoException extends RuntimeException {

    private BindingResult bindingResult;

}
