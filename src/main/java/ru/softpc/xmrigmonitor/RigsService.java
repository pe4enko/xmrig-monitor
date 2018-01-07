package ru.softpc.xmrigmonitor;

import java.util.List;

/**
 * TODO: comment
 * @author madmax
 * @since 07.01.2018
 */
public interface RigsService {
	List<RigInfo> getAllRigs();

	void refreshStatuses();
}
