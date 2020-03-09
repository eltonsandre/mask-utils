package com.github.eltonsandre.maskutils;

import com.github.eltonsandre.maskutils.model.Endereco;
import com.github.eltonsandre.maskutils.model.Telefone;
import com.github.eltonsandre.maskutils.model.Usuario;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author eltonsandre
 * date 09/03/2019 15:05
 */

public class MaskUtilsTest {

    @Test
    public void test_MaskGroups_AnyGroup() {
        final Usuario mask = this.userBuilderMock();
        MaskUtils.mask(mask);

        assertEquals("notMask", mask.getNotMask());
        assertEquals("********", mask.getUsername());
        assertEquals("321.321.321-45", mask.getCpf());
        assertEquals("test******@****.com", mask.getEmail());

        System.out.println(mask);
    }

    @Test
    void test_MaskGroups_TestGroup() {
        final Usuario mask = (Usuario) MaskUtils.mask(this.userBuilderMock(), "test");

        assertEquals("notMask", mask.getNotMask());
        assertEquals("321.***.***-**", mask.getCpf());
        assertEquals("test******@******", mask.getEmail());
        assertEquals("**/**/1985", mask.getDataNascimento());

        System.out.println(mask);
    }

    @Test
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
    void test_MaskObjectData_TestGroup() {
        final Usuario mask = (Usuario) MaskUtils.mask(this.userBuilderMock(), "test");
        assertEquals("notMask", mask.getNotMask());

        System.out.println(mask);
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
