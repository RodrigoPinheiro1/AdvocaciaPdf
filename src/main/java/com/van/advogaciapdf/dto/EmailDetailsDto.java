package com.van.advogaciapdf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetailsDto {

    private String recebedor;
    private String texto;
    private String assunto;
}