# language: pt
@web @progress_bar
Funcionalidade: Parte 2 - Progress Bar
  Como usuário do site DemoQA
  Quero controlar a progress bar
  Para validar os valores exibidos em cada estado

  Cenário: Parar a progress bar antes de 25% e resetar após atingir 100%
    Dado que eu acesso o site da DemoQA
    Quando escolho a opção "Widgets" na página inicial
    E clico no submenu "Progress Bar"
    E clico no botão Start da progress bar
    E paro a progress bar antes de atingir 25%
    Então o valor da progress bar deve ser menor ou igual a 25%
    Quando clico no botão Start novamente e aguardo a progress bar atingir 100%
    E reseto a progress bar
    Então o valor da progress bar deve ser 0%
