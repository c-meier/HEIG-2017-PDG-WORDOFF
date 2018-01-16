/*
 * File: IDto.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.common.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface IDto {
    @JsonIgnore
    boolean isWellformed();
}
