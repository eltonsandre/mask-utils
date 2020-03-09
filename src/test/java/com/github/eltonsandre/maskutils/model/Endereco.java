package com.github.eltonsandre.maskutils.model;

import com.github.eltonsandre.maskutils.annotations.MaskField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author eltonsandre
 * date 03/03/2019 19:19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    @MaskField(regex = "\\w")
    private String logradouro;

    @MaskField
    private String numero;

    @MaskField
    private String bairro;

    @MaskField(remove = true)
    private String cidade;

}
