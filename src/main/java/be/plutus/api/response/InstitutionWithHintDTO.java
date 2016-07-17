package be.plutus.api.response;

public class InstitutionWithHintDTO{

    private String name;
    private String slur;

    public InstitutionWithHintDTO(){
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
}
