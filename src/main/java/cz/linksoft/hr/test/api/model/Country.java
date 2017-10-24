package cz.linksoft.hr.test.api.model;

import javax.annotation.Nonnull;

public class Country {

	/** Unique country ID (generated). */
	@Nonnull
	Long id;

	/** Unique ISO country code. */
	@Nonnull
	String code;

	/** Country name. */
	@Nonnull
	String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

}
