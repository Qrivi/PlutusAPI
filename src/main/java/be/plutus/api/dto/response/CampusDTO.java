package be.plutus.api.dto.response;

public class CampusDTO{

    private String name;
    private InstitutionDTO institution;
    private double lat;
    private double lng;
    private String address;
    private String zip;
    private String city;
    private String country;

    public CampusDTO(){
    }

    public String getName(){
        return name;
    }

    public void setName( String name ){
        this.name = name;
    }

    public InstitutionDTO getInstitution(){
        return institution;
    }

    public void setInstitution( InstitutionDTO institution ){
        this.institution = institution;
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

    public String getAddress(){
        return address;
    }

    public void setAddress( String address ){
        this.address = address;
    }

    public String getZip(){
        return zip;
    }

    public void setZip( String zip ){
        this.zip = zip;
    }

    public String getCity(){
        return city;
    }

    public void setCity( String city ){
        this.city = city;
    }

    public String getCountry(){
        return country;
    }

    public void setCountry( String country ){
        this.country = country;
    }
}
