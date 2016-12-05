package com.mindware.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by freddy on 19-08-16.
 */
public class Tools {
    public  static Boolean ValidarTelefono(String numero) {


        if (numero.length()!= 8) {
            return false;
        }
        else  if (!numero.matches("(6|7).*")) {
            return false;
        }
        else  if (EsNumero(numero)) {
            return true;
        } else return false;


    }

    public static Boolean EsNumero(String numero){
        try {
            Integer.parseInt(numero);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }

    private String getActualTime(){
        Date dtFechaActual = new Date ();
        DateFormat dfLocal = new SimpleDateFormat("HH:mm:ss");

        return dfLocal.format(dtFechaActual);
    }
}
