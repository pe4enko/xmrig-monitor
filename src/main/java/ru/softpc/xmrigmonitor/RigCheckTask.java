package ru.softpc.xmrigmonitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

@Component
public class RigCheckTask {
	private static final Logger log = LoggerFactory.getLogger(RigCheckTask.class);

	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);


	private RigsService rigsService;
	private ApplicationEventPublisher publisher;

	@Autowired
	public RigCheckTask(RigsService rigsService, ApplicationEventPublisher publisher) {
		this.rigsService = rigsService;
		this.publisher = publisher;
	}

	@Scheduled(fixedDelay = 60_000)
	public void refreshRigsStatuses() {
		rigsService.refreshStatuses();
//		this.publisher.publishEvent(new RefreshRigInfoEvent(rigsService.getAllRigs()));
		pcs.firePropertyChange("rigs", null, rigsService.getAllRigs());
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}
}
