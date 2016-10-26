package busesapp.choferapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private Button btnIngresar;
    private EditText etCorreo,etContraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnIngresar = (Button) findViewById(R.id.btnIngresar);
        etCorreo = (EditText) findViewById(R.id.etCorreo);
        etContraseña = (EditText) findViewById(R.id.etContraseña);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataHelper helper = new DataHelper(LoginActivity.this);
                SQLiteDatabase db = helper.getReadableDatabase();
                helper.openDataBase();

                String getCorreo = etCorreo.getText().toString();
                String getContraseña = etContraseña.getText().toString();

                Cursor correo = db.rawQuery("SELECT u.Correo,c.Contraseña FROM usuario AS u INNER JOIN chofer AS c " +
                        "ON u.Id=c.Usuario_Id WHERE u.Correo='"+getCorreo+"' AND c.Contraseña='"+getContraseña+"';",null);
                if (correo.moveToFirst()) {
                    do {
                        Intent intent = new Intent(LoginActivity.this, PrincipalActivity.class);
                        startActivity(intent);;
                    } while (correo.moveToNext());
                    correo.close();
                }else{
                    Toast.makeText(LoginActivity.this,"Datos no validos",Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
