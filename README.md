# Desafio QA Automation Engineer

Automação de testes de API e Frontend sobre o site [demoqa.com](https://demoqa.com), desenvolvida em **Java + Selenium WebDriver + Cucumber (BDD) + RestAssured**.

## O desafio

**Parte 1 – API (BookStore)** — fluxo executado de forma contínua em uma única execução:

1. Criar um usuário
2. Gerar um token de acesso
3. Confirmar que o usuário criado está autorizado
4. Listar os livros disponíveis
5. Alugar dois livros de livre escolha
6. Listar os detalhes do usuário com os livros alugados

**Parte 2 – Frontend** — cinco cenários de automação web:

| Cenário | Página | Descrição |
|---|---|---|
| Practice Form | Forms | Preenche o formulário com valores aleatórios, faz upload de um arquivo .txt versionado no repositório, valida e fecha o popup de confirmação |
| Browser Windows | Alerts, Frame & Windows | Abre uma nova janela, valida a mensagem "This is a sample page" e a fecha |
| Web Tables | Elements | Cria, edita e deleta um registro. **Bônus:** cria 12 registros de forma dinâmica e deleta todos |
| Progress Bar | Widgets | Para a barra antes de 25%, valida o valor, aguarda atingir 100% e reseta |
| Sortable | Interactions | Ordena os elementos da lista em ordem crescente com drag and drop |

## Stack

- Java 17
- Selenium WebDriver 4 (Selenium Manager resolve o driver automaticamente)
- Cucumber 7 (BDD, features escritas em português)
- RestAssured (testes de API)
- JUnit 5
- Datafaker (geração de dados aleatórios)
- Maven

## Estrutura do projeto

```
src/test
├── java/br/com/joselv/desafioqa
│   ├── api/        # clients RestAssured (Account e BookStore)
│   ├── model/      # modelos de dados de teste
│   ├── pages/      # Page Objects (POM)
│   ├── runners/    # runner do Cucumber (JUnit 5)
│   ├── steps/      # step definitions (api e web)
│   └── support/    # DriverFactory e hooks
└── resources
    ├── features/   # cenários BDD (api e web)
    └── upload/     # arquivo .txt usado no upload do Practice Form
```

## Pré-requisitos

- Java 17+
- Maven 3.8+
- Google Chrome instalado

## Como executar

```bash
# suíte completa (API + Web)
mvn test

# apenas os testes de API (Parte 1)
mvn test -Dcucumber.filter.tags="@api"

# apenas os testes web (Parte 2)
mvn test -Dcucumber.filter.tags="@web"

# um cenário específico
mvn test -Dcucumber.filter.tags="@practice_form"   # @browser_windows, @web_tables, @progress_bar, @sortable

# em modo headless (sem abrir o navegador)
mvn test -Dheadless=true
```

## Relatórios

- Relatório HTML do Cucumber: `target/cucumber-report/cucumber.html`
- Screenshots de cenários com falha: anexados ao relatório e salvos em `target/screenshots/`

## Observações técnicas

Alguns comportamentos do demoqa.com exigiram tratamento específico:

- **Anúncios sobrepondo elementos**: são removidos via JavaScript ao navegar, e os cliques possuem fallback via JS quando interceptados.
- **Botão Close do popup do Practice Form**: na versão atual do site o botão não responde ao clique (nem nativo, nem via JS). O fechamento usa ESC como alternativa quando o clique não surte efeito.
- **Web Tables**: registros criados na mesma sessão compartilham o id interno no site e a exclusão remove por id — deletar um registro pode remover os demais. A exclusão em massa trata esse comportamento validando a presença de cada registro antes de deletá-lo.

## CI

O projeto possui workflow de GitHub Actions que executa a suíte de API a cada push e permite disparo manual da suíte web em modo headless.

## Autor

**Jose Luis Vieira**
[github.com/joselv1990](https://github.com/joselv1990) · [linkedin.com/in/jose-luis-vieira](https://linkedin.com/in/jose-luis-vieira)
