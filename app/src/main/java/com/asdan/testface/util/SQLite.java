package com.asdan.testface.util;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLite extends SQLiteOpenHelper {

	public SQLite(Context contexto, String nombre, CursorFactory factory,
			int version) {
		super(contexto, nombre, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			for (Consulta a : Consulta.values()) {
				db.execSQL(a.getConsulta());
			}
		} catch (SQLException ex) {
			Log.e("SQLite.onCreate", ".." + ex.getMessage());
		} catch (Exception ex) {
			Log.e("SQLite.onCreate", "." + ex.getMessage());
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
