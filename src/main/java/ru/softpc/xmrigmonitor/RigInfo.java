package ru.softpc.xmrigmonitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO: comment
 * @author madmax
 * @since 07.01.2018
 */
public class RigInfo {
	private static final Logger log = LoggerFactory.getLogger(RigInfo.class);

	private Long id;
	private String ip;

	private String workerId;

	private Double hashrateHighest;

	private Double currentHashrate;
	private Double hashrate1min;
	private Double hashrate15min;

	private boolean hugepages;

	private boolean online;

	public RigInfo(Rig rig, XmrigCpuResponse xmrigCpuResponse, boolean online) {
		id = rig.getId();
		ip = rig.getIp();
		if (xmrigCpuResponse != null) {
			workerId = xmrigCpuResponse.getWorkerId();
			hashrateHighest = xmrigCpuResponse.getHashrate().getHighest();
			currentHashrate = xmrigCpuResponse.getHashrate().getTotal()[0];
			hashrate1min = xmrigCpuResponse.getHashrate().getTotal()[1];
			hashrate15min = xmrigCpuResponse.getHashrate().getTotal()[2];
			hugepages = xmrigCpuResponse.isHugepages();
		}
		this.online = online;
	}

	public Long getId() {
		return id;
	}

	public String getIp() {
		return ip;
	}

	public String getWorkerId() {
		return workerId;
	}

	public Double getHashrateHighest() {
		return hashrateHighest;
	}

	public Double getCurrentHashrate() {
		return currentHashrate;
	}

	public Double getHashrate1min() {
		return hashrate1min;
	}

	public Double getHashrate15min() {
		return hashrate15min;
	}

	public boolean isHugepages() {
		return hugepages;
	}

	public boolean isOnline() {
		return online;
	}
}
