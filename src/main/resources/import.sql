insert into cozinha (id, nome) values(1, 'Tailandesa');
insert into cozinha (id, nome) values(2, 'Indiana');

insert into forma_pagamento (descricao) values('À vista');
insert into forma_pagamento (descricao) values('Cartão de crédito');
insert into forma_pagamento (descricao) values('Cartão de débito');
insert into forma_pagamento (descricao) values('Pix');

insert into estado (id, nome) values(1, 'Acre');
insert into estado (id, nome) values(2, 'Alagoas');
insert into estado (id, nome) values(3, 'Amazonas');
insert into estado (id, nome) values(4, 'Amapá');
insert into estado (id, nome) values(5, 'Bahia');
insert into estado (id, nome) values(6, 'Ceará');
insert into estado (id, nome) values(7, 'Distrito Federal');
insert into estado (id, nome) values(8, 'Espírito Santo');
insert into estado (id, nome) values(9, 'Goiás');
insert into estado (id, nome) values(10, 'Maranhão');
insert into estado (id, nome) values(11, 'Minas Gerais');
insert into estado (id, nome) values(12, 'Mato Grosso do Sul');
insert into estado (id, nome) values(13, 'Mato Grosso');
insert into estado (id, nome) values(14, 'Pará');
insert into estado (id, nome) values(15, 'Paraíba');
insert into estado (id, nome) values(16, 'Pernambuco');
insert into estado (id, nome) values(17, 'Piauí');
insert into estado (id, nome) values(18, 'Paraná');
insert into estado (id, nome) values(19, 'Rio de Janeiro');
insert into estado (id, nome) values(20, 'Rio Grande do Norte');
insert into estado (id, nome) values(21, 'Rondônia');
insert into estado (id, nome) values(22, 'Roraima');
insert into estado (id, nome) values(23, 'Rio Grande do Sul');
insert into estado (id, nome) values(24, 'Santa Catarina');
insert into estado (id, nome) values(25, 'Sergipe');
insert into estado (id, nome) values(26, 'São Paulo');
insert into estado (id, nome) values(27, 'Tocantins');

insert into cidade (nome, estado_id) values('Belo Horizonte', 11);
insert into cidade (nome, estado_id) values('Uberlândia', 11);
insert into cidade (nome, estado_id) values('Uberaba', 11);
insert into cidade (nome, estado_id) values('São Paulo', 26);
insert into cidade (nome, estado_id) values('Campinas', 26);
insert into cidade (nome, estado_id) values('Rio de Janeiro', 19);
insert into cidade (nome, estado_id) values('Angra dos Reis', 19);
insert into cidade (nome, estado_id) values('Goiânia', 9);
insert into cidade (nome, estado_id) values('Caldas Novas', 9);


insert into permissao (nome, descricao) values('LEITURA_COZINHA', 'Pesquisar cozinhas');
insert into permissao (nome, descricao) values('ESCRITA_COZINHA', 'Cadastrar cozinhas');
insert into permissao (nome, descricao) values('LEITURA_RESTAURANTE', 'Pesquisar restaurantes');
insert into permissao (nome, descricao) values('ESCRITA_RESTAURANTE', 'Cadastrar restaurantes');

insert into restaurante (nome, taxa_frete, cozinha_id) values('Muay Thai Restaurante', 7.50, 1);
insert into restaurante (nome, taxa_frete, cozinha_id) values('Restaurante Dragão Branco', 12.00, 1);
insert into restaurante (nome, taxa_frete, cozinha_id) values('Arebaba Hamburgueria', 5.75, 2);
