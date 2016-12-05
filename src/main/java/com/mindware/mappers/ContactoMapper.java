package com.mindware.mappers;

import com.mindware.domain.Contacto;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface ContactoMapper {
	List<Contacto> findAllContactos();
	void insertContacto(Contacto contacto);
	void updateContacto(Contacto contacto);
	Contacto findContactoById(int id);
	List<Contacto> findContactoByUser(int usuarioId);
	List<Contacto> findAvailableContactoByGroup(@Param("grupoId") int grupoId,
												@Param("fechaImportacion") Date fechaImportacion);
	List<Contacto> findAsignedContactoByGroup(int grupoId);

	List<Contacto> findAvailableContactoActivo(@Param("grupoId") int grupoId,
											   @Param("fechaImportacion") Date fechaImportacion);

	int verificarAsignacionContactoGrupo(int contactoId);
	int verificarContactoMensaje(int contactoId);


	ArrayList<Contacto> contactSelected(@Param("usuarioId") int usuarioId, @Param("list") List<String> celulares);
	ArrayList<Contacto> contactByGroup(int grupoId);
	void bajaContactos(int usuarioId);
	int deleteContacto(int id);

}
