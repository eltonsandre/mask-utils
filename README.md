# mask-utils

Utils para gerar ofuscação e mascaramento em dados e campos de objetos.

A ideia do projeto é mascarar e/ou ofuscar dados sensiveis, como credenciais,
dados [PCI DSS](https://en.wikipedia.org/wiki/Payment_Card_Industry_Data_Security_Standard)
, [PII](https://pt.wikipedia.org/wiki/Informa%C3%A7%C3%A3o_pessoalmente_identific%C3%A1vel) e outros que possam fazer sentido.

A ofuscação acontece via anotação do campo e baseada em replace utilizando regex.
**_Funciona somente para tipos String._**

### Gradle

```groovy
//build.gradle
dependencies {
    implementation 'com.github.eltonsandre.utils:mask-utils:1.0.1'
}
```

### Maven

```xml
//pom.xml
<dependency>
    <groupId>com.github.eltonsandre.utils</groupId>
    <artifactId>mask-utils</artifactId>
    <version>1.0.1</version>
</dependency>
```

---

### **Anotações**

#### @MaskField: _Mascara o valor do campo de tipo String._

- **remove:** _Remove o valor do campo._

- **regex:** _Expreção regular em que o campo será mascarado_.

- **replacement:** _valor ou regex em que o campo será mascarado._

- **namesGroup:** _nome do grupo para qual grupo o campo será mascarado._

#### @MaskGroups - Anotação para marcar atributo com grupos de ofuscação.

#### @MaskObjectData - Anotação para marcar atributo como tipo complexo acessar atributos e ofuscar.

#### @MaskCollectonData - Anotação para marcar atributo como coleção para interar e ofuscar os campos e objetos anotados.

---

### Exemplos de uso:

o exempo mostra mascarameto de um dado [PII](https://pt.wikipedia.org/wiki/Informa%C3%A7%C3%A3o_pessoalmente_identific%C3%A1vel) do campo para o
grupo **'dado.pii'**:

```java
public class Cliente {
 ...
    @MaskField(regex = "(^[^@]{3}).*", replacement = "$1.***.***-**", namesGroup = {"dado.pii"})
    private String cpf;
 ...
}

```

```java
public class Cliente {

    public Cliente recuperaDadosCliente(final String id) {
        //codigo ...
        final Cliente clienteOfuscado = (Cliente) MaskUtils.mask(cliente, "dado.pii");
        //codigo ...
        return clienteOfuscado;
    }
}
```

#

---
Mascaramento por grupo:
no exemplo poderá ser mascarado pelo default, isto é sem grupo ou passando o grupo, no caso dado.pii

```java
public class Cliente {
...
    @MaskGroups({
            @MaskField(regex = "(^[^@]{4}).*@[^.]*(\..*)", replacement = "$1******@****$2"),
            @MaskField(regex = "(^[^@]{4}).*@[^.]*(\..*)", replacement = "$1******@******",
                    namesGroup = {"dado.pci"})
    })
    private String email;
...
}


class ExemploParaGrupo {

    public Cliente mascarar(final Cliente cliente, final boolean semGrupo) {
        cliente.setEmail("test.mask@mock.com");
        if (semGrupo) {
            /*default sem  grupo*/
            return MaskUtils.mask(cliente);
            // resultado: email=test******@****.com
        }
        /*Passando grupo*/
        return MaskUtils.mask(cliente, "dado.pii");
        // resultado: email=test******@******
    }
}
```

#

---
Ofuscando dados de cartões (PCI DSS)

```java

@Data
public class CartaoCredito {

    private String nome;

    @MaskField(regex = "^([0-9]{4})(.*)([0-9]{4})", replacement = "$1 **** **** $3",
            namesGroup = {"dado.pci"})
    private String numero;

    @MaskField(namesGroup = {"dado.pci"})
    private String cvv;

    @MaskField(namesGroup = {"dado.pci"})
    private String anoValidade;

    @MaskField(namesGroup = {"dado.pci"})
    private String mesValidade;
}
```

```java

public class ExemploCard {

    public Cartao mask() {
        Cartao cartao = Cartao.builder()
                .nome("John Connor")
                .cvv("123")
                .numero("5498 0305 8674 0032")
                .anoValidade("2030")
                .mesValidade("12")
                .build();

        return MaskUtils.mask(cartao, "dado.pci");
    }

```

```
Resultado:
 Cartao(
    nome=John Connor,
    numero=5498 **** **** 0032,
    cvv=***,
    anoValidade=****,
    mesValidade=**
 )
```

#

---
Ofuscação de Credencias (senha)

```java

@Data
public class Usuario {

    @MaskField
    private String username;

    @MaskGroups({
            @MaskField(regex = "(^[^@]{4}).*@[^.]*(\..*)", replacement = "$1******@****$2"),
            @MaskField(regex = "(^[^@]{4}).*@[^.]*(\..*)", replacement = "$1******@******",
                    namesGroup = {"dado.pii"})
    })
    private String email;


    @MaskField(regex = "^.*", replacement = "[SECRETO]", namesGroup = {"dado.sensivel"})
    private String password;

}

```

#

---
Exemplo geral

```java
public class Cliente {

    private String nome;

    @MaskField(regex = "(^[^@]{3}).*", replacement = "$1.***.***-**", namesGroup = {"dado.pii"})
    private String cpf;

    @MaskGroups({
            @MaskField(regex = "(^[^@]{4}).*@[^.]*(\..*)", replacement = "$1******@****$2"),
            @MaskField(regex = "(^[^@]{4}).*@[^.]*(\..*)", replacement = "$1******@******",
                    namesGroup = {"dado.pci"})
    })
    private String email;

    @MaskField(regex = "(^[^@]{4}).*", replacement = "**/**/$1", namesGroup = {"dado.pii"})
    private String dataNascimento;

    @MaskCollectonData
    private List<Telefone> telefones;

    @MaskObjectData
    private Endereco endereco;

}

```

```java

@Data
public class Telefone {

    @MaskField
    private String ddi;

    @MaskField
    private String ddd;

    @MaskGroups({
            @MaskField(remove = true),
            @MaskField(regex = "(^[^@]{3}).*", replacement = "$1******@******",
                    namesGroup = {"dado.pii"})
    })
    private String numero;

}
```

---

## Pull requests, novas ideias de features, colaboração e/ou issues serão muito bem-vindas.

---