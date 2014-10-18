package hu.zerotohero.main;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

/**
 * @author Fekete György <gyorgy.fekete@webvalto.hu>
 */

@Theme("reindeer")
public class MainUI extends UI {

	@Override
	protected void init(VaadinRequest request) {
	    MainPage mainPage = new MainPage();
		setContent(mainPage);
	}

}
