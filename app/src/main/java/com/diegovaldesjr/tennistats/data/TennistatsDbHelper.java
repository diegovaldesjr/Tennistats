package com.diegovaldesjr.tennistats.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.diegovaldesjr.tennistats.model.Jugada;
import com.diegovaldesjr.tennistats.model.Jugador;
import com.diegovaldesjr.tennistats.model.Partido;
import com.diegovaldesjr.tennistats.model.Saque;
import com.diegovaldesjr.tennistats.model.Set;

/**
 * Created by diego on 30/12/2017.
 */

public class TennistatsDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Tennistats.db";

    public TennistatsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Comandos SQL
        db.execSQL("CREATE TABLE " + TennistatsContract.JugadorEntry.TABLE_NAME + " ("
                + TennistatsContract.JugadorEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TennistatsContract.JugadorEntry.ID_USUARIO + " TEXT NOT NULL,"
                + TennistatsContract.JugadorEntry.NOMBRE + " TEXT NOT NULL,"
                + TennistatsContract.JugadorEntry.APELLIDO + " TEXT NOT NULL,"
                + TennistatsContract.JugadorEntry.EDAD + " INTEGER NOT NULL,"
                + TennistatsContract.JugadorEntry.MANO_DIESTRA + " TEXT NOT NULL,"
                + TennistatsContract.JugadorEntry.GENERO + " TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + TennistatsContract.PartidoEntry.TABLE_NAME + " ("
                + TennistatsContract.PartidoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TennistatsContract.PartidoEntry.ID_JUGADOR + " INTEGER NOT NULL,"
                + TennistatsContract.PartidoEntry.ID_USUARIO + " TEXT NOT NULL,"
                + TennistatsContract.PartidoEntry.CATEGORIA + " TEXT NOT NULL,"
                + TennistatsContract.PartidoEntry.FECHA + " TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + TennistatsContract.SetEntry.TABLE_NAME + " ("
                + TennistatsContract.SetEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TennistatsContract.SetEntry.ID_PARTIDO + " INTEGER NOT NULL,"
                + TennistatsContract.SetEntry.NUMERO + " INTEGER NOT NULL,"
                + TennistatsContract.SetEntry.PUNTAJEJ + " INTEGER NOT NULL,"
                + TennistatsContract.SetEntry.PUNTAJEO + " INTEGER NOT NULL,"
                + TennistatsContract.SetEntry.GANADOR + " TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + TennistatsContract.JugadaEntry.TABLE_NAME + " ("
                + TennistatsContract.JugadaEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TennistatsContract.JugadaEntry.ID_SET + " INTEGER NOT NULL,"
                + TennistatsContract.JugadaEntry.INDICE + " INTEGER NOT NULL,"
                + TennistatsContract.JugadaEntry.TIPO_GOLPE + " TEXT NOT NULL,"
                + TennistatsContract.JugadaEntry.ZONA + " INTEGER NOT NULL,"
                + TennistatsContract.JugadaEntry.TIPO_JUGADA + " TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + TennistatsContract.SaqueEntry.TABLE_NAME + " ("
                + TennistatsContract.SaqueEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TennistatsContract.SaqueEntry.ID_SET + " INTEGER NOT NULL,"
                + TennistatsContract.SaqueEntry.INDICE + " INTEGER NOT NULL,"
                + TennistatsContract.SaqueEntry.TIPO_GOLPE + " TEXT NOT NULL,"
                + TennistatsContract.SaqueEntry.ZONA + " INTEGER NOT NULL,"
                + TennistatsContract.SaqueEntry.TIPO_SAQUE + " TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No hay operaciones
    }

    public long saveJugador(Jugador jugador) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                TennistatsContract.JugadorEntry.TABLE_NAME,
                null,
                jugador.jugadorToContentValues());

    }

    public int savePartido(Partido partido) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return (int) sqLiteDatabase.insert(
                TennistatsContract.PartidoEntry.TABLE_NAME,
                null,
                partido.partidoToContentValues());

    }

    public int saveSet(Set set) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return (int) sqLiteDatabase.insert(
                TennistatsContract.SetEntry.TABLE_NAME,
                null,
                set.setToContentValues());

    }

    public long saveJugada(Jugada jugada) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                TennistatsContract.JugadaEntry.TABLE_NAME,
                null,
                jugada.jugadaToContentValues());

    }

    public long saveSaque(Saque saque) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                TennistatsContract.SaqueEntry.TABLE_NAME,
                null,
                saque.saqueToContentValues());

    }

    public Cursor getAllJugadores(String idUsuario) {
        String query = "SELECT * FROM jugador WHERE idUsuario = '"+idUsuario+"'";

        return getReadableDatabase().rawQuery(query, null);
    }

    public Cursor getJugadorById(String jugadorId) {
        Cursor c = getReadableDatabase().query(
                TennistatsContract.JugadorEntry.TABLE_NAME,
                null,
                TennistatsContract.JugadorEntry._ID + " LIKE ?",
                new String[]{jugadorId},
                null,
                null,
                null);
        return c;
    }

    public Cursor getAllPartidos(String idUsuario) {
        String query = "SELECT partido.*, jugador.nombre, jugador.apellido, sets.* FROM ((partido INNER JOIN jugador " +
                "ON partido.idJugador = jugador."+TennistatsContract.JugadorEntry._ID+" AND partido.idUsuario = '"+idUsuario+"' AND " +
                "jugador.idUsuario = '"+idUsuario+"') " + "INNER JOIN sets ON partido."+TennistatsContract.PartidoEntry._ID+" = sets.idPartido)";

        return getReadableDatabase().rawQuery(query, null);
    }

    //Sin sets
    public Cursor getAllPartidos2() {
        String query = "SELECT partido.*, jugador.nombre, jugador.apellido FROM partido INNER JOIN jugador " +
                "ON partido.idJugador = jugador." + TennistatsContract.JugadorEntry._ID;

        return getReadableDatabase().rawQuery(query, null);
    }

    public Cursor getPartidosPorJugador(String id, String idUsuario) {
        String query = "SELECT partido.*, jugador.nombre, jugador.apellido, sets.* FROM ((partido INNER JOIN jugador " +
                "ON partido.idJugador = jugador."+TennistatsContract.JugadorEntry._ID+" AND jugador."+
                TennistatsContract.JugadorEntry._ID +" = "+id+" AND partido.idUsuario = '"+idUsuario+"' AND jugador.idUsuario = '"
                +idUsuario+"') " + "INNER JOIN sets ON partido."+ TennistatsContract.PartidoEntry._ID+" = sets.idPartido)";

        return getReadableDatabase().rawQuery(query, null);
    }

    public int updateSet(Set set, String id) {
        return getWritableDatabase().update(
                TennistatsContract.SetEntry.TABLE_NAME,
                set.setToContentValues(),
                TennistatsContract.SetEntry._ID + " LIKE ?",
                new String[]{id}
        );
    }

    public int deleteJugador(String idJugador) {
        return getWritableDatabase().delete(
                TennistatsContract.JugadorEntry.TABLE_NAME,
                TennistatsContract.JugadorEntry._ID + " LIKE ?",
                new String[]{idJugador});
    }

    public int deletePartido(String idPartido) {
        return getWritableDatabase().delete(
                TennistatsContract.PartidoEntry.TABLE_NAME,
                TennistatsContract.PartidoEntry._ID + " LIKE ?",
                new String[]{idPartido});
    }

    public Cursor getJugadasSet(int idSet) {
        String query = "SELECT * FROM jugada WHERE idSet = "+idSet;

        return getReadableDatabase().rawQuery(query, null);
    }

    public Cursor getSaquesSet(int idSet) {
        String query = "SELECT * FROM saque WHERE idSet = "+idSet;

        return getReadableDatabase().rawQuery(query, null);
    }
}
