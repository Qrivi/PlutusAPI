package be.plutus.api.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ExceptionHandler<E extends Exception>{
    void handle( HttpServletRequest req, HttpServletResponse res, E e ) throws IOException;
}
