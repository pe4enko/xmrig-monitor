package ru.softpc.xmrigmonitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * TODO: comment
 * @author madmax
 * @since 07.01.2018
 */
public class RefreshRigInfoEvent extends ApplicationEvent {
	private static final Logger log = LoggerFactory.getLogger(RefreshRigInfoEvent.class);

	/**
	 * Create a new ApplicationEvent.
	 * @param rigs the object on which the event initially occurred (never {@code null})
	 */
	public RefreshRigInfoEvent(List<RigInfo> rigs) {
		super(rigs);
	}

	@Override
	public List<RigInfo> getSource() {
		//noinspection unchecked
		return (List<RigInfo>) super.getSource();
	}
}
