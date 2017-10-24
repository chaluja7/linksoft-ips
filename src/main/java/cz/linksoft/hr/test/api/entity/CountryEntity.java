package cz.linksoft.hr.test.api.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Country. Consists of 0-* regions.
 *
 * @author jakubchalupa
 * @since 24.10.17
 */
@Entity
@Table(name = "countries")
public class CountryEntity extends AbstractEntity {

    /** Unique ISO country code. */
    @Column(nullable = false, unique = true)
    @NotNull
    private String code;

    /** Country name. */
    @Column(nullable = false)
    @NotNull
    @Size(min = 1)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "country")
    private List<RegionEntity> regions;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RegionEntity> getRegions() {
        if (regions == null) {
            regions = new ArrayList<>();
        }

        return regions;
    }

    public void setRegions(List<RegionEntity> regions) {
        this.regions = regions;
    }

    public void addRegion(RegionEntity region) {
        if (!getRegions().contains(region)) {
            regions.add(region);
        }

        region.setCountry(this);
    }
}
