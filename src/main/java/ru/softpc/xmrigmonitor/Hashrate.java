package ru.softpc.xmrigmonitor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * TODO: comment
 * @author madmax
 * @since 07.01.2018
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Hashrate {
	private static final Logger log = LoggerFactory.getLogger(Hashrate.class);
	private Double highest;
	private Double[] total;

	public Hashrate() {
	}

	public Double getHighest() {
		return highest;
	}

	public void setHighest(Double highest) {
		this.highest = highest;
	}

	public Double[] getTotal() {
		return total;
	}

	public void setTotal(Double[] total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "Hashrate{" +
		       "highest=" + highest +
		       ", total=" + Arrays.toString(total) +
		       '}';
	}
}
