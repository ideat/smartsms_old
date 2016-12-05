package com.mindware.mappers;

import com.mindware.domain.Contacto;
import com.mindware.domain.Grupo;

import java.util.List;

/**
 * Created by freddy on 20-08-16.
 */
public interface GrupoMapper {
    List<Grupo> getGruposUsuario(int usuarioId);
    List<Grupo> getGrupoMiembros(int usuarioId);
    List<Grupo> getMiembrosGrupo();
    List<Contacto> getContactosGrupo(int grupoId);
    void insertGrupo(Grupo grupo);
    void updateGrupo(Grupo grupo);
    int deleteGrupo(int grupoId);
    void bajaGrupo(int usuarioId);

}
