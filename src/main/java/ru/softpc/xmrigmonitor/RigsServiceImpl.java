package ru.softpc.xmrigmonitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * TODO: comment
 * @author madmax
 * @since 06.01.2018
 */
@Service
public class RigsServiceImpl implements InitializingBean, DisposableBean, RigsService {
	private static final Logger log = LoggerFactory.getLogger(RigsServiceImpl.class);

	private ExecutorService taskExecutor = Executors.newFixedThreadPool(50);
	//	private List<Rig> rigs = new ArrayList<>();
	private List<RigInfo> rigInfos;

	private RigRepository rigRepository;
	private RestTemplate restTemplate;

	@Autowired
	public RigsServiceImpl(RigRepository rigRepository, RestTemplate restTemplate) {
		this.rigRepository = rigRepository;
		this.restTemplate = restTemplate;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
//		rigs.add(new Rig("192.168.1.1", "DSL", true));
//		rigs.add(new Rig("192.168.1.45", "My Computer", true));
	}

	@Override
	public List<RigInfo> getAllRigs() {
		if (rigInfos == null) {
			refreshStatuses();
		}
		return rigInfos;
	}

	@Override
	public synchronized void refreshStatuses() {
		List<Rig> rigs = rigRepository.findAll();
		List<RigInfo> rigInfos = Collections.synchronizedList(new ArrayList<>(rigs.size()));

		List<Runnable> tasks = new ArrayList<>(rigs.size());
		for (Rig rig : rigs) {
			tasks.add(new ValidateRigTask(rig, rigInfos));
		}

		CompletableFuture<?>[] futures = tasks.stream()
				.map(task -> CompletableFuture.runAsync(task, taskExecutor))
				.toArray(CompletableFuture[]::new);

		CompletableFuture.allOf(futures).join();

		this.rigInfos = rigInfos;
	}

	@Override
	public void destroy() throws Exception {
		taskExecutor.shutdown();
		try {
			taskExecutor.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			//ignore
		}
	}

	private class ValidateRigTask implements Runnable {
		private Rig rig;
		private List<RigInfo> rigInfos;

		private ValidateRigTask(Rig rig, List<RigInfo> rigInfos) {
			this.rig = rig;
			this.rigInfos = rigInfos;
		}

		@Override
		public void run() {
			try {
				XmrigCpuResponse response = restTemplate.getForObject("http://" + rig.getIp() + ":" + rig.getPort(), XmrigCpuResponse.class);
				rigInfos.add(new RigInfo(rig, response, true));
			} catch (Exception e) {
				rigInfos.add(new RigInfo(rig, null, false));
				if (log.isDebugEnabled()) {
					log.error("Error check rig {}", rig, e);
				}
			}
		}
	}
}