package hu.zerotohero.panels;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import hu.zerotohero.entities.Country;
import hu.zerotohero.entities.Region;
import hu.zerotohero.services.CountryService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @author Fekete György <gyorgy.fekete@webvalto.hu>
 */
public class CountryPanel extends CustomComponent {

	private static final String COUNTRY_ID = "Ország azonosító";
	private static final String COUNTRY_NAME = "Ország Név";
	private static final String REGION_ID = "Régió";
	private static final String REGION_NAME = "Régió Név";

	private CountryService countryService;

	private Table countryTable;
	private ComboBox tableRegionComboBox;
	private TextField newCountryIdTextField;
	private TextField newCountryNameTextField;
	private ComboBox newCountryRegionComboBox;

	@Override
	public void attach() {
		super.attach();
		initContext();
		initPanel();
	}

	private void initContext() {
		ApplicationContext context = new ClassPathXmlApplicationContext("/controller-application-context.xml");
		countryService = context.getBean(CountryService.class);
	}

	private void initPanel() {
		setSizeFull();

		HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();
		splitPanel.addComponent(createCountryTableLayout());
		splitPanel.addComponent(createNewCountryEditorLayout());

		setCompositionRoot(splitPanel);
	}

	private VerticalLayout createCountryTableLayout() {
		countryTable = new Table("", createCountryContainer(1));
		countryTable.setSelectable(true);
		countryTable.setImmediate(true);
		countryTable.setSizeFull();

		tableRegionComboBox = createRegionComboBox();
		tableRegionComboBox.addValueChangeListener(new RegionChangedListener());

		VerticalLayout countryTableLayout = new VerticalLayout();
		countryTableLayout.addComponent(tableRegionComboBox);
		countryTableLayout.addComponent(countryTable);
		countryTableLayout.addComponent(createDeleteCountryButton());
		return countryTableLayout;
	}

	private Button createDeleteCountryButton() {
		Button deleteCountryButton = new Button("Törlés");
		deleteCountryButton.addClickListener(new DeleteCountryButtonClickListener());
		return deleteCountryButton;
	}

	private FormLayout createNewCountryEditorLayout() {
		Label newCountryLabel = new Label("<h2 style=\"text-align: center;\">Új ország felvétele</h2>", ContentMode.HTML);

		newCountryIdTextField = createTextField(COUNTRY_ID);
		newCountryNameTextField = createTextField(COUNTRY_NAME);
		newCountryRegionComboBox = createRegionComboBox();

		FormLayout newCountryEditorLayout = new FormLayout();
		newCountryEditorLayout.addComponent(newCountryLabel);
		newCountryEditorLayout.addComponent(newCountryIdTextField);
		newCountryEditorLayout.addComponent(newCountryNameTextField);
		newCountryEditorLayout.addComponent(newCountryRegionComboBox);
		newCountryEditorLayout.addComponent(createNewCountryButton());
		newCountryEditorLayout.setMargin(true);
		return newCountryEditorLayout;
	}

	private TextField createTextField(String caption) {
		TextField textField = new TextField(caption);
		textField.setWidth(100, Unit.PERCENTAGE);
		return textField;
	}

	private ComboBox createRegionComboBox() {
		ComboBox regionComboBox = new ComboBox(REGION_ID, createRegionContainer());
		regionComboBox.setSizeFull();
		regionComboBox.setImmediate(true);
		regionComboBox.setNullSelectionAllowed(false);
		regionComboBox.setItemCaptionPropertyId(REGION_NAME);
		regionComboBox.setValue(regionComboBox.getItemIds().iterator().next());
		return regionComboBox;
	}

	private Button createNewCountryButton() {
		Button newCountryButton = new Button("Hozzáadás");
		newCountryButton.addClickListener(new NewCountryButtonClickListener());
		return newCountryButton;
	}

	private void refreshCountryTable() {
		Integer currentRegionId = ((Region) tableRegionComboBox.getValue()).getId();
		countryTable.setContainerDataSource(createCountryContainer(currentRegionId));
	}

	private IndexedContainer createCountryContainer(Integer regionId) {
		IndexedContainer countryContainer = new IndexedContainer();
		countryContainer.addContainerProperty(COUNTRY_ID, String.class, "");
		countryContainer.addContainerProperty(COUNTRY_NAME, String.class, "");
		countryContainer.addContainerProperty(REGION_ID, String.class, "");

		List<Country> countryList = countryService.findAllCountriesFromOneRegion(regionId);
		for (Country country : countryList) {
			createCountryContainerItem(countryContainer, country);
		}
		return countryContainer;
	}

	private void createCountryContainerItem(IndexedContainer countryContainer, Country country) {
		Item item = countryContainer.addItem(country);
		if (item != null) {
			item.getItemProperty(COUNTRY_ID).setValue(country.getId().toString());
			item.getItemProperty(COUNTRY_NAME).setValue(country.getName());
			item.getItemProperty(REGION_ID).setValue(country.getRegion().getId().toString());
		}
	}

	private IndexedContainer createRegionContainer() {
		IndexedContainer regionContainer = new IndexedContainer();
		regionContainer.addContainerProperty(REGION_ID, String.class, "");
		regionContainer.addContainerProperty(REGION_NAME, String.class, "");

		for (Region region : countryService.findAllRegions()) {
			createRegionContainerItem(regionContainer, region);
		}
		return regionContainer;
	}

	private void createRegionContainerItem(IndexedContainer regionContainer, Region region) {
		Item item = regionContainer.addItem(region);
		if (item != null) {
			item.getItemProperty(REGION_ID).setValue(region.getId().toString());
			item.getItemProperty(REGION_NAME).setValue(region.getRegionName());
		}
	}

	protected class RegionChangedListener implements Property.ValueChangeListener {
		@Override
		public void valueChange(Property.ValueChangeEvent event) {
			refreshCountryTable();
		}
	}

	protected class DeleteCountryButtonClickListener implements Button.ClickListener {
		@Override
		public void buttonClick(Button.ClickEvent event) {
			deleteCurrentlySelectedCountry();
			refreshCountryTable();
		}

		private void deleteCurrentlySelectedCountry() {
			Country currentlySelectedCountry = (Country) countryTable.getValue();
			if (currentlySelectedCountry != null) {
				countryService.deleteCountry(currentlySelectedCountry);
			}
		}
	}

	protected class NewCountryButtonClickListener implements Button.ClickListener {
		@Override
		public void buttonClick(Button.ClickEvent event) {
			if (areInputValuesValid()) {
				createNewCountryAndRefreshTable();
			}
		}

		private boolean areInputValuesValid() {
			return newCountryRegionComboBox.getValue() != null &&
					newCountryIdTextField.getValue() != null && !newCountryIdTextField.getValue().trim().isEmpty() &&
					newCountryNameTextField.getValue() != null && !newCountryNameTextField.getValue().trim().isEmpty();
		}

		private void createNewCountryAndRefreshTable() {
			try {
				createNewCountryFromInput();
				refreshCountryTable();
			} catch (Exception e) {
				Notification.show("Hiba történt a mentés során. Kérlek ellenőrízd az adatokat!");
			}
		}

		private void createNewCountryFromInput() {
			countryService.createCountry(newCountryIdTextField.getValue(), ((Region) newCountryRegionComboBox.getValue()).getId(), newCountryNameTextField.getValue());
			newCountryIdTextField.setValue("");
			newCountryNameTextField.setValue("");
		}
	}
}
