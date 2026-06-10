# language: pt
@web @web_tables
Funcionalidade: Parte 2 - Web Tables
  Como usuário do site DemoQA
  Quero gerenciar registros na tabela
  Para validar as operações de criação, edição e exclusão

  Cenário: Criar, editar e deletar um registro
    Dado que eu acesso o site da DemoQA
    Quando escolho a opção "Elements" na página inicial
    E clico no submenu "Web Tables"
    E crio um novo registro na tabela
    Então o registro criado deve ser exibido na tabela
    Quando edito o registro criado
    Então o registro editado deve ser exibido na tabela
    Quando deleto o registro criado
    Então o registro não deve mais ser exibido na tabela

  @bonus
  Cenário: Criar 12 registros de forma dinâmica e deletar todos
    Dado que eu acesso o site da DemoQA
    Quando escolho a opção "Elements" na página inicial
    E clico no submenu "Web Tables"
    E crio 12 novos registros de forma dinâmica
    Então a tabela deve conter os 12 registros criados
    Quando deleto todos os registros criados
    Então nenhum dos registros criados deve ser exibido na tabela
