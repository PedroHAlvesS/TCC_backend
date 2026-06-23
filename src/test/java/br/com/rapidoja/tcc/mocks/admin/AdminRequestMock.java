package br.com.rapidoja.tcc.mocks.admin;

import br.com.rapidoja.tcc.dto.admin.AdminRequestDTO;

public class AdminRequestMock {
    public static AdminRequestDTO getAdminRequestDTO() {
        AdminRequestDTO adminRequestDTO = new AdminRequestDTO();
        adminRequestDTO.setEmail("email@email.com");
        adminRequestDTO.setPassword("secretPassword");
        adminRequestDTO.setName("Teste Name");
        adminRequestDTO.setPhoneNumber("31987654321");
        return adminRequestDTO;
    }
}
