package be.plutus.api.response;

import be.plutus.core.model.account.Account;
import be.plutus.core.model.account.User;
import be.plutus.core.model.currency.Currency;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Arrays;
import java.util.Collection;

@JsonInclude( JsonInclude.Include.NON_NULL )
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

        //region META

        public Builder account( Account account ){
            this.meta = meta.account( account.getEmail() );
            this.meta = meta.currency( account.getDefaultCurrency() );
            return this;
        }

        public Builder user( User user ){
            this.meta = meta.user( user.getUsername() );
            this.meta = meta.updated( user.getFetchDate() );
            return this;
        }

        public Builder currency( Currency currency ){
            this.meta = meta.currency( currency );
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

        //endregion

        //region DATA

        public Builder data( Object data ){
            this.data = data;
            return this;
        }

        //endregion

        //region ERRORS

        public Builder errors( String... errors ){
            this.errors = Arrays.asList( errors );
            return this;
        }

        public Builder errors( Collection<String> errors ){
            this.errors = errors;
            return this;
        }

        //endregion

        public Response build(){
            return new Response( meta.build(), data, errors );
        }
    }
}
