/*
 * File: ErrorDto.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.common.Dto;

import java.util.Objects;

public class ErrorDto {
    private int errorCode;
    private String msg;

    // Necessary for Jackson deserialization
    protected ErrorDto() {}

    public ErrorDto(int errorCode, String msg) {
        this.errorCode = errorCode;
        this.msg = msg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ErrorDto)) {
            return false;
        }
        ErrorDto c = (ErrorDto) o;
        return Objects.equals(errorCode, c.errorCode) &&
                Objects.equals(msg, c.msg);
    }
}
