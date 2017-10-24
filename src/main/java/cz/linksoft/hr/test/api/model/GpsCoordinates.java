package cz.linksoft.hr.test.api.model;

import javax.annotation.Nonnull;

/**
 * GPS latitude/longitude.
 */
public class GpsCoordinates {

	@Nonnull
	private Double latitude;

	@Nonnull
	private Double longtitude;

	public GpsCoordinates() {
	}

	public GpsCoordinates(@Nonnull Double latitude, @Nonnull Double longtitude) {
		this.latitude = latitude;
		this.longtitude = longtitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(Double longtitude) {
		this.longtitude = longtitude;
	}

}
