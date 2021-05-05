package com.softwarefoundation.ministore.auth.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UsuarioDto implements Serializable {

    private String username;
    private String password;

}
