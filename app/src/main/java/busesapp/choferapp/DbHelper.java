package busesapp.choferapp;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Juan JP Zamorano on 22-06-2016.
 */
public class DbHelper extends SQLiteOpenHelper {

    //private static final String DB_NAME = "tesisbd.sqlite";
    private static final int DB_SCHEME_VERSION = 1;
    private static String DB_PATH = "/data/data/busesapp.choferapp/databases/";
    private static String DB_NAME = "Tesisbd";
    protected SQLiteDatabase db;
    private final Context context;

    public DbHelper(Context context) {

        super(context, DB_NAME, null, DB_SCHEME_VERSION);
        this.context = context;
        try {
            createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
            // Si existe, no haemos nada!
        } else {
            // Llamando a este método se crea la base de datos vacía en la ruta
            // por defecto del sistema de nuestra aplicación por lo que
            // podremos sobreescribirla con nuestra base de datos.
            this.getReadableDatabase();
        }
        try {

            copyDataBase();

        } catch (Exception e) {

            throw new Error("Error copiando database");
        }
    }


    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {
            // Base de datos no creada todavia
        }

        if (checkDB != null) {

            checkDB.close();
        }

        return checkDB != null ? true : false;

    }

    public void openDataBase() throws SQLException {

        // Open the database
        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {

        if (db != null)
            db.close();

        super.close();
    }

    private void copyDataBase() throws IOException {

        //Abrimos el fichero de base de datos como entrada
        InputStream myInput = context.getAssets().open(DB_NAME);

        //Ruta a la base de datos vacía recién creada
        String outFileName = DB_PATH + DB_NAME;

        //Abrimos la base de datos vacía como salida
        OutputStream myOutput = new FileOutputStream(outFileName);

        //Transferimos los bytes desde el fichero de entrada al de salida
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Liberamos los streams
        myOutput.flush();
        myOutput.close();
        myInput.close();



        try{
            openDataBase();
            String CREATE_CIUDAD = "CREATE TABLE IF NOT EXISTS \"ciudad\" (\"Id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  ,\"Nombre\" VARCHAR(45)  NULL DEFAULT NULL COLLATE NOCASE);";
            db.execSQL(CREATE_CIUDAD);
            String CREATE_EMPRESA = "CREATE TABLE IF NOT EXISTS \"empresa\" (\"Id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  ,\"Nombre\" VARCHAR(45)  NOT NULL  COLLATE NOCASE,\"Correo\" VARCHAR(45)  NOT NULL  COLLATE NOCASE,\"Dirección\" VARCHAR(45)  NOT NULL  COLLATE NOCASE,\"Telefono\" VARCHAR(15)  NOT NULL  COLLATE NOCASE);";
            db.execSQL(CREATE_EMPRESA);
            String CREATE_BUS = "CREATE TABLE IF NOT EXISTS \"bus\" (\"Id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  ,\"Empresa_Id\" INT  NOT NULL  ,\"Patente\" VARCHAR(45)  NOT NULL  COLLATE NOCASE,CONSTRAINT `fk_Bus_Empresa1` FOREIGN KEY (`Empresa_Id`) REFERENCES empresa (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION);\n" +
                    "\n" +
                    "CREATE INDEX 'fk_Bus_Empresa1_idx' ON 'bus' (`Empresa_Id` DESC);)";
            db.execSQL(CREATE_BUS);
            String CREATE_HACE = "CREATE TABLE IF NOT EXISTS \"hace\" (\"Id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  ,\"hora_inicio\" TIME  NULL DEFAULT NULL ,\"hora_termino\" TIME  NULL DEFAULT NULL ,\"Bus_Id\" INT  NOT NULL  ,\"Recorrido_Id\" INT  NOT NULL  ,CONSTRAINT `fk_Hace_Bus1` FOREIGN KEY (`Bus_Id`) REFERENCES bus (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,CONSTRAINT `fk_Hace_Recorrido1` FOREIGN KEY (`Recorrido_Id`) REFERENCES recorrido (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION);\n" +
                    "\n" +
                    "CREATE INDEX 'fk_Hace_Bus1_idx' ON 'hace' (`Bus_Id` DESC);";
            db.execSQL(CREATE_HACE);
            String CREATE_USUARIO = "CREATE TABLE IF NOT EXISTS \"usuario\" (\"Id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  ,\"Longitud\" FLOAT(0,0)  NULL DEFAULT NULL ,\"Latitud\" FLOAT(0,0)  NULL DEFAULT NULL ,\"Correo\" VARCHAR(45)  NOT NULL  COLLATE NOCASE);\n" +
                    "\n" +
                    "CREATE UNIQUE INDEX 'Id_UNIQUE' ON 'usuario' (`Id` DESC);";
            db.execSQL(CREATE_USUARIO);
            String CREATE_CHOFER = "CREATE TABLE IF NOT EXISTS \"chofer\" (\"Id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  ,\"Usuario_Id\" INT  NOT NULL  ,\"Bus_Id\" INT  NOT NULL  ,\"Empresa_Id\" INT  NOT NULL  ,CONSTRAINT `fk_Chofer_Bus1` FOREIGN KEY (`Bus_Id`) REFERENCES bus (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,CONSTRAINT `fk_Chofer_Empresa1` FOREIGN KEY (`Empresa_Id`) REFERENCES empresa (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,CONSTRAINT `fk_Chofer_Usuario1` FOREIGN KEY (`Usuario_Id`) REFERENCES usuario (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION);\n" +
                    "\n" +
                    "CREATE INDEX 'fk_Chofer_Bus1_idx' ON 'chofer' (`Bus_Id` DESC);";
            db.execSQL(CREATE_CHOFER);
            String CREATE_PASAJERO = "CREATE TABLE IF NOT EXISTS \"pasajero\" (\"Id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  ,\"Usuario_Id\" INT  NOT NULL  ,CONSTRAINT `fk_Pasajero_Usuario1` FOREIGN KEY (`Usuario_Id`) REFERENCES usuario (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION);\n" +
                    "\n" +
                    "CREATE INDEX 'fk_Pasajero_Usuario1_idx' ON 'pasajero' (`Usuario_Id` DESC);";
            db.execSQL(CREATE_PASAJERO);
            String CREATE_PARADERO = "CREATE TABLE IF NOT EXISTS \"paradero\" (\"Id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  ,\"Longitud\" FLOAT(0,0)  NOT NULL  ,\"Latitud\" FLOAT(0,0)  NOT NULL  ,\"Nombre\" CHAR(1)  NOT NULL  COLLATE NOCASE);";
            db.execSQL(CREATE_PARADERO);
            String CREATE_NOTIFICACION = "CREATE TABLE IF NOT EXISTS \"notificación\" (\"Id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  ,\"Notificacion\" VARCHAR(45)  NOT NULL  COLLATE NOCASE,\"Usuario_Id\" INT  NOT NULL  ,CONSTRAINT `fk_Notificación_Usuario1` FOREIGN KEY (`Usuario_Id`) REFERENCES usuario (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION);\n" +
                    "\n" +
                    "CREATE INDEX 'fk_Notificación_Usuario1_idx' ON 'notificación' (`Usuario_Id` DESC);";
            db.execSQL(CREATE_NOTIFICACION);
            String CREATE_TIPO_PASAJE = "CREATE TABLE IF NOT EXISTS \"tipopasaje\" (\"Id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  ,\"Tipo\" VARCHAR(45)  NULL DEFAULT NULL COLLATE NOCASE);";
            db.execSQL(CREATE_TIPO_PASAJE);
            String CREATE_ESTA_EN = "CREATE TABLE IF NOT EXISTS \"esta_en\" (\"Id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  ,\"Usuario_Id\" INT  NOT NULL  ,\"Paradero_Id\" INT  NOT NULL  ,CONSTRAINT `fk_Esta_En_Paradero1` FOREIGN KEY (`Paradero_Id`) REFERENCES paradero (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,CONSTRAINT `fk_Esta_En_Usuario1` FOREIGN KEY (`Usuario_Id`) REFERENCES usuario (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION);\n" +
                    "\n" +
                    "CREATE INDEX 'fk_Esta_En_Paradero1_idx' ON 'esta_en' (`Paradero_Id` DESC);";
            db.execSQL(CREATE_ESTA_EN);
            String CREATE_RECORRIDO = "CREATE TABLE IF NOT EXISTS \"recorrido\" (\"Id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  ,\"HorarioOrigen\" TIME  NULL DEFAULT NULL ,\"HorarioDestino\" TIME  NULL DEFAULT NULL ,\"Ciudad_Origen_Id\" INT  NOT NULL  ,\"Ciudad_Destino_Id\" INT  NOT NULL  ,CONSTRAINT `fk_Recorrido_Ciudad1` FOREIGN KEY (`Ciudad_Origen_Id`) REFERENCES ciudad (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,CONSTRAINT `fk_Recorrido_Ciudad2` FOREIGN KEY (`Ciudad_Destino_Id`) REFERENCES ciudad (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION);\n" +
                    "\n" +
                    "CREATE INDEX 'fk_Recorrido_Ciudad1_idx' ON 'recorrido' (`Ciudad_Origen_Id` DESC);";
            db.execSQL(CREATE_RECORRIDO);
            String CREATE_RECORRIDO_PARADERO = "CREATE TABLE IF NOT EXISTS \"recorrido_paradero\" (\"Recorrido_Id\" INT  NOT NULL  ,\"Paradero_Id\" INT  NOT NULL  ,\"Id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  ,CONSTRAINT `fk_Recorrido_has_Paradero_Paradero1` FOREIGN KEY (`Paradero_Id`) REFERENCES paradero (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,CONSTRAINT `fk_Recorrido_has_Paradero_Recorrido1` FOREIGN KEY (`Recorrido_Id`) REFERENCES recorrido (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION);\n" +
                    "\n" +
                    "CREATE INDEX 'fk_Recorrido_has_Paradero_Paradero1_idx' ON 'recorrido_paradero' (`Paradero_Id` DESC);";
            db.execSQL(CREATE_RECORRIDO_PARADERO);
            String CREATE_TIPOPASAJE_RECORRIDO = "CREATE TABLE IF NOT EXISTS \"tipopasaje_recorrido\" (\"TipoPasaje_Id\" INT  NOT NULL  ,\"Recorrido_Id\" INT  NOT NULL  ,\"Id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  ,\"Precio\" INT  NULL DEFAULT NULL ,CONSTRAINT `fk_TipoPasaje_has_Recorrido_Recorrido1` FOREIGN KEY (`Recorrido_Id`) REFERENCES recorrido (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,CONSTRAINT `fk_TipoPasaje_has_Recorrido_TipoPasaje1` FOREIGN KEY (`TipoPasaje_Id`) REFERENCES tipopasaje (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION);\n" +
                    "\n" +
                    "CREATE INDEX 'fk_TipoPasaje_has_Recorrido_Recorrido1_idx' ON 'tipopasaje_recorrido' (`Recorrido_Id` DESC);";
            db.execSQL(CREATE_TIPOPASAJE_RECORRIDO);


            close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

    }
    /*
    private void copyDataBase() throws IOException {

        OutputStream databaseOutputStream = new FileOutputStream("" + DB_PATH + DB_NAME);
        InputStream databaseInputStream;

        byte[] buffer = new byte[1024];
        int length;

        databaseInputStream = context.getAssets().open(DB_NAME);
        while ((length = databaseInputStream.read(buffer)) > 0) {
            databaseOutputStream.write(buffer);
        }

        databaseInputStream.close();
        databaseOutputStream.flush();
        databaseOutputStream.close();
    }
    */
}
