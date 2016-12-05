package com.mindware.services;

import com.mindware.domain.Contacto;
import com.mindware.domain.Grupo;
import com.mindware.mappers.GrupoMapper;
import com.mindware.util.MyBatisSqlSessionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * Created by freddy on 29-08-16.
 */
public class GrupoService {
    public List<Grupo> getGrupoUsuario(int usuarioId) {
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
            GrupoMapper grupoMapper = sqlSession.getMapper(GrupoMapper.class);
            return grupoMapper.getGruposUsuario(usuarioId);
        }
        finally {
            sqlSession.close();
        }

    }

    public List<Grupo> getGrupoMiembros (int usuarioId) {
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
            GrupoMapper grupoMapper = sqlSession.getMapper(GrupoMapper.class);
            return  grupoMapper.getGrupoMiembros(usuarioId);
        }
        finally {
            sqlSession.close();
        }
    }

    public List<Contacto> getContactosGrupo(int grupoId){
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
            GrupoMapper grupoMapper= sqlSession.getMapper(GrupoMapper.class);
            return grupoMapper.getContactosGrupo(grupoId);
        }
        finally {
            sqlSession.close();
        }
    }


    public Grupo insertGrupo(Grupo grupo) {
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();

        try {
            GrupoMapper grupoMapper = sqlSession.getMapper(GrupoMapper.class);
            grupoMapper.insertGrupo(grupo);
            sqlSession.commit();
        }
        finally {
            sqlSession.close();
        }

        return grupo;
    }

    public void deleteGrupo (int grupoId) {
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
            GrupoMapper grupoMapper = sqlSession.getMapper(GrupoMapper.class);
            grupoMapper.deleteGrupo(grupoId);
            sqlSession.commit();
        }
        finally {
            sqlSession.close();
        }

    }

    public void updateGrupo(Grupo grupo) {
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
            GrupoMapper grupoMapper = sqlSession.getMapper(GrupoMapper.class);
            grupoMapper.updateGrupo(grupo);
            sqlSession.commit();
        }
        finally {
            sqlSession.close();
        }
    }
    
    public void bajaGrupo(int usuarioId) {
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
            GrupoMapper grupoMapper = sqlSession.getMapper(GrupoMapper.class);
            grupoMapper.bajaGrupo(usuarioId);
            sqlSession.commit();
        }
        finally {
            sqlSession.close();
        }
    }
}
