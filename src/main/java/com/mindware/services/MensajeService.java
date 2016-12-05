package com.mindware.services;

import com.mindware.domain.Contacto;
import com.mindware.domain.Mensaje;
import com.mindware.mappers.MensajeMapper;
import com.mindware.util.MyBatisSqlSessionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by freddy on 18-09-16.
 */
public class MensajeService {

    public List<Mensaje> prepararMensajes(String plantilla, List<Contacto> contactos, String grupo, int grupoId, int usuarioId) {
        List<Mensaje> listaMensaje = new ArrayList<>();
        //java.util.Date fecha = new Date();

        for(Contacto contacto:contactos) {
            //Obtiene valores de los campo
            Mensaje mensajeObj = new Mensaje();
            String mensaje = crearMensaje(plantilla,contacto);
            Integer longitud = mensaje.length();
            mensajeObj.setCelular(contacto.getCelular());
            mensajeObj.setEnviado("F"); //Inicializa en falso
            mensajeObj.setFechaEnvio(null);
            mensajeObj.setGrupo(grupo);
            mensajeObj.setHoraEnvio(null);
            mensajeObj.setNombre(contacto.getNombreContacto());
            mensajeObj.setMensaje(mensaje);
            mensajeObj.setContactoId(contacto.getContactoId());
            mensajeObj.setLongitudSms(longitud);
            mensajeObj.setGrupoId(grupoId);
            mensajeObj.setUsuarioId(usuarioId);
            mensajeObj.setNumeroIntentos(0);

            listaMensaje.add(mensajeObj);
        }
        return listaMensaje;

    }

    public String crearMensaje(String plantilla, Contacto contacto) {
        String mensaje = plantilla;
        String nombre = contacto.getNombreContacto();
        String campo1 = contacto.getCampo1();
        String campo2 = contacto.getCampo2();
        String campo3 = contacto.getCampo3();

        mensaje = mensaje.replace("#nombre#",nombre);
        mensaje = mensaje.replace("#campo1#",campo1);
        mensaje = mensaje.replace("#campo2#",campo2);
        mensaje = mensaje.replace("#campo3#",campo3);

        mensaje.trim();
        return mensaje;
    }

    public void insertarMensaje(List<Mensaje> mensajes) {
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
            MensajeMapper mensajeMapper = sqlSession.getMapper(MensajeMapper.class);
            for(Mensaje mensaje:mensajes) {
                mensajeMapper.insertarMensaje(mensaje);
            }
            sqlSession.commit();

        } finally {
            sqlSession.close();
        }
    }

    public List<Mensaje> findMensajesUsuario(int usuarioId, String estado) {
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
            MensajeMapper mensajeMapper = sqlSession.getMapper(MensajeMapper.class);

            return mensajeMapper.findMensajesUsuario(usuarioId,estado);

        } finally {
            sqlSession.close();
        }
    }
    
    public List<Mensaje> findMensajeNoEnviado(String estado) {
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
            MensajeMapper mensajeMapper = sqlSession.getMapper(MensajeMapper.class);
           
            return mensajeMapper.findMensajeNoEnviado(estado);

        } finally {
            sqlSession.close();
        }
    }
    
    public List<Mensaje> findMensajeNoEntregados(String status) {
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
            MensajeMapper mensajeMapper = sqlSession.getMapper(MensajeMapper.class);
           
            return mensajeMapper.findMensajeNoEntregados(status);

        } finally {
            sqlSession.close();
        }
    }

    public void updateListaMensajes(List<Mensaje> listaMensajes){
    	SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
            MensajeMapper mensajeMapper = sqlSession.getMapper(MensajeMapper.class);
            for (Mensaje mensaje:listaMensajes) {
            	mensajeMapper.updateMensaje(mensaje);
            }
            sqlSession.commit();

        } finally {
            sqlSession.close();
        }

    }
    
    public void updateMensaje(Mensaje mensaje){
    	SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
            MensajeMapper mensajeMapper = sqlSession.getMapper(MensajeMapper.class);
            mensajeMapper.updateMensaje(mensaje);
            sqlSession.commit();

        } finally {
            sqlSession.close();
        }

    }

}
