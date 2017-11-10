package ch.heigvd.wordoff.common.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface IDto {
    @JsonIgnore
    boolean isWellformed();
}
