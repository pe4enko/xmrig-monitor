package ru.softpc.xmrigmonitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * TODO: comment
 * @author madmax
 * @since 06.01.2018
 */
@Entity
public class Rig {
	private static final Logger log = LoggerFactory.getLogger(Rig.class);

	@Id
	@GeneratedValue
	private Long id;

	private String ip;

	private String customName;

	private String port;

	private boolean online;

	public Rig() {
	}

	public Rig(String ip, String port) {
		this.ip = ip;
		this.port = port;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	@Override
	public String toString() {
		return "Rig{" +
		       "ip='" + ip + '\'' +
		       ", customName='" + customName + '\'' +
		       ", online=" + online +
		       '}';
	}
}
