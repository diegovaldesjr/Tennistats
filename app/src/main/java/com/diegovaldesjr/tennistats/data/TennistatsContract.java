package com.diegovaldesjr.tennistats.data;

import android.provider.BaseColumns;

/**
 * Created by diego on 30/12/2017.
 */

public class TennistatsContract {

    public static abstract class JugadorEntry implements BaseColumns {
        public static final String TABLE_NAME ="jugador";

        public static final String ID_USUARIO = "idUsuario";
        public static final String NOMBRE = "nombre";
        public static final String APELLIDO = "apellido";
        public static final String EDAD = "edad";
        public static final String MANO_DIESTRA = "manoDiestra";
        public static final String GENERO = "genero";
    }

    public static abstract class PartidoEntry implements BaseColumns {
        public static final String TABLE_NAME ="partido";

        public static final String ID_JUGADOR = "idJugador";
        public static final String ID_USUARIO = "idUsuario";
        public static final String CATEGORIA = "categoria";
        public static final String FECHA = "fecha";
    }

    public static abstract class SetEntry implements BaseColumns {
        public static final String TABLE_NAME ="sets";

        public static final String ID_PARTIDO = "idPartido";
        public static final String NUMERO = "numero";
        public static final String PUNTAJEJ = "puntajej";
        public static final String PUNTAJEO = "puntajeo";
        public static final String GANADOR = "ganador";
    }

    public static abstract class JugadaEntry implements BaseColumns {
        public static final String TABLE_NAME ="jugada";

        public static final String INDICE = "indice";
        public static final String ID_SET = "idSet";
        public static final String TIPO_GOLPE = "tipoGolpe";
        public static final String TIPO_JUGADA = "tipoJugada";
    }

    public static abstract class SaqueEntry implements BaseColumns {
        public static final String TABLE_NAME ="saque";

        public static final String INDICE = "indice";
        public static final String ID_SET = "idSet";
        public static final String TIPO_GOLPE = "tipoGolpe";
        public static final String TIPO_SAQUE = "tipoSaque";
    }
}
