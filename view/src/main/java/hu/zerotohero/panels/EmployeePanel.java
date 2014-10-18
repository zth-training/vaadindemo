package hu.zerotohero.panels;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import hu.zerotohero.services.EmployeeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Fekete György <gyorgy.fekete@webvalto.hu>
 */
public class EmployeePanel extends CustomComponent {

	private EmployeeService employeeService;

	@Override
	public void attach() {
		super.attach();
		initContext();
		initPanel();
	}

	private void initContext() {
		ApplicationContext context = new ClassPathXmlApplicationContext("/controller-application-context.xml");
		employeeService = context.getBean(EmployeeService.class);
	}

	private void initPanel() {
		VerticalLayout mainLayout = createMainLayout();
		setCompositionRoot(mainLayout);
		setSizeFull();
	}

	private VerticalLayout createMainLayout() {
		Label label = new Label("<h2>Alkalmazottak statisztikái</h2>", ContentMode.HTML);
		FormLayout infoLayout = createInfoLayout();

		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.addComponent(label);
		mainLayout.addComponent(infoLayout);
		mainLayout.setComponentAlignment(label, Alignment.TOP_CENTER);
		mainLayout.setComponentAlignment(infoLayout, Alignment.MIDDLE_CENTER);
		mainLayout.setMargin(true);

		return mainLayout;
	}

	private FormLayout createInfoLayout() {
		TextField maxSalaryTextField = createReadOnlyTextField("Legnagyobb fizetés:", employeeService.getMaxSalaryWithOwner());
		TextField avgSalaryTextField = createReadOnlyTextField("Átlag fizetés:", employeeService.getAvarageSalary());
		TextField minSalaryTextField = createReadOnlyTextField("Legkisebb fizetés:", employeeService.getMinSalaryWithOwner());

		FormLayout infoLayout = new FormLayout();
		infoLayout.addComponent(maxSalaryTextField);
		infoLayout.addComponent(avgSalaryTextField);
		infoLayout.addComponent(minSalaryTextField);
		return infoLayout;
	}

	private TextField createReadOnlyTextField(String caption, String value) {
		TextField textField = new TextField(caption, value);
		textField.setWidth(100, Unit.PERCENTAGE);
		textField.setReadOnly(true);
		return textField;
	}

}
