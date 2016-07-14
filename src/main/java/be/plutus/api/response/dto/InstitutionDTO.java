package be.plutus.api.response.dto;

public class InstitutionDTO{

    private String name;
    private String slur;
    private String hint;

    public InstitutionDTO(){
    }

    public String getName(){
        return name;
    }

    public void setName( String name ){
        this.name = name;
    }

    public String getSlur(){
        return slur;
    }

    public void setSlur( String slur ){
        this.slur = slur;
    }

    public String getHint(){
        return hint;
    }

    public void setHint( String hint ){
        this.hint = hint;
    }
}
