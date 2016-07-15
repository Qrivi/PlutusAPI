package be.plutus.api.security;

public class AccountNotActiveException extends AuthenticationException{
    public AccountNotActiveException(){
        super(403);
    }
}
