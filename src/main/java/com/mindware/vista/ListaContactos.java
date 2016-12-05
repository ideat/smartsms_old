package com.mindware.vista;

import com.mindware.domain.Contacto;
import com.mindware.services.ContactoService;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MultiSelectMode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import de.steinwedel.messagebox.MessageBox;

import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("serial")
public class ListaContactos extends CustomComponent{
	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private HorizontalLayout horizontalLayout_1;
	@AutoGenerated
	private Table tbl_contactos;
	@AutoGenerated
	private GridLayout gridLayout_1;
	@AutoGenerated
	private NativeButton btnEditar;
	@AutoGenerated
	private NativeButton btnImportar;
	@AutoGenerated
	private NativeButton btnNuevoContacto;
	private NativeButton btnEliminarContacto;
	private Label titulo;
	private ContactoService contactoService;
	private NativeButton btnComponer;
	private ArrayList<String> listaCelulares;
	private ArrayList<Integer> listaContactoId;
	private Contacto contactoEdit;
	private int usuarioId;
	

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public ListaContactos(int id)  {
		usuarioId = id;
		buildMainLayout();
		setCompositionRoot(mainLayout);
		contactoService = new ContactoService();

        btnNuevoContacto.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				AddContacto addContacto = new AddContacto();
				addContacto.setModal(true);
				addContacto.setWidth("550px");
				addContacto.setHeight("520px");
				addContacto.center();
				addContacto.setUsuarioId(usuarioId);
				UI.getCurrent().addWindow(addContacto);

				addContacto.addCloseListener(new Window.CloseListener() {
					@Override
					public void windowClose(Window.CloseEvent e) {
						llenarContactos(contactoService.findContactoByUser(usuarioId)); //TODO reemplazar 1 por el codigo de logeo del usuario
					}
				});

			}
		});

		btnEditar.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				listaCelulares.clear(); //Limpia celulares seleccionados

				EditContacto editContacto = new EditContacto(contactoEdit);
				editContacto.setModal(true);
				editContacto.setWidth("550px");
				editContacto.setHeight("520px");
				editContacto.center();
				UI.getCurrent().addWindow(editContacto);

				editContacto.addCloseListener(new Window.CloseListener() {
					@Override
					public void windowClose(Window.CloseEvent e) {
						llenarContactos(contactoService.findContactoByUser(usuarioId)); //TODO reemplazar 1 por el codigo de logeo del usuario
					}
				});

			}
		});


		btnImportar.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				CargarArchivo cargarArchivo = new CargarArchivo(usuarioId);
				cargarArchivo.setModal(true);
				cargarArchivo.setWidth("700px");
				cargarArchivo.setHeight("700px");
				cargarArchivo.center();
				cargarArchivo.setUsuarioId(usuarioId);
				
				UI.getCurrent().addWindow(cargarArchivo);

				cargarArchivo.addCloseListener(new Window.CloseListener() {
					@Override
					public void windowClose(Window.CloseEvent e) {
						mostrarContactos();
					}
				});
				
			}
		});

		btnComponer.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (!listaCelulares.isEmpty()) {
					Window subWindowComponer = new Window("Componer Mensaje");
					Componer componerMensaje = new Componer(usuarioId);
					componerMensaje.numeroCelulares(celulares(listaCelulares));
					subWindowComponer.setContent(componerMensaje);
					subWindowComponer.center();
					subWindowComponer.setModal(true);
					subWindowComponer.setWidth("700px");
					UI.getCurrent().addWindow(subWindowComponer);

					subWindowComponer.addCloseListener(new Window.CloseListener() {
						@Override
						public void windowClose(Window.CloseEvent e) {
							listaCelulares.clear();
						}
					});
				} else {
					MessageBox.createWarning()
							.withCaption("Contactos")
							.withMessage("Seleccione contactos")
							.open();
				}
			}
		});

		//Llenar contactos
		mostrarContactos();
		seleccionarCelulares();
		deleteContacto();

	}
	
	private void mostrarContactos(){

		llenarContactos(contactoService.findContactoByUser(usuarioId));
	}

	private void deleteContacto() {
		btnEliminarContacto.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				contactoService = new ContactoService();
				String contactoNoEliminados = "ID: ";

				for (Object c : listaContactoId) {

					if ((contactoService.verificarAsignacionContactoGrupo(Integer.parseInt(c.toString()))==0)
							&& (contactoService.verificarContactoMensaje(Integer.parseInt(c.toString()))==0)){
						contactoService.deleteContacto(Integer.parseInt(c.toString()));
					} else {
						String id = c.toString();
						contactoNoEliminados = contactoNoEliminados + id + ",";
					}

				}
				if (listaContactoId.size() > 0) {
					Notification.show("Los contactos con " + contactoNoEliminados + " tienen grupo o mensajes asociados y No fueron eliminados");
					listaContactoId.clear();
					llenarContactos(contactoService.findContactoByUser(usuarioId));
				}

			}
		});
	}

	private void llenarContactos(List<Contacto> contactos) {
		if (contactos.size() >0) {

			IndexedContainer containerContacto = new IndexedContainer();

			containerContacto.addContainerProperty("ID", Integer.class, "");
			containerContacto.addContainerProperty("Celular", String.class, "");
			containerContacto.addContainerProperty("Contacto", String.class, "");
			containerContacto.addContainerProperty("Campo1", String.class, "");
			containerContacto.addContainerProperty("Campo2", String.class, "");
			containerContacto.addContainerProperty("Campo3", String.class, "");

			for (Contacto contacto : contactos) {
				Item item = containerContacto.addItem(contacto);
				item.getItemProperty("ID").setValue(contacto.getContactoId());
				item.getItemProperty("Celular").setValue(contacto.getCelular());
				item.getItemProperty("Contacto").setValue(contacto.getNombreContacto());
				item.getItemProperty("Campo1").setValue(contacto.getCampo1());
				item.getItemProperty("Campo2").setValue(contacto.getCampo2());
				item.getItemProperty("Campo3").setValue(contacto.getCampo3());

			}
			tbl_contactos.setContainerDataSource(containerContacto);
		}
	}

	public void seleccionarCelulares() {
		listaCelulares = new ArrayList<String>(); //inicializa lista celulares seleccionados
		listaContactoId = new ArrayList<>();
		tbl_contactos.addItemClickListener(new ItemClickEvent.ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent event) {

				Object value = event.getItemId();
				contactoEditar(value);

				Notification.show("Celular: " + (String)tbl_contactos.getItem(value).getItemProperty("Celular").getValue());
				celularesSeleccionados((String)tbl_contactos.getItem(value).getItemProperty("Celular").getValue());
				contactoSeleccionados((Integer) tbl_contactos.getItem(value).getItemProperty("ID").getValue());
			}
		});
	}

	private void contactoEditar(Object fila) {
		contactoEdit = new Contacto();
		contactoEdit.setCelular(tbl_contactos.getItem(fila).getItemProperty("Celular").getValue().toString());
		contactoEdit.setContactoId((Integer) tbl_contactos.getItem(fila).getItemProperty("ID").getValue());
		contactoEdit.setNombreContacto(tbl_contactos.getItem(fila).getItemProperty("Contacto").getValue().toString());
		contactoEdit.setCampo1(tbl_contactos.getItem(fila).getItemProperty("Campo1").getValue().toString());
		contactoEdit.setCampo2(tbl_contactos.getItem(fila).getItemProperty("Campo2").getValue().toString());
		contactoEdit.setCampo3(tbl_contactos.getItem(fila).getItemProperty("Campo3").getValue().toString());

	}

	public void celularesSeleccionados(String celular){
		listaCelulares.add(celular);
		Object[] st = listaCelulares.toArray();
		for (Object s : st) {
			if (listaCelulares.indexOf(s) != listaCelulares.lastIndexOf(s)) {
				listaCelulares.remove(listaCelulares.lastIndexOf(s));
				listaCelulares.remove(listaCelulares.lastIndexOf(s));
			}
		}
	}

	public void contactoSeleccionados(int contactoId){
		listaContactoId.add(contactoId);
		Object[] st = listaContactoId.toArray();
		for (Object s : st) {
			if (listaContactoId.indexOf(s) != listaContactoId.lastIndexOf(s)) {
				listaContactoId.remove(listaContactoId.lastIndexOf(s));
				listaContactoId.remove(listaContactoId.lastIndexOf(s));
			}
		}
	}


	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		//titulo = new Label("Contactos");
        // titulo
        titulo = new Label();
        titulo.setImmediate(false);
        titulo.setWidth("560px");
        titulo.setHeight("60px");
        titulo.setValue("Contactos");
        titulo.setContentMode(ContentMode.HTML);
        titulo.setStyleName("titulo");
		mainLayout.addComponent(titulo);
		// gridLayout_1
		gridLayout_1 = buildGridLayout_1();
		mainLayout.addComponent(gridLayout_1, "top:51.0px;right:13.0px;left:9.0px;");
		
		// horizontalLayout_1
		horizontalLayout_1 = buildHorizontalLayout_1();
		mainLayout.addComponent(horizontalLayout_1, "top:89.0px;left:9.0px;");
		
		return mainLayout;
	}

	@AutoGenerated
	private GridLayout buildGridLayout_1() {

		// common part: create layout
		gridLayout_1 = new GridLayout();
		gridLayout_1.setImmediate(false);
		gridLayout_1.setWidth("100.0%");
		gridLayout_1.setHeight("50px");
		gridLayout_1.setMargin(false);
		gridLayout_1.setColumns(5);
		gridLayout_1.setRows(2);



		// btnNuevoContacto
		btnNuevoContacto = new NativeButton();
		btnNuevoContacto.setCaption("Nuevo");
		btnNuevoContacto.setImmediate(true);
		btnNuevoContacto.setWidth("160px");
		btnNuevoContacto.setHeight("37px");
		btnNuevoContacto.setIcon(new ThemeResource("../images/buttons/card-contact.png"));
		//btnNuevoContacto.setIcon(FontAwesome.USER_PLUS);
		gridLayout_1.addComponent(btnNuevoContacto, 0, 0);
		
		// btnImportar
		btnImportar = new NativeButton();
		btnImportar.setCaption("Importar");
		btnImportar.setImmediate(true);
		btnImportar.setWidth("160px");
		btnImportar.setHeight("37px");
		btnImportar.setIcon(new ThemeResource("../images/buttons/order_incoming_24_h.png"));
		gridLayout_1.addComponent(btnImportar, 1, 0);
		
		// btnEditar
		btnEditar = new NativeButton();
		btnEditar.setCaption("Editar");
		btnEditar.setImmediate(true);
		btnEditar.setWidth("160px");
		btnEditar.setHeight("37px");
		btnEditar.setIcon(new ThemeResource("../images/buttons/edit.png"));
		gridLayout_1.addComponent(btnEditar, 2, 0);

		// btnComponer
		btnComponer = new NativeButton();
		btnComponer.setCaption("Componer Msje");
		btnComponer.setImmediate(true);
		btnComponer.setWidth("160px");
		btnComponer.setHeight("37px");
		btnComponer.setIcon(new ThemeResource("../images/buttons/mail_a_24_h.png"));
		gridLayout_1.addComponent(btnComponer, 3, 0);

		// btnEliminar
		btnEliminarContacto = new NativeButton();
		btnEliminarContacto.setCaption("Eliminar");
		btnEliminarContacto.setImmediate(true);
		btnEliminarContacto.setWidth("160px");
		btnEliminarContacto.setHeight("37px");
		btnEliminarContacto.setIcon(new ThemeResource("../images/buttons/delete_24_h.png"));
		gridLayout_1.addComponent(btnEliminarContacto, 4, 0);

		return gridLayout_1;
	}

	@AutoGenerated
	private HorizontalLayout buildHorizontalLayout_1() {
		// common part: create layout
		horizontalLayout_1 = new HorizontalLayout();
		horizontalLayout_1.setImmediate(false);
		horizontalLayout_1.setWidth("100.0%");
		horizontalLayout_1.setHeight("452px");
		horizontalLayout_1.setMargin(false);
		
		// tbl_contactos
		tbl_contactos = new Table();
		tbl_contactos.setImmediate(false);
		tbl_contactos.setWidth("100.0%");
		tbl_contactos.setHeight("100.0%");
		tbl_contactos.setVisible(true);
		tbl_contactos.setSelectable(true);
		tbl_contactos.setMultiSelectMode(MultiSelectMode.SIMPLE);
		tbl_contactos.setMultiSelect(true);

		horizontalLayout_1.addComponent(tbl_contactos);

		return horizontalLayout_1;
	}

	private String celulares(ArrayList lista) {
		String lstcell = new String();
		for(int i=0; i <  lista.size();i++) {
			lstcell = lstcell + "," + lista.get(i).toString();

		}

		return lstcell.substring(1);
	}
	

}
