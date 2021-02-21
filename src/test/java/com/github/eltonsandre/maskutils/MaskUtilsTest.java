package com.github.eltonsandre.maskutils;

import com.github.eltonsandre.maskutils.model.Cartao;
import com.github.eltonsandre.maskutils.model.Endereco;
import com.github.eltonsandre.maskutils.model.Telefone;
import com.github.eltonsandre.maskutils.model.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author eltonsandre
 * date 09/03/2019 15:05
 */

class MaskUtilsTest {

    @Test
    @DisplayName("Mascarando para qualquer grupo")
    void test_MaskGroups_AnyGroup() {
        final Usuario mask = this.userBuilderMock();
        MaskUtils.mask(mask);

        assertEquals("notMask", mask.getNotMask());
        assertEquals("********", mask.getUsername());
        assertEquals("321.321.321-45", mask.getCpf());
        assertEquals("test******@****.com", mask.getEmail());

        System.out.println(mask);
    }

    @Test
    @DisplayName("Mascarando para o grupo 'test' ")
    void test_MaskGroups_TestGroup() {
        final Usuario mask = (Usuario) MaskUtils.mask(this.userBuilderMock(), "test");

        assertEquals("notMask", mask.getNotMask());
        assertEquals("321.***.***-**", mask.getCpf());
        assertEquals("test******@******", mask.getEmail());
        assertEquals("**/**/1985", mask.getDataNascimento());

        System.out.println(mask);
    }

    @Test
    @DisplayName("Mascarando objetos agregados")
    void test_MaskObjectData_AnyGroup() {
        final Usuario mask = this.userBuilderMock();
        MaskUtils.mask(mask);

        assertAll(
                () -> assertEquals("notMask", mask.getNotMask()),
                () -> assertEquals("[SECRETO]", mask.getSenha()),
                () -> assertEquals("*. *** ****", mask.getEndereco().getLogradouro()),
                () -> assertEquals("***", mask.getEndereco().getNumero()),
                () -> assertEquals("test******@****.com", mask.getEmail())
        );

        System.out.println(mask);
    }

    @Test
    @DisplayName("Mascarando objetos agregados para o grupo 'test' ")
    void test_MaskObjectData_TestGroup() {
        final Usuario mask = (Usuario) MaskUtils.mask(this.userBuilderMock(), "test");
        assertEquals("notMask", mask.getNotMask());

        System.out.println(mask);
    }

    @Test
    @DisplayName("Mascarando dados do cartão para o grupo 'dado.pci' ")
    void test_MaskCard_PCI_TestGroup() {
        final Cartao mask = (Cartao) MaskUtils.mask(this.cardBuilderMock(), "dado.pci");
        assertEquals("5498 **** **** 0032", mask.getNumero());

        System.out.println(mask);
    }

    public final Cartao cardBuilderMock() {
        return Cartao.builder()
                .nome("John Connor")
                .numero("5498 0305 8674 0032")
                .anoValidade("2030")
                .cvv("123")
                .mesValidade("12")
                .build();
    }

    public final Usuario userBuilderMock() {
        return Usuario.builder()
                .notMask("notMask")
                .senha("As233asd$#")
                .email("test.mask@mock.com")
                .username("usertest")
                .cpf("321.321.321-45")
                .dataNascimento("1985-08-28")
                .endereco(Endereco.builder()
                        .logradouro("R. rua A123")
                        .numero("431")
                        .cidade("São Paulo").build())
                .telefones(Arrays.asList(
                        Telefone.builder()
                                .ddi("55")
                                .ddd("11")
                                .numero("99876-5432").build())
                ).build();
    }

}
