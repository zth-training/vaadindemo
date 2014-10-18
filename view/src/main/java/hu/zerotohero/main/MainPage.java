package hu.zerotohero.main;

import com.vaadin.ui.*;
import hu.zerotohero.panels.CountryPanel;
import hu.zerotohero.panels.EmployeePanel;

/**
 * @author Fekete György <gyorgy.fekete@webvalto.hu>
 */
public class MainPage extends CustomComponent {

	private MenuBar menu;
	private TabSheet tabSheet;
	private VerticalLayout mainLayout;

	public MainPage() {
		super();
		init();
	}

	private void init() {
		initTabSheet();
		initMenu();
		initMainLayout();

		setCompositionRoot(mainLayout);
	}

	private void initTabSheet() {
		tabSheet = new TabSheet();
	}

	private void initMenu() {
		menu = new MenuBar();
		menu.setWidth(100, Unit.PERCENTAGE);
		menu.setAutoOpen(true);

		NewTabCommand employeeCommand = new NewTabCommand("Alkalmazottak", new EmployeePanel());
		NewTabCommand countryCommand = new NewTabCommand("Országok", new CountryPanel());

		menu.addItem("Alkalmazottak", employeeCommand);
		menu.addItem("Országok", countryCommand);
	}

	private void initMainLayout() {
		mainLayout = new VerticalLayout();
		mainLayout.addComponent(menu);
		mainLayout.addComponent(tabSheet);
	}

	private class NewTabCommand implements MenuBar.Command {

		private String caption;
		private Component component;

		private NewTabCommand(String caption, Component component) {
			this.caption = caption;
			this.component = component;
		}

		@Override
		public void menuSelected(MenuBar.MenuItem selectedItem) {
			TabSheet.Tab tab = tabSheet.addTab(component, caption);
			tab.setClosable(true);
			tabSheet.setSelectedTab(tab);
		}
	}

}
