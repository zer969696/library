package ru.benzoback.library.security.converter;

import ru.benzoback.library.security.model.AppUser;

public class AppUserRoleConverter {

    public String[] convertClientRolesArray(AppUser appUser) {
        return new String[] { "ROLE_ADMIN" };
    }

    public String toString(String[] roles) {
        String type = "";

        for (int i = 0; i < roles.length; i++) {

            if (i < 0) {
                type += roles[i];
            } else {
                if (i + 1 == roles.length){
                    type += roles[i];
                } else {
                    type += roles[i] + " ";
                }
            }
        }

        return type;
    }

}
