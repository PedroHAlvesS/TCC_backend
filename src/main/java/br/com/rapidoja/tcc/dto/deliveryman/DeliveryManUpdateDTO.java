package br.com.rapidoja.tcc.dto.deliveryman;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryManUpdateDTO {

    private String email;

    private String password;

    private String phoneNumber;
}
