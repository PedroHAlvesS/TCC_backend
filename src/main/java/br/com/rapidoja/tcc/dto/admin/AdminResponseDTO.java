package br.com.rapidoja.tcc.dto.admin;

import br.com.rapidoja.tcc.model.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private Profile profile;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean enabled;
}
