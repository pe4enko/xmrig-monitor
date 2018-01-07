package ru.softpc.xmrigmonitor;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * TODO: comment
 * @author madmax
 * @since 06.01.2018
 */
public class AddWindow extends Window {
	private static final Logger log = LoggerFactory.getLogger(AddWindow.class);
	private RigRepository rigRepository;

	public AddWindow(RigRepository rigRepository) {
		super("Add new rig");

		this.rigRepository = rigRepository;

		center();

		setClosable(false);
		setModal(true);
		setResizable(false);

		VerticalLayout verticalLayout = new VerticalLayout();
		TextField ipAddress = new TextField("IP-address");
		TextField port = new TextField("Port");
		verticalLayout.addComponent(ipAddress);
		verticalLayout.addComponent(port);


		Button saveButton = new Button("Save", event -> {
			//todo: validation
			rigRepository.save(new Rig(ipAddress.getValue(), port.getValue()));
		});

		Button closeButton = new Button("Close", event -> close());
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.addComponents(saveButton, closeButton);

		verticalLayout.addComponent(horizontalLayout);
		setContent(verticalLayout);
	}
}
