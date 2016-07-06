package be.plutus.api.response;

import be.plutus.api.response.meta.Meta;

import java.util.Arrays;
import java.util.Collection;

public class Response<M extends Meta, O extends Object>{

    private M meta;
    private Collection<String> errors;
    private O data;

    public M getMeta(){
        return meta;
    }

    public void setMeta( M meta ){
        this.meta = meta;
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

    public O getData(){
        return data;
    }

    public void setData( O data ){
        this.data = data;
    }
}
