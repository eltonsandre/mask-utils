package com.github.eltonsandre.maskutils;

import com.github.eltonsandre.maskutils.model.Endereco;
import com.github.eltonsandre.maskutils.model.Telefone;
import com.github.eltonsandre.maskutils.model.Usuario;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author eltonsandre
 * date 09/03/2019 15:05
 */

public class MaskUtilsTest {

    @Test
    public void test_MaskGroups_AnyGroup() {
        final Usuario mask = this.userBuilderMock();
        MaskUtils.mask(mask);

        Assert.assertEquals("notMask", mask.getNotMask());
        Assert.assertEquals("********", mask.getUsername());
        Assert.assertEquals("321.321.321-45", mask.getCpf());
        Assert.assertEquals("test******@****.com", mask.getEmail());

        System.out.println(mask);
    }

    @Test
    public void test_MaskGroups_TestGroup() {
        final Usuario mask = (Usuario) MaskUtils.mask(this.userBuilderMock(), "test");
        Assert.assertEquals("notMask", mask.getNotMask());
        Assert.assertEquals("321.***.***-**", mask.getCpf());
        Assert.assertEquals("test******@******", mask.getEmail());
        Assert.assertEquals("**/**/1985", mask.getDataNascimento());

        System.out.println(mask);
    }

    @Test
    public void test_MaskObjectData_AnyGroup() {
        final Usuario mask = this.userBuilderMock();
        MaskUtils.mask(mask);
        Assert.assertEquals("notMask", mask.getNotMask());
        Assert.assertEquals("*. *** ****", mask.getEndereco().getLogradouro());
        Assert.assertEquals("***", mask.getEndereco().getNumero());
        Assert.assertEquals("test******@****.com", mask.getEmail());

        System.out.println(mask);
    }

    @Test
    public void test_MaskObjectData_TestGroup() {
        final Usuario mask = (Usuario) MaskUtils.mask(this.userBuilderMock(), "test");
        Assert.assertEquals("notMask", mask.getNotMask());
        //        Assert.assertEquals("321.***.***-**", mask.getEndereco().getLogradouro());
        //        Assert.assertEquals("321.***.***-**", mask.getEndereco().getNumero());
        //        Assert.assertEquals("test******@******", mask.getEmail());

        System.out.println(mask);
    }

    public final Usuario userBuilderMock() {
        return Usuario.builder()
                .notMask("notMask")
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
