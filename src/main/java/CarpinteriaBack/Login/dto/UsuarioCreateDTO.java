package CarpinteriaBack.Login.dto;

import lombok.Data;

@Data
public class UsuarioCreateDTO {
    private String username;
    private String password;
    private Integer idEmpleado;
}