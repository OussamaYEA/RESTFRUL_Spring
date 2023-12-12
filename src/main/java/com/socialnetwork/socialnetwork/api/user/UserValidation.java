package com.socialnetwork.socialnetwork.api.user;

import com.socialnetwork.socialnetwork.api.utils.Validator;

public class UserValidation {
    public static void newUserValidation(User user) {

        if(Validator.emailValidation(user.getEmail())){

        }
    }
}
