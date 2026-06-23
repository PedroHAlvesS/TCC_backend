package br.com.rapidoja.tcc.mocks;

import br.com.rapidoja.tcc.model.Profile;
import br.com.rapidoja.tcc.model.User;

import java.time.LocalDateTime;
import java.util.List;

public class UserMock {

    public static User getUser() {
        User user = new User();
        user.setEmail("email");
        user.setPhoneNumber("31987654321");
        user.setProfile(Profile.CUSTOMER);
        user.setEnabled(true);
        user.setPassword("password");
        user.setId(1L);
        user.setName("name");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }

    public static List<User> getUsers() {
        return List.of(getUser());
    }
}
