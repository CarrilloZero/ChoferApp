package busesapp.choferapp;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.regex.Pattern;

import busesapp.choferapp.beans.NotificacionDAO;
import busesapp.choferapp.beans.Usuario;
import busesapp.choferapp.beans.UsuarioDAO;

public class ComentarioActivity extends AppCompatActivity {

    private EditText comentario;
    private Button button;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentario);
        button = (Button)findViewById(R.id.btnEnviar);


    }

    public void onClick(View v) {

        NotificacionDAO noti = new NotificacionDAO(this);
        UsuarioDAO usu = new UsuarioDAO(this);
        String correo="";
        comentario = (EditText) findViewById(R.id.txtComentario);
        String txtComentario = comentario.getText().toString();

        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(this).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                correo = (account.name).toString();

            }
        }

        correo="daniel.carrillo05@hotmail.com";

        try {


            Usuario usuario = usu.buscar(correo);
            String elCorreo = usuario.getCorreo().toString();
            int id = usuario.getId();

            long ahora = System.currentTimeMillis();
            Calendar calendario = Calendar.getInstance();
            calendario.setTimeInMillis(ahora);
            int hora = calendario.get(Calendar.HOUR_OF_DAY);
            int minuto = calendario.get(Calendar.MINUTE);
            String laHora = String.valueOf(hora)+":"+String.valueOf(minuto);

            if (correo.equals(elCorreo)) {

                if(noti.insertar(txtComentario, id, laHora)){
                    Toast.makeText(getApplicationContext(), "Registro insertado", Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(getApplicationContext(), "Registro no insertado", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(getApplicationContext(), "Correos no coinciden.", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e){
            e.printStackTrace();
        }








    }
}
