package br.com.rapidoja.tcc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderStatus {
    PENDING("Pendente"),
    ASSIGNED("Atribuído"),
    IN_TRANSIT("Em Trânsito"),
    DELIVERED("Entregue"),
    CANCELED("Cancelado");

    private final String statusName;
}
