package be.plutus.api.response;

public class InstitutionWithHintDTO extends InstitutionDTO{

    private String hint;

    public InstitutionWithHintDTO(){
    }

    public String getHint(){
        return hint;
    }

    public void setHint( String hint ){
        this.hint = hint;
    }
}
