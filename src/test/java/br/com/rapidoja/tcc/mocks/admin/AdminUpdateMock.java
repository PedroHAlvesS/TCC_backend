package br.com.rapidoja.tcc.mocks.admin;

import br.com.rapidoja.tcc.dto.admin.AdminUpdateDTO;

public class AdminUpdateMock {
    public static AdminUpdateDTO getAdminUpdateDTO() {
        AdminUpdateDTO adminUpdateDTO = new AdminUpdateDTO();
        adminUpdateDTO.setEmail("email@email.com");
        adminUpdateDTO.setPassword("secretPassword");
        adminUpdateDTO.setPhoneNumber("31987654321");
        return adminUpdateDTO;
    }
}
