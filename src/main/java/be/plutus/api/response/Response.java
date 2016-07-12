package be.plutus.api.response;

import be.plutus.api.security.Auth;

import java.util.Arrays;
import java.util.Collection;

public class Response{

    private Meta meta;
    private Object data;
    private Collection<String> errors;

    public Response(){
    }

    public Meta getMeta(){
        return meta;
    }

    public void setMeta( Meta meta ){
        this.meta = meta;
    }

    public Object getData(){
        return data;
    }

    public void setData( Object data ){
        this.data = data;
    }

    public Collection<String> getErrors(){
        return errors;
    }

    public void setErrors( String... errors ){
        this.errors = Arrays.asList( errors );
    }

    public void setErrors( Collection<String> errors ){
        this.errors = errors;
    }

    public static class Builder{

        private Meta.Builder meta;
        private Object data;
        private Collection<String> errors;

        public Builder(){
            meta = new Meta.Builder();
        }

        public Builder data( Object data ){
            this.data = data;
            return this;
        }

        public Builder errors( String... errors ){
            this.errors = Arrays.asList( errors );
            return this;
        }

        public Builder errors( Collection<String> errors ){
            this.errors = errors;
            return this;
        }

        public Builder accountDetails(){
            this.meta = meta.account( Auth.current() );
            return this;
        }

        public Builder userDetails(){
            //TODO implement
            return this;
        }

        public Builder success(){
            this.meta = this.meta.success();
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

        public Response build(){
            Response response = new Response();
            response.setMeta( meta.build() );
            response.setErrors( errors );
            response.setData( data );
            return response;
        }
    }
}
