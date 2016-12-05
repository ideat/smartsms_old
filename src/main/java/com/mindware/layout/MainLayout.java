package com.mindware.layout;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

public class MainLayout extends VerticalLayout{
	private VerticalLayout upperSection = new VerticalLayout();
	private HorizontalSplitPanel lowerSection = new	HorizontalSplitPanel();
	private VerticalLayout menuLayout = new VerticalLayout();
	private VerticalLayout contentLayout = new VerticalLayout();
	
	public MainLayout(String login) {

		Label logotext = new Label("SMART-SMS");
		ThemeResource resource = new ThemeResource("logo1.png");
		ThemeResource resource1 = new ThemeResource("fondo.png");
		ThemeResource resource2 = new ThemeResource("banner.png");
		
		Embedded reindeerImage = new Embedded( null, resource1 );
		reindeerImage.setWidth( "640px" ); 
		reindeerImage.setHeight( "380px" );
		
        Image image = new Image(null, resource);
        Image image1 = new Image(null, resource1);
        Image image2 = new Image(null, resource2);
        logotext.setHeight("65px");
        logotext.setContentMode(ContentMode.HTML);
        logotext.setStyleName("logotext");
        
        image1.setResponsive(true);  
        GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.setImmediate(false);
		gridLayout_1.setWidth("100.0%");
		gridLayout_1.setHeight("60px");
		gridLayout_1.setMargin(false);
		gridLayout_1.setColumns(2);
		gridLayout_1.setRows(1);
        
		upperSection.addComponent(gridLayout_1);
		gridLayout_1.addComponent(logotext);
		gridLayout_1.setComponentAlignment(logotext, Alignment.BOTTOM_CENTER);
		gridLayout_1.addComponent(image2);
		gridLayout_1.setComponentAlignment(image2, Alignment.TOP_RIGHT);
//		upperSection.addComponent(image);
//		upperSection.addComponent(image2);
//		upperSection.setComponentAlignment(image2, Alignment.TOP_RIGHT);
		menuLayout.addComponent(new Label("MENU"));
		contentLayout.addComponent(reindeerImage);
		contentLayout.setComponentAlignment(reindeerImage, Alignment.TOP_CENTER);
		lowerSection.addComponent(menuLayout);
		lowerSection.addComponent(contentLayout);
		addComponent(upperSection);
		addComponent(lowerSection);
		Label usuario = new Label("USUARIO:" + login);
		addComponent(usuario);
		setSizeFull();
		lowerSection.setSizeFull();
		

		//menuLayout.setSizeFull();
		contentLayout.setSizeFull();
		
		showBorders();
		setExpandRatio(lowerSection, 1);
		lowerSection.setSplitPosition(20);

	}
	
		
	private void showBorders() {
		String style = "v-ddwrapper-over";
		setStyleName(style);
		upperSection.setStyleName(style);
		lowerSection.setStyleName(style);
		menuLayout.setStyleName(style);
		contentLayout.setStyleName(style);
	}
	
	public void addMenuOption(String caption, final Component component) {
		NativeButton button = new NativeButton(caption);
		button.setWidth("100%");
		button.setHeight("40px");
		//menuLayout.setSpacing(true);
		
		menuLayout.addComponent(button);
		button.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				contentLayout.removeAllComponents();
				contentLayout.addComponent(component);



			}
		});
	}
	
	public void callContentLayout(final Component component) {
		contentLayout.removeAllComponents();
		contentLayout.addComponent(component);
	}
		
	
}

