package com.mindware.smartsms;

import com.mindware.domain.Usuario;
import com.mindware.layout.MainLayout;
import com.mindware.vista.*;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import javax.servlet.annotation.WebServlet;

/**
 *
 */
@Theme("mytheme")
//@Widgetset("com.mindware.MyAppWidgetset")
public class smsUI extends UI {
	GrupoForm grupoForm;
	BandejaSalida bandejaSalida;
	private LoginUI login;
    @Override
    protected void init(VaadinRequest vaadinRequest) {
//        final VerticalLayout layout = new VerticalLayout();
//        layout.setMargin(true);
//        setContent(layout);
    	//Categoria categoria = new Categoria();
    	Usuario usuario = new Usuario();
    	//categoria.setCategoria_id(1);
        //	categoria.setNombreCagoria("Categoria");
    	
        	
//    	Session session = sessionFactory.openSession();
//    	session.beginTransaction();
//    	session.save(categoria);
//    	session.save(usuario);
//    	session.getTransaction().commit();
    	
         login = new LoginUI();
		
		setContent(login);
		

    }

    public void cargarMenu(int usuarioId, String login){
    	   		
    		Componer componerMensaje = new Componer(usuarioId);
    		ListaContactos listaContactos = new ListaContactos(usuarioId);
    		grupoForm = new GrupoForm(usuarioId);
//    		BandejaEntrada bandejaEntrada = new BandejaEntrada(usuarioId);
    		bandejaSalida = new BandejaSalida(usuarioId);
    		BandejaEnviados bandejaEnviados = new BandejaEnviados(usuarioId);
    		ListaUsuarios listaUsuarios = new ListaUsuarios(usuarioId);
    		MainLayout layout = new MainLayout(login);
    		
    		
    		layout.addMenuOption("Componer Mensaje", componerMensaje);
    		layout.addMenuOption("Contactos", listaContactos);
    		layout.addMenuOption("Grupos", grupoForm);
//    		layout.addMenuOption("Bandeja Entrada",bandejaEntrada);
    		layout.addMenuOption("Bandeja Salida",bandejaSalida);
    		layout.addMenuOption("Mensajes Enviados",bandejaEnviados);
//    		layout.addMenuOption("Modem",getAbsolutLayout());
    		layout.addMenuOption("Usuarios",listaUsuarios);
//    		layout.addMenuOption("Planificador",getAbsolutLayout());

    	//	layout.addMenuOption("Form layout",  formularios.FormularioConfiguracionCobros() );
    		
    		
    		setContent(layout);
    		


    	}
    
    public void actualizarGrupo() {
    	grupoForm.llenarTabla();
    }
    
    public void actualizarBandejaSalida() {
    	bandejaSalida.datosBandejaSalida();
    }
    
    private GridLayout getGridLayout() {
		int rows = 3, columns = 3;
		GridLayout gridLayout = new GridLayout(columns, rows);
		gridLayout.setSizeFull();
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				Button button = new Button("-_-");
				button.setStyleName(getCaption());
				gridLayout.addComponent(button, column, row);
			}
		}
		return gridLayout;
	}
	
	private Component getAbsolutLayout() {
		Button button = new Button(
				"I'm whimsically located at 42, 57");
		AbsoluteLayout absoluteLayout = new AbsoluteLayout();
		absoluteLayout.addComponent(button,
				"left: 42px; top: 57px");
		button.setStyleName("boton");
		//button.setHeight("80px");
		return absoluteLayout;
	}
	
	private FormLayout getFormLayout() {
		TextField tf1 = new TextField("TextField");
		TextField tf2 = new TextField("TextField");
		ComboBox cb = new ComboBox("ComboBox");
		Button b = new Button("Button");
		FormLayout formLayout = new FormLayout(tf1, tf2, cb, b);
		formLayout.setMargin(true);
		return formLayout;
	}
    
    
    
    
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = smsUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
