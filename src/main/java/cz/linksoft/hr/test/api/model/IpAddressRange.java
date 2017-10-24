package cz.linksoft.hr.test.api.model;

import java.net.InetAddress;

/**
 * IP address range as interval &lt;{@link #from}; {@link #to}&gt;.
 */
public class IpAddressRange {

	private InetAddress from;
	private InetAddress to;

	public IpAddressRange() {
	}

	public IpAddressRange(InetAddress from, InetAddress to) {
		this.from = from;
		this.to = to;
	}

	public InetAddress getFrom() {
		return from;
	}

	public void setFrom(InetAddress from) {
		this.from = from;
	}

	public InetAddress getTo() {
		return to;
	}

	public void setTo(InetAddress to) {
		this.to = to;
	}
}
