package be.plutus.api.security.exception;

public class AccountNotActiveException extends AuthenticationException{
    public AccountNotActiveException(){
        super( 403 );
    }
}
