package be.plutus.api.dto.response;

public class LocationDTO{

    private String name;
    private double lat;
    private double lng;
    private CampusDTO campus;

    public LocationDTO(){
    }

    public String getName(){
        return name;
    }

    public void setName( String name ){
        this.name = name;
    }

    public double getLat(){
        return lat;
    }

    public void setLat( double lat ){
        this.lat = lat;
    }

    public double getLng(){
        return lng;
    }

    public void setLng( double lng ){
        this.lng = lng;
    }

    public CampusDTO getCampus(){
        return campus;
    }

    public void setCampus( CampusDTO campus ){
        this.campus = campus;
    }
}
