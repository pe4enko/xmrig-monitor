package ru.softpc.xmrigmonitor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class XmrigCpuResponse {

	@JsonProperty("worker_id")
	private String workerId;
	private Hashrate hashrate;
	private boolean hugepages;

	public XmrigCpuResponse() {
	}

	public String getWorkerId() {
		return workerId;
	}

	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}

	public Hashrate getHashrate() {
		return hashrate;
	}

	public void setHashrate(Hashrate hashrate) {
		this.hashrate = hashrate;
	}

	public boolean isHugepages() {
		return hugepages;
	}

	public void setHugepages(boolean hugepages) {
		this.hugepages = hugepages;
	}

	@Override
	public String toString() {
		return "XmrigCpuResponse{" +
		       "workerId='" + workerId + '\'' +
		       ", hashrate=" + hashrate +
		       ", hugepages=" + hugepages +
		       '}';
	}
}
