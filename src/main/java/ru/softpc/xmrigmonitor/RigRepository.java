package ru.softpc.xmrigmonitor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * TODO: comment
 * @author madmax
 * @since 06.01.2018
 */
public interface RigRepository extends JpaRepository<Rig, Long> {
//	List<Rig> getAllRigs();
}
