# README

## Sobre a biblioteca

Esta é uma biblioteca de manipulação de JSON em Kotlin. Ela permite que os usuários trabalhem com JSON de uma forma que se alinha com os conceitos fundamentais da linguagem Kotlin. A biblioteca suporta a leitura, escrita e manipulação de JSON utilizando uma variedade de classes, interfaces e anotações.

## Instalação

Para instalar a biblioteca usando Maven, adicione a seguinte dependência ao seu arquivo **`pom.xml`**:

```
<dependencies>
    <dependency>
        <groupId>RonaldoProject</groupId>
        <artifactId>final-project-library</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```
## Para instalar localmente o projeto

Para instalar localmente o projeto, você precisa rodar :

***```mvn clean install```***

## Executando os testes

Para rodar os testes, você pode usar a ferramenta de linha de comando do Maven. No terminal, navegue até o diretório raiz do seu projeto e execute o seguinte comando:

 ***```mvn test```***

O Maven compilará seu código, executará os testes e fornecerá um resumo dos resultados.

## Anotações

#### A biblioteca fornece as seguintes anotações:

**JsonExclude**: Use esta anotação em qualquer propriedade de uma data class que você não deseja incluir na saída JSON.

**JsonName**: Use esta anotação para especificar um nome personalizado para a propriedade na saída JSON. Por exemplo, se você tem uma propriedade chamada firstName, mas deseja que ela apareça como first_name na saída JSON, você pode usar a anotação da seguinte forma: @JsonName("first_name").

**JsonToString**: Use esta anotação em uma propriedade numérica se você deseja que a saída JSON represente o número como uma string.

## Transformando um objeto em JSON

Para transformar um objeto em JSON, você pode usar a classe JsonBuilder fornecida pela biblioteca. Aqui está um exemplo de uso:

```
val builder = JsonBuilder()
val json = builder.buildJson(myObject)
println(json.toJsonString())
Neste exemplo, myObject é o objeto que você deseja converter em JSON. A chamada builder.buildJson(myObject) retorna um objeto JsonValue que representa a estrutura de dados JSON. Você pode então usar o método toJsonString() para converter este objeto JsonValue em uma string JSON.
```