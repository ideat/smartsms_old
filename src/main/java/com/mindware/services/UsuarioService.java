package com.mindware.services;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.mindware.domain.Usuario;
import com.mindware.mappers.UsuarioMapper;
import com.mindware.util.MyBatisSqlSessionFactory;

public class UsuarioService {
	public List<Usuario> listaUsuarios(String estado) {
		 SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
	        try {
	            UsuarioMapper usuarioMapper = sqlSession.getMapper(UsuarioMapper.class);
	            return usuarioMapper.listaUsuarios(estado);
	        }
	        finally {
	            sqlSession.close();
	        }
		
		
	}
	
	public Usuario buscaLogin(String login) {
		 SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
	        try {
	            UsuarioMapper usuarioMapper = sqlSession.getMapper(UsuarioMapper.class);
	            return usuarioMapper.buscaLogin(login);
	        }
	        finally {
	            sqlSession.close();
	        }
		
		
	}
	
	public void insertUsuario(Usuario usuario) {
		SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
            UsuarioMapper usuarioMapper = sqlSession.getMapper(UsuarioMapper.class);
            usuarioMapper.insertUsuario(usuario);
            sqlSession.commit();
        }
        finally {
            sqlSession.close();
        }
	
		
	}
	
	public void updateUsuario(Usuario usuario) {
		SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
            UsuarioMapper usuarioMapper = sqlSession.getMapper(UsuarioMapper.class);
            usuarioMapper.updateUsuario(usuario);
            sqlSession.commit();
        }
        finally {
            sqlSession.close();
        }
	
		
	}
	
	public void updateClaveUsuario(int usuarioId, String password) {
		SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
            UsuarioMapper usuarioMapper = sqlSession.getMapper(UsuarioMapper.class);
            usuarioMapper.updateClaveUsuario(usuarioId, password);
            sqlSession.commit();
        }
        finally {
            sqlSession.close();
        }
	
		
	}
	

}
