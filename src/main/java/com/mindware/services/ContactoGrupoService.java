package com.mindware.services;

import com.mindware.domain.ContactoGrupo;
import com.mindware.mappers.ContactoGrupoMapper;
import com.mindware.util.MyBatisSqlSessionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * Created by freddy on 24-09-16.
 */
public class ContactoGrupoService {

    public void deleteContactoGrupo(int contactoId, int grupoId) {
    SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
    try {
        ContactoGrupoMapper contactoGrupoMapper = sqlSession.getMapper(ContactoGrupoMapper.class);
        contactoGrupoMapper.deleteContactoGrupo(contactoId,grupoId);
        sqlSession.commit();
    } finally {
        sqlSession.close();
    }
    }

    public void deleteContactosGrupo(int grupoId) {
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
            ContactoGrupoMapper contactoGrupoMapper = sqlSession.getMapper(ContactoGrupoMapper.class);
            contactoGrupoMapper.deleteContactosGrupo(grupoId);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    public void insertContactoGrupo(List<ContactoGrupo> contactoGrupoList) {
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
            ContactoGrupoMapper contactoGrupoMapper = sqlSession.getMapper(ContactoGrupoMapper.class);
            for (ContactoGrupo contactoGrupo : contactoGrupoList) {
                contactoGrupoMapper.insertContactoGrupo(contactoGrupo);
            }

            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

}
