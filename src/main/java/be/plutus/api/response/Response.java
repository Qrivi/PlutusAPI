package be.plutus.api.response;

import be.plutus.core.model.account.Account;
import be.plutus.core.model.account.User;

import java.util.Arrays;
import java.util.Collection;

public class Response{

    private Meta meta;
    private Object data;
    private Collection<String> errors;

    Response( Meta meta, Object data, Collection<String> errors ){
        this.meta = meta;
        this.data = data;
        this.errors = errors;
    }

    public Meta getMeta(){
        return meta;
    }

    public Object getData(){
        return data;
    }

    public Collection<String> getErrors(){
        return errors;
    }

    public static class Builder{

        private Meta.Builder meta;
        private Object data;
        private Collection<String> errors;

        public Builder(){
            meta = new Meta.Builder();
        }

        // META

        public Builder accountDetails( Account account ){
            this.meta = meta.account( account.getEmail(), account.getDefaultCurrency() );
            return this;
        }

        public Builder userDetails( User user ){
            this.meta = meta.user( String.format( "%s %s", user.getFirstName(), user.getLastName() ), user.getFetchDate() );
            return this;
        }

        public Builder success(){
            this.meta = this.meta.success();
            return this;
        }

        public Builder created(){
            this.meta = this.meta.created();
            return this;
        }

        public Builder badRequest(){
            this.meta = this.meta.badRequest();
            return this;
        }

        public Builder unauthorized(){
            this.meta = this.meta.unauthorized();
            return this;
        }

        public Builder forbidden(){
            this.meta = this.meta.forbidden();
            return this;
        }

        public Builder serverError(){
            this.meta = this.meta.serverError();
            return this;
        }

        public Builder notFound(){
            this.meta = this.meta.notFound();
            return this;
        }

        // DATA

        public Builder data( Object data ){
            this.data = data;
            return this;
        }

        // ERRORS

        public Builder errors( String... errors ){
            this.errors = Arrays.asList( errors );
            return this;
        }

        public Builder errors( Collection<String> errors ){
            this.errors = errors;
            return this;
        }

        // BUILD

        public Response build(){
            return new Response(meta.build(), data, errors);
        }
    }
}
