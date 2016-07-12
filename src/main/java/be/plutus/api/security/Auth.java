package be.plutus.api.security;

import be.plutus.core.model.account.Account;
import org.springframework.security.core.context.SecurityContextHolder;

public class Auth{

    public static Account current(){
        return (Account)SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
