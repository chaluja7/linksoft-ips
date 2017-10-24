package cz.linksoft.hr.test.api.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * City. Belongs to exactly one region.
 *
 * @author jakubchalupa
 * @since 24.10.17
 */
@Entity
@Table(name = "cities")
public class CityEntity extends AbstractEntity {

    /** City name. Not unique on purpose. */
    @Column(nullable = false)
    @NotNull
    @Size(min = 1)
    private String name;

    /** Latitude of the city center. */
    @Column(nullable = false)
    @NotNull
    @Min(-90)
    @Max(90)
    private Double latitude;

    /** Longitude of the city center. */
    @Column(nullable = false)
    @NotNull
    @Min(-180)
    @Max(180)
    private Double longitude;

    /** Reference to {@link RegionEntity}. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "region_id")
    private RegionEntity region;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "city")
    private List<IpAddressRangeEntity> ipAddressRanges;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public RegionEntity getRegion() {
        return region;
    }

    public void setRegion(RegionEntity region) {
        this.region = region;
    }

    public List<IpAddressRangeEntity> getIpAddressRanges() {
        if (ipAddressRanges == null) {
            ipAddressRanges = new ArrayList<>();
        }

        return ipAddressRanges;
    }

    public void setIpAddressRanges(List<IpAddressRangeEntity> ipAddressRanges) {
        this.ipAddressRanges = ipAddressRanges;
    }

    public void addIpAddressRange(IpAddressRangeEntity ipAddressRange) {
        if (!getIpAddressRanges().contains(ipAddressRange)) {
            ipAddressRanges.add(ipAddressRange);
        }

        ipAddressRange.setCity(this);
    }
}
