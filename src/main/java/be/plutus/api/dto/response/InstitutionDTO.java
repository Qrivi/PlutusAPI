package be.plutus.api.dto.response;

public class InstitutionDTO{

    private String name;
    private String slur;

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
}
