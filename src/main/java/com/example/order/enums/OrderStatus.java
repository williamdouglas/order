package com.example.order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {

    AGUARDANDO_ENVIO(1, "Aguardando envio"),
    ENVIADO_TRANSPORTADORA(2, "Enviado à transportadora");

    private final int statusId;
    private final String description;

    public static OrderStatus getEnumByStatusId(int statusId) {
        for (OrderStatus e : OrderStatus.values()) {
            if (e.getStatusId() == statusId) {
                return e;
            }
        }
        return null;
    }

}
