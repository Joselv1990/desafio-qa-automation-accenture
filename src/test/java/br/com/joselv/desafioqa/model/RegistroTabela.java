package br.com.joselv.desafioqa.model;

import net.datafaker.Faker;

public record RegistroTabela(
        String primeiroNome,
        String sobrenome,
        String email,
        String idade,
        String salario,
        String departamento) {

    private static final Faker FAKER = new Faker();

    public static RegistroTabela aleatorio() {
        return new RegistroTabela(
                FAKER.name().firstName(),
                FAKER.name().lastName(),
                emailUnico(),
                String.valueOf(FAKER.number().numberBetween(18, 65)),
                String.valueOf(FAKER.number().numberBetween(2000, 20000)),
                FAKER.job().field());
    }

    public RegistroTabela comNovosDados() {
        return new RegistroTabela(
                FAKER.name().firstName(),
                FAKER.name().lastName(),
                email,
                String.valueOf(FAKER.number().numberBetween(18, 65)),
                String.valueOf(FAKER.number().numberBetween(2000, 20000)),
                FAKER.job().field());
    }

    private static String emailUnico() {
        return String.format("%s.%05d@teste.com",
                FAKER.name().firstName().toLowerCase().replaceAll("[^a-z]", ""),
                FAKER.number().numberBetween(0, 100000));
    }
}
