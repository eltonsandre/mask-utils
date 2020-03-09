package com.github.eltonsandre.maskutils.model;

import com.github.eltonsandre.maskutils.annotations.MaskCollectonData;
import com.github.eltonsandre.maskutils.annotations.MaskField;
import com.github.eltonsandre.maskutils.annotations.MaskGroups;
import com.github.eltonsandre.maskutils.annotations.MaskObjectData;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author eltonsandre
 * date 03/03/2019 19:16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    private String notMask = "notMask";

    @MaskField
    private String username;

    @MaskField(regex = "(^[^@]{3}).*", replacement = "$1.***.***-**", namesGroup = { "test" })
    private String cpf;

    @MaskGroups({
            @MaskField(regex = "(^[^@]{4}).*@[^.]*(\\..*)", replacement = "$1******@****$2"),
            @MaskField(regex = "(^[^@]{4}).*@[^.]*(\\..*)", replacement = "$1******@******", namesGroup = { "test" })
    })
    private String email;

    @MaskField(regex = "(^[^@]{4}).*", replacement = "**/**/$1", namesGroup = { "test" })
    private String dataNascimento;

    @MaskCollectonData
    private List<Telefone> telefones;

    @MaskObjectData
    private Endereco endereco;

}
