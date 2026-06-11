# language: pt
@web @sortable
Funcionalidade: Parte 2 - Sortable
  Como usuário do site DemoQA
  Quero ordenar os elementos da lista com drag and drop
  Para que fiquem em ordem crescente

  Cenário: Ordenar os elementos da lista em ordem crescente
    Dado que eu acesso o site da DemoQA
    Quando escolho a opção "Interactions" na página inicial
    E clico no submenu "Sortable"
    E embaralho os elementos da lista caso já estejam ordenados
    E ordeno os elementos da lista em ordem crescente utilizando drag and drop
    Então os elementos da lista devem estar em ordem crescente
