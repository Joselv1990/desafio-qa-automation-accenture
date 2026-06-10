# language: pt
@web @browser_windows
Funcionalidade: Parte 2 - Browser Windows
  Como usuário do site DemoQA
  Quero abrir uma nova janela do navegador
  Para validar o conteúdo exibido nela

  Cenário: Abrir nova janela e validar a mensagem exibida
    Dado que eu acesso o site da DemoQA
    Quando escolho a opção "Alerts, Frame & Windows" na página inicial
    E clico no submenu "Browser Windows"
    E clico no botão New Window
    Então uma nova janela deve ser aberta com a mensagem "This is a sample page"
    E eu fecho a nova janela aberta
