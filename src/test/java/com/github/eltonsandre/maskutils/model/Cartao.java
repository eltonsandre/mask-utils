package com.github.eltonsandre.maskutils.model;

import com.github.eltonsandre.maskutils.annotations.MaskField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cartao {

    private String nome;

    @MaskField(regex = "^([0-9]{4})(.*)([0-9]{4})", replacement = "$1 **** **** $3", namesGroup = {"dado.pci"})
    private String numero;

    @MaskField(namesGroup = {"dado.pci"})
    private String cvv;

    @MaskField(namesGroup = {"dado.pci"})
    private String anoValidade;

    @MaskField(namesGroup = {"dado.pci"})
    private String mesValidade;
}