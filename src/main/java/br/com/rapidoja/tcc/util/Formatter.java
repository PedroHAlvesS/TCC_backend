package br.com.rapidoja.tcc.util;

import br.com.rapidoja.tcc.model.Profile;
import org.mapstruct.Named;

public class Formatter {

    @Named("formatProfile")
    public static String formatProfile(Profile profile) {
        return profile.getProfileName();
    }
}
