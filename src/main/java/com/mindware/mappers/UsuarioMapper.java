package com.mindware.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mindware.domain.Usuario;

public interface UsuarioMapper {
	
List<Usuario> listaUsuarios(String estado);
Usuario buscaLogin(String login);
void insertUsuario(Usuario usuario);
void updateUsuario(Usuario usuario);
void updateClaveUsuario(@Param("usuarioId")int usuarioId, @Param("password") String password);

} 
