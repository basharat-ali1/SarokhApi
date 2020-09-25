package net.sarokh.api.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "Shipper_Address", schema = "Sarokh_db", catalog = "")
public class ShipperAddress {
    private int id;
    private String address;
    private String city;
    private Integer postCode;
    private String country;
    private String locationLatitude;
    private String locationLongitude;
    private int shipperId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "address", nullable = true, length = 45)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "city", nullable = true, length = 45)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "post_code", nullable = true)
    public Integer getPostCode() {
        return postCode;
    }

    public void setPostCode(Integer postCode) {
        this.postCode = postCode;
    }

    @Basic
    @Column(name = "country", nullable = true, length = 45)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Basic
    @Column(name = "location_latitude", nullable = true, length = 20)
    public String getLocationLatitude() {
        return locationLatitude;
    }

    public void setLocationLatitude(String locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    @Basic
    @Column(name = "location_longitude", nullable = true, length = 20)
    public String getLocationLongitude() {
        return locationLongitude;
    }

    public void setLocationLongitude(String locationLongitude) {
        this.locationLongitude = locationLongitude;
    }

    @Basic
    @Column(name = "shipper_id", nullable = false)
    public int getShipperId() {
        return shipperId;
    }

    public void setShipperId(int shipperId) {
        this.shipperId = shipperId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShipperAddress that = (ShipperAddress) o;

        if (id != that.id) return false;
        if (shipperId != that.shipperId) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (postCode != null ? !postCode.equals(that.postCode) : that.postCode != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (locationLatitude != null ? !locationLatitude.equals(that.locationLatitude) : that.locationLatitude != null)
            return false;
        return locationLongitude != null ? locationLongitude.equals(that.locationLongitude) : that.locationLongitude == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (postCode != null ? postCode.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (locationLatitude != null ? locationLatitude.hashCode() : 0);
        result = 31 * result + (locationLongitude != null ? locationLongitude.hashCode() : 0);
        result = 31 * result + shipperId;
        return result;
    }
}
