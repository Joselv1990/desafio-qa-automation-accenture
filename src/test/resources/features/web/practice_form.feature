# language: pt
@web @practice_form
Funcionalidade: Parte 2 - Practice Form
  Como usuário do site DemoQA
  Quero preencher e submeter o formulário de prática
  Para validar o cadastro com dados aleatórios e upload de arquivo

  Cenário: Preencher e submeter o formulário com valores aleatórios
    Dado que eu acesso o site da DemoQA
    Quando escolho a opção "Forms" na página inicial
    E clico no submenu "Practice Form"
    E preencho todo o formulário com valores aleatórios
    E anexo um arquivo .txt no formulário
    E submeto o formulário
    Então um popup de confirmação deve ser exibido
    E eu fecho o popup de confirmação
