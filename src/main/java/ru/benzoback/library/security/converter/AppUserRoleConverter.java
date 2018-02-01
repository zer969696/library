package ru.benzoback.library.security.converter;

import ru.benzoback.library.security.model.AppUser;

public class AppUserRoleConverter {

    public String[] convertClientRolesArray(AppUser appUser) {
//        Set<Role> clientRoles = customUser.getRoles();
//        Role[] clientRolesArray = clientRoles.toArray(new Role[clientRoles.size()]);
//
//        String[] clientType = new String[clientRolesArray.length];
//        for (int i = 0; i < clientRolesArray.length; i++){
//            clientType[i] = clientRolesArray[i].getDescription();
//        }
        String[] array = new String[] { "ROLE_ADMIN" };
        return array;
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
