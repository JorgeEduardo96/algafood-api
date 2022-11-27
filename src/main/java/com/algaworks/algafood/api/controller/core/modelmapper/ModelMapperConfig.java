package com.algaworks.algafood.api.controller.core.modelmapper;

import com.algaworks.algafood.api.model.EnderecoModel;
import com.algaworks.algafood.api.model.input.ItemPedidoInput;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.ItemPedido;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        // ignorar o mapemaneto do setId -> mapper.skip
        modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
                .addMappings(mapper -> mapper.skip(ItemPedido::setId));

//		modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
//			.addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);

        var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(
                Endereco.class, EnderecoModel.class);

        enderecoToEnderecoModelTypeMap.<String>addMapping(
                src -> src.getCidade().getEstado().getNome(),
                (dest, value) -> dest.getCidade().setEstado(value));

        return modelMapper;
    }


}
