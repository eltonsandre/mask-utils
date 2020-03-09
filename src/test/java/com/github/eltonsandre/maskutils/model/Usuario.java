package com.github.eltonsandre.maskutils.model;

import com.github.eltonsandre.maskutils.annotations.MaskCollectonData;
import com.github.eltonsandre.maskutils.annotations.MaskField;
import com.github.eltonsandre.maskutils.annotations.MaskGroups;
import com.github.eltonsandre.maskutils.annotations.MaskObjectData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author eltonsandre
 * date 03/03/2019 19:16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Builder.Default
    private String notMask = "notMask";

    @MaskField
    private String username;

    @MaskField(regex = "^.*", replacement = "[SECRETO]")
    private String senha;

    @MaskField(regex = "(^[^@]{3}).*", replacement = "$1.***.***-**", namesGroup = {"test"})
    private String cpf;

    @MaskGroups({
            @MaskField(regex = "(^[^@]{4}).*@[^.]*(\\..*)", replacement = "$1******@****$2"),
            @MaskField(regex = "(^[^@]{4}).*@[^.]*(\\..*)", replacement = "$1******@******", namesGroup = {"test"})
    })
    private String email;

    @MaskField(regex = "(^[^@]{4}).*", replacement = "**/**/$1", namesGroup = {"test"})
    private String dataNascimento;

    @MaskCollectonData
    private List<Telefone> telefones;

    @MaskObjectData
    private Endereco endereco;

}
