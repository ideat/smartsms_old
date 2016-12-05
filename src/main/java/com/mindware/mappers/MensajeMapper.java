package com.mindware.mappers;

import com.mindware.domain.Mensaje;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by freddy on 18-09-16.
 */
public interface MensajeMapper {
    void insertarMensaje(Mensaje mensaje);
    List<Mensaje> findMensajesUsuario(@Param("usuarioId") int usuarioId,
                                         @Param("estado") String estado);
    List<Mensaje> findMensajeNoEnviado( String estado);
    List<Mensaje> findMensajeNoEntregados(String status);
    void updateMensaje(Mensaje mensaje);
}
