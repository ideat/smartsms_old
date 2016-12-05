package com.mindware.services;

import com.mindware.domain.Contacto;
import com.mindware.mappers.ContactoMapper;
import com.mindware.util.MyBatisSqlSessionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by freddy on 03-08-16.
 */
public class ContactoService {

    public List<Contacto> findAllContactos() {
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
            ContactoMapper contactoMapper = sqlSession.getMapper(ContactoMapper.class);
            return contactoMapper.findAllContactos();
        }finally {
            sqlSession.close();
        }
    }

    public void updateContacto(Contacto contacto) {
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
            ContactoMapper contactoMapper = sqlSession.getMapper(ContactoMapper.class);
            contactoMapper.updateContacto(contacto);
            sqlSession.commit();
        }finally {
            sqlSession.close();
        }
    }

    public int verificarAsignacionContactoGrupo(int contactoId) {
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
            ContactoMapper contactoMapper = sqlSession.getMapper(ContactoMapper.class);
            return contactoMapper.verificarAsignacionContactoGrupo(contactoId);
        }finally {
            sqlSession.close();
        }
    }

    public int verificarContactoMensaje(int contactoId) {
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
            ContactoMapper contactoMapper = sqlSession.getMapper(ContactoMapper.class);
            return contactoMapper.verificarContactoMensaje(contactoId);
        }finally {
            sqlSession.close();
        }
    }

    public Contacto findContactoById(int contactoId) {
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
            ContactoMapper contactoMapper = sqlSession.getMapper(ContactoMapper.class);
            return contactoMapper.findContactoById(contactoId);

        } finally {
            sqlSession.close();
        }
    }

    public List<Contacto> findContactoByUser(int usuarioId) {
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
            ContactoMapper contactoMapper = sqlSession.getMapper(ContactoMapper.class);

            return contactoMapper.findContactoByUser(usuarioId);

        } finally {
            sqlSession.close();
        }
    }

    public List<Contacto> findAvailableContactoByGroup(int grupoId, Date fechaImportacion) {
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
            ContactoMapper contactoMapper = sqlSession.getMapper(ContactoMapper.class);

            return contactoMapper.findAvailableContactoByGroup(grupoId, fechaImportacion);

        } finally {
            sqlSession.close();
        }
    }

    public List<Contacto> findAsignedContactoByGroup(int grupoId) {
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
            ContactoMapper contactoMapper = sqlSession.getMapper(ContactoMapper.class);

            return contactoMapper.findAsignedContactoByGroup(grupoId);

        } finally {
            sqlSession.close();
        }
    }

    public List<Contacto> findAvailableContactoActivo(int grupo, Date fechaImportacion) {
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
            ContactoMapper contactoMapper = sqlSession.getMapper(ContactoMapper.class);
            return contactoMapper.findAvailableContactoActivo(grupo, fechaImportacion);
        } finally {
            sqlSession.close();
        }
    }

    public void insertaContacto(Contacto contacto) {
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
            try {
                ContactoMapper contactoMapper = sqlSession.getMapper(ContactoMapper.class);
                contactoMapper.insertContacto(contacto);
                sqlSession.commit();
            } finally {
                sqlSession.close();
            }
    }

    public void insertarContactos(List<Contacto> contactos) {

        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
            ContactoMapper contactoMapper = sqlSession.getMapper(ContactoMapper.class);
//            contactos.parallelStream().forEach(contacto -> contactoMapper.insertContacto(contacto));
            for(Contacto contacto:contactos) {
                contactoMapper.insertContacto(contacto);
            }
            sqlSession.commit();

        } finally {
            sqlSession.close();
        }
    }

    public boolean deleteContacto(int contacto_id) {
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
            ContactoMapper mapper = sqlSession.getMapper(ContactoMapper.class);
            int count = mapper.deleteContacto(contacto_id);
            sqlSession.commit();
            return count > 0;
        }
        catch (Exception e) {
            sqlSession.rollback();
            e.printStackTrace();
            throw new RuntimeException(e.getCause());
        }
        finally {
            sqlSession.close();
        }

    }

    public ArrayList<Contacto> contactSelected(int usuarioId, List<String> celulares) {
        ArrayList<Contacto> listCell = new ArrayList<>();
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
            ContactoMapper mapper = sqlSession.getMapper(ContactoMapper.class);
            listCell = mapper.contactSelected(usuarioId,celulares);
            sqlSession.commit();
            return listCell;
        }
        catch (Exception e) {
            sqlSession.rollback();
            e.printStackTrace();
            throw new RuntimeException(e.getCause());
        }
        finally {
            sqlSession.close();
        }

    }


    public ArrayList<Contacto> contactByGroup(int grupoId) {
        ArrayList<Contacto> listContacto = new ArrayList<>();
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
            ContactoMapper mapper = sqlSession.getMapper(ContactoMapper.class);
            listContacto = mapper.contactByGroup(grupoId);
            sqlSession.commit();
            return listContacto;
        }
        catch (Exception e) {
            sqlSession.rollback();
            e.printStackTrace();
            throw new RuntimeException(e.getCause());
        }
        finally {
            sqlSession.close();
        }

    }

    public void bajaContactos(int usuarioId) {
    	SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
            ContactoMapper contactoMapper = sqlSession.getMapper(ContactoMapper.class);
            contactoMapper.bajaContactos(usuarioId);
            sqlSession.commit();

        } finally {
            sqlSession.close();
        }
    }




}
