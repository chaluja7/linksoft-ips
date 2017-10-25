package cz.linksoft.hr.test.business.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * IP address range. Belongs to exactly one city. Addresses are stored as "IP NUMBERS":
 *
 * IP Address = w.x.y.z
 * IP Number = 256^3*w + 256^2*x + 256*y + z
 *
 * @author jakubchalupa
 * @since 24.10.17
 */
@Entity
@Table(name = "ip_address_ranges")
@NamedQuery(name = "IpAddressRangeEntity.findByCityId",
    query = "select distinct i from IpAddressRangeEntity i where i.city.id = :cityId")

public class IpAddressRangeEntity extends AbstractEntity {

    /** IP address range FROM. In form of "IP NUMBER" */
    @Column(nullable = false)
    @NotNull
    @Min(0) //Max can be defined as Unsigned int max value for IPv4
    private Long rangeFrom;

    /** IP address range TO. In form of "IP NUMBER" */
    @Column(nullable = false)
    @NotNull
    @Min(0) //Max can be defined as Unsigned int max value for IPv4
    private Long rangeTo;

    /** Reference to {@link CityEntity}. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "city_id")
    private CityEntity city;

    public Long getRangeFrom() {
        return rangeFrom;
    }

    public void setRangeFrom(Long rangeFrom) {
        this.rangeFrom = rangeFrom;
    }

    public Long getRangeTo() {
        return rangeTo;
    }

    public void setRangeTo(Long rangeTo) {
        this.rangeTo = rangeTo;
    }

    public CityEntity getCity() {
        return city;
    }

    public void setCity(CityEntity city) {
        this.city = city;
    }
}
