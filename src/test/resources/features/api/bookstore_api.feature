# language: pt
@api
Funcionalidade: Parte 1 - BookStore API
  Como usuário da BookStore da DemoQA
  Quero criar minha conta, me autenticar e alugar livros
  Para que os livros alugados constem nos detalhes do meu usuário

  Cenário: Criar usuário, autorizá-lo e alugar dois livros
    Dado que eu crio um novo usuário com dados válidos
    E gero um token de acesso para o usuário
    Então o usuário criado deve estar autorizado
    Quando eu listo os livros disponíveis na BookStore
    E alugo 2 livros de livre escolha para o usuário
    Então os detalhes do usuário devem conter os livros alugados
