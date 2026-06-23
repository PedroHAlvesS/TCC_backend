package br.com.rapidoja.tcc.dto.customer;

import br.com.rapidoja.tcc.model.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private Profile profile;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean enabled;
}
