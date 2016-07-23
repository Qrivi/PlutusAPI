package be.plutus.api.security.exception;

public class AuthenticationException extends RuntimeException{

    private int status;

    public AuthenticationException(){
        this.status = 401;
    }

    public AuthenticationException( int status ){
        this.status = status;
    }

    public int getStatus(){
        return status;
    }
}
