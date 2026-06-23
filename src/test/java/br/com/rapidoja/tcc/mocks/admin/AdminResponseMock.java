package br.com.rapidoja.tcc.mocks.admin;

import br.com.rapidoja.tcc.dto.admin.AdminResponseDTO;
import br.com.rapidoja.tcc.model.Profile;

import java.time.LocalDateTime;
import java.util.List;

public class AdminResponseMock {
    public static AdminResponseDTO getAdminResponseDTO() {
        AdminResponseDTO adminResponseDTO = new AdminResponseDTO();
        adminResponseDTO.setProfile(Profile.ADMIN);
        adminResponseDTO.setCreatedAt(LocalDateTime.now());
        adminResponseDTO.setUpdatedAt(LocalDateTime.now());
        adminResponseDTO.setEnabled(true);
        adminResponseDTO.setId(1L);
        adminResponseDTO.setName("name");
        adminResponseDTO.setEmail("email");
        adminResponseDTO.setPhoneNumber("31987654321");
        return adminResponseDTO;
    }

    public static List<AdminResponseDTO> getAdminResponseList() {
        return List.of(getAdminResponseDTO());
    }
}
