package ru.softpc.xmrigmonitor;

import com.vaadin.annotations.Theme;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * TODO: comment
 * @author madmax
 * @since 06.01.2018
 */
@SpringUI
@Theme("valo")
public class XmrigMonitorUI extends UI implements PropertyChangeListener {
	private static final Logger log = LoggerFactory.getLogger(XmrigMonitorUI.class);

	private Button addRigBtn;
	private Button removeRigBtn;
	private Button checkRig;

	private Grid<RigInfo> grid;
	private RigRepository rigRepository;
	private RigsService rigsService;
	private RestTemplate restTemplate;
	private RigCheckTask rigCheckTask;

	@Autowired
	public XmrigMonitorUI(RigRepository rigRepository, RigsService rigsService, RestTemplate restTemplate, RigCheckTask rigCheckTask) {
		this.rigRepository = rigRepository;
		this.rigsService = rigsService;
		this.restTemplate = restTemplate;
		this.rigCheckTask = rigCheckTask;

		addRigBtn = new Button("Add rig", FontAwesome.PLUS);
		removeRigBtn = new Button("Remove rig", FontAwesome.TRASH_O);
		checkRig = new Button("Check rig", FontAwesome.CHECK);

		grid = new Grid<>(RigInfo.class);
	}

	@Override
	protected void init(VaadinRequest request) {
		removeRigBtn.setEnabled(false);
		checkRig.setEnabled(false);

		addRigBtn.addClickListener(event -> {
			AddWindow sub = new AddWindow(rigRepository);
			UI.getCurrent().addWindow(sub);
		});

		removeRigBtn.addClickListener(event -> {
			Set<RigInfo> selectedItems = grid.getSelectedItems();
			selectedItems.forEach(rigInfo -> rigRepository.delete(rigInfo.getId()));

			refreshRigs("ddf");
		});

		checkRig.addClickListener(event -> {
			XmrigCpuResponse response = restTemplate.getForObject("http://localhost:3334/", XmrigCpuResponse.class);
			log.info("Response: {}", response);
		});

//		CssLayout actions = new CssLayout(addRigBtn, removeRigBtn);
//		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
//		grid.setHeight(500, Unit.PIXELS);
//		grid.setWidth();
//		setHeight(100, Unit.PERCENTAGE);
		grid.setSizeFull();
		grid.setHeight(100, Unit.PERCENTAGE);
		grid.setColumns("ip", "workerId", "currentHashrate", "hashrate1min", "hashrate15min", "hashrateHighest", "online");

		HorizontalLayout actions = new HorizontalLayout(addRigBtn, removeRigBtn, checkRig);
		VerticalLayout mainLayout = new VerticalLayout(actions);
		mainLayout.addComponentsAndExpand(grid);
		setContent(mainLayout);

//		setContent(new Button("Click me", e -> Notification.show("Hello Spring+Vaadin user!")));

		// Connect selected Customer to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			removeRigBtn.setEnabled(e.getValue() != null);
			checkRig.setEnabled(e.getValue() != null);
		});

		refreshRigs("ddf");

		setPollInterval(15000);

		rigCheckTask.addPropertyChangeListener(this);
	}

	private void refreshRigs(String filterText) {
		rigsService.refreshStatuses();
		grid.setItems(rigsService.getAllRigs());
	}

	@EventListener
	public void handleContextRefresh(RefreshRigInfoEvent event) {
		access(() -> grid.setItems(event.getSource()));
	}

	@Override
	public void close() {
		super.close();
		rigCheckTask.removePropertyChangeListener(this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		//noinspection unchecked
		access(() -> grid.setItems((Collection<RigInfo>) event.getNewValue()));
	}
}