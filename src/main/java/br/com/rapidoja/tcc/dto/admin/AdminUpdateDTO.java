package br.com.rapidoja.tcc.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUpdateDTO {

    private String email;

    private String password;

    private String phoneNumber;
}
