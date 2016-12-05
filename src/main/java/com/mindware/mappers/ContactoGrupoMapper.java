package com.mindware.mappers;

import com.mindware.domain.ContactoGrupo;

/**
 * Created by freddy on 21-09-16.
 */
public interface ContactoGrupoMapper {
    void deleteContactoGrupo(int contactoId, int grupoId);
    void deleteContactosGrupo(int grupoId);
    void insertContactoGrupo(ContactoGrupo contactoGrupoMapper);

}
