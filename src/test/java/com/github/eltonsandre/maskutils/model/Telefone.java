package com.github.eltonsandre.maskutils.model;

import com.github.eltonsandre.maskutils.annotations.MaskField;
import com.github.eltonsandre.maskutils.annotations.MaskGroups;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author eltonsandre
 * date 03/03/2019 19:17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Telefone {

    @MaskField
    private String ddi;

    @MaskField
    private String ddd;

    @MaskGroups({
            @MaskField(remove = true),
            @MaskField(regex = "(^[^@]{3}).*", replacement = "$1******@******", namesGroup = { "test" })
    })
    private String numero;

}
