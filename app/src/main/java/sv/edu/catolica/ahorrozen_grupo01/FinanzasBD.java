package sv.edu.catolica.ahorrozen_grupo01;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class FinanzasBD extends SQLiteOpenHelper {

    public FinanzasBD(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Usuarios (" +
                "idUsuario INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "correo TEXT UNIQUE NOT NULL, " +
                "contrasena TEXT NOT NULL)");

        db.execSQL("CREATE TABLE Categoria (" +
                "idCategoria INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL)");

        db.execSQL("CREATE TABLE MetodoPago (" +
                "idMetodoPago INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "metodo TEXT NOT NULL)");

        db.execSQL("CREATE TABLE Saldo (" +
                "idSaldo INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idUsuario INTEGER NOT NULL, " +
                "saldoTotal REAL NOT NULL, " +
                "fechaActualizacion DATE NOT NULL, " +
                "FOREIGN KEY (idUsuario) REFERENCES Usuario(idUsuario))");

        db.execSQL("CREATE TABLE Ingreso (" +
                "idIngreso INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idUsuario INTEGER NOT NULL, " +
                "idCategoria INTEGER NOT NULL, " +
                "idMetodoPago INTEGER NOT NULL, " +
                "monto REAL NOT NULL, " +
                "fecha DATE NOT NULL, " +
                "FOREIGN KEY (idUsuario) REFERENCES Usuario(idUsuario), " +
                "FOREIGN KEY (idCategoria) REFERENCES Categoria(idCategoria), " +
                "FOREIGN KEY (idMetodoPago) REFERENCES MetodoPago(idMetodoPago))");

        db.execSQL("CREATE TABLE Egreso (" +
                "idEgreso INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idUsuario INTEGER NOT NULL, " +
                "idCategoria INTEGER NOT NULL, " +
                "idMetodoPago INTEGER NOT NULL, " +
                "monto REAL NOT NULL, " +
                "fecha DATE NOT NULL, " +
                "FOREIGN KEY (idUsuario) REFERENCES Usuario(idUsuario), " +
                "FOREIGN KEY (idCategoria) REFERENCES Categoria(idCategoria), " +
                "FOREIGN KEY (idMetodoPago) REFERENCES MetodoPago(idMetodoPago))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Egreso");
        db.execSQL("DROP TABLE IF EXISTS Ingreso");
        db.execSQL("DROP TABLE IF EXISTS Saldo");
        db.execSQL("DROP TABLE IF EXISTS MetodoPago");
        db.execSQL("DROP TABLE IF EXISTS Categoria");
        db.execSQL("DROP TABLE IF EXISTS Usuario");
        onCreate(db);
    }
}
