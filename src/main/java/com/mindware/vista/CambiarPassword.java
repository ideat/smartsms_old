package com.mindware.vista;

import com.mindware.domain.Usuario;
import com.mindware.services.UsuarioService;
import com.mindware.security.Encript;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

import de.steinwedel.messagebox.MessageBox;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;


public class CambiarPassword extends Window implements ClickListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AbsoluteLayout mainLayout;
	private HorizontalLayout horizontalLayout_4;
	private Button btncerrar;
	private Button btnguardar;
	private Label lbltitulo;
	private GridLayout gridLayout_1;
		
	private PasswordField txtNuevoPassword;
	private Label lblNuevoPassword;
	private PasswordField txtPassword;
	private Label lblPassword;
	private PasswordField txtVerificarPassword;
	private Label lblVerificarPassword;
	private String password;
	private Integer usuarioId;
	private String login;
	
	
	public CambiarPassword(String login1){
		login = login1;
		buildMainLayout();
		setContent(mainLayout);
		btnClick();
		
	}
	
	private Usuario buscarUsuarioLogin() {
		Usuario usuario = new Usuario();
		UsuarioService usuarioService = new UsuarioService();
		usuario = usuarioService.buscaLogin(login);
		return usuario;
	}
	
	
	
	private boolean validarDatos(){
		
		if (txtPassword.isEmpty()) {return false;}
		if (txtNuevoPassword.isEmpty()) {return false;}
		if (txtVerificarPassword.isEmpty()) {return false;}
		if (!(txtNuevoPassword.getValue().equals(txtVerificarPassword.getValue()))) {return false;}
		
		else {
			return true;
		}
	}
	
	private void btnClick() {
		btnguardar.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if (validarDatos()) {
						guardar();
				} else {
					MessageBox.createError()
					.withCaption("Error ")
					.withMessage("Datos incorrectos ")
					.open();
				}
				
			}
		});
		
		btncerrar.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				close();
				
			}
		});
	}
	
	private void guardar(){
		UsuarioService usuarioService = new UsuarioService();
		Encript encript = new Encript();
		Usuario usuario = buscarUsuarioLogin();
		if (usuario != null) { 
			try {
				if(usuario.getPassword().equals(encript.encriptString(txtPassword.getValue()))) {
					password = encript.encriptString(txtNuevoPassword.getValue());
					usuarioId = usuario.getUsuarioId();			
					usuarioService.updateClaveUsuario(usuarioId, password);
					
					MessageBox.createInfo()
					.withCaption("Actualizar Password")
					.withMessage("Password actualizado con exito!")
					.open();
				} else {
					MessageBox.createWarning()
					.withCaption("Error")
					.withMessage("Password antiguo incorrecto")
					.open();
				}
				
			}
			catch (Exception e) {
				MessageBox.createError()
				.withCaption("Error ")
				.withMessage("Error: "+e)
				.open();
				
			}
		} else {
			MessageBox.createError()
			.withCaption("Error ")
			.withMessage("Error: Login de usuario no existe ")
			.open();
		}
	}
	
	
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("450px");
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("500px");
		
		// gridLayout_1
		gridLayout_1 = buildGridLayout_1();
		mainLayout.addComponent(gridLayout_1, "top:70.0px;left:20.0px;");
		
		// lbltitulo
		lbltitulo = new Label();
		lbltitulo.setImmediate(false);
		lbltitulo.setWidth("-1px");
		lbltitulo.setHeight("-1px");
		lbltitulo.setValue("ACTUALIZAR PASSWORD");
		mainLayout.addComponent(lbltitulo, "top:12.0px;left:210.0px;");
		
		// horizontalLayout_4
		horizontalLayout_4 = buildHorizontalLayout_4();
		mainLayout.addComponent(horizontalLayout_4, "top:300.0px;left:38.0px;");
		
		return mainLayout;
	}
	
	private GridLayout buildGridLayout_1() {
		// common part: create layout
		gridLayout_1 = new GridLayout();
		gridLayout_1.setImmediate(false);
		gridLayout_1.setWidth("500px");
		gridLayout_1.setHeight("300px");
		gridLayout_1.setMargin(false);
		gridLayout_1.setColumns(2);
		gridLayout_1.setRows(8);
		
				
		
		// lblPassword
		lblPassword = new Label();
		lblPassword.setImmediate(false);
		lblPassword.setWidth("-1px");
		lblPassword.setHeight("-1px");
		lblPassword.setValue("Password Antiguo:");
		gridLayout_1.addComponent(lblPassword, 0, 0);
		
		// txtPassword
		txtPassword = new PasswordField();
		txtPassword.setImmediate(false);
		txtPassword.setWidth("307px");
		txtPassword.setHeight("-1px");
		txtPassword.setRequired(true);
		txtPassword.setRequiredError("Debe ingresar un password");
		gridLayout_1.addComponent(txtPassword, 1, 0);
		
		// lblNuevoPassword
		lblNuevoPassword = new Label();
		lblNuevoPassword.setImmediate(false);
		lblNuevoPassword.setWidth("-1px");
		lblNuevoPassword.setHeight("-1px");
		lblNuevoPassword.setValue("Nuevo Password:");
		gridLayout_1.addComponent(lblNuevoPassword, 0, 1);
		
		// txtNuevoPassword
		txtNuevoPassword = new PasswordField();
		txtNuevoPassword.setImmediate(false);
		txtNuevoPassword.setWidth("307px");
		txtNuevoPassword.setHeight("-1px");
		txtNuevoPassword.setRequired(true);
		gridLayout_1.addComponent(txtNuevoPassword, 1, 1);
		
		// lblVerificarPassword
		lblVerificarPassword = new Label();
		lblVerificarPassword.setImmediate(false);
		lblVerificarPassword.setWidth("-1px");
		lblVerificarPassword.setHeight("-1px");
		lblVerificarPassword.setValue("Repetir Password: ");
		gridLayout_1.addComponent(lblVerificarPassword, 0, 2);
		
		// textField_9
		txtVerificarPassword = new PasswordField();
		txtVerificarPassword.setImmediate(false);
		txtVerificarPassword.setWidth("307px");
		txtVerificarPassword.setHeight("-1px");
		txtVerificarPassword.setRequired(true);
		gridLayout_1.addComponent(txtVerificarPassword, 1, 2);
			
		
		return gridLayout_1;
	}
	
	private HorizontalLayout buildHorizontalLayout_4() {
		// common part: create layout
		horizontalLayout_4 = new HorizontalLayout();
		horizontalLayout_4.setImmediate(false);
		horizontalLayout_4.setWidth("510px");
		horizontalLayout_4.setHeight("50px");
		horizontalLayout_4.setMargin(false);
		
		// btnguardar
		btnguardar = new Button();
		btnguardar.setCaption("Guardar");
		btnguardar.setImmediate(true);
		btnguardar.setWidth("120px");
		btnguardar.setHeight("-1px");
		horizontalLayout_4.addComponent(btnguardar);
		horizontalLayout_4.setComponentAlignment(btnguardar, new Alignment(20));
		
		// btncerrar
		btncerrar = new Button();
		btncerrar.setCaption("Cerrar");
		btncerrar.setImmediate(true);
		btncerrar.setWidth("120px");
		btncerrar.setHeight("-1px");
		horizontalLayout_4.addComponent(btncerrar);
		horizontalLayout_4.setComponentAlignment(btncerrar, new Alignment(20));
		
		return horizontalLayout_4;
	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		
	}

}