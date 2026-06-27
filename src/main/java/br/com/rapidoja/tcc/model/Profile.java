package br.com.rapidoja.tcc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Profile {

    CUSTOMER("Cliente"),
    ADMIN("Administrador"),
    DELIVERY_MAN("Entregador");

    private final String profileName;
}
