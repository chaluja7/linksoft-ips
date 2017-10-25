package cz.linksoft.hr.test.business.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Region. Belongs to exactly one country and locates 0-* cities.
 *
 * @author jakubchalupa
 * @since 24.10.17
 */
@Entity
@Table(name = "regions")
@NamedQuery(name = "RegionEntity.findByCountryId",
    query = "select distinct r from RegionEntity r left outer join fetch r.country where r.country.id = :countryId order by r.id")
public class RegionEntity extends AbstractEntity {

    /** Region name. */
    @Column(nullable = false, length = 128)
    @NotNull
    @Size(min = 1, max = 128)
    private String name;

    /** Reference to {@link CountryEntity}. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "country_id")
    private CountryEntity country;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "region")
    private List<CityEntity> cities;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CountryEntity getCountry() {
        return country;
    }

    public void setCountry(CountryEntity country) {
        this.country = country;
    }

    public List<CityEntity> getCities() {
        if (cities == null) {
            cities = new ArrayList<>();
        }

        return cities;
    }

    public void setCities(List<CityEntity> cities) {
        this.cities = cities;
    }

    public void addCity(CityEntity city) {
        if (!getCities().contains(city)) {
            cities.add(city);
        }

        city.setRegion(this);
    }
}
