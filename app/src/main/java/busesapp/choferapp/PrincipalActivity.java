package busesapp.choferapp;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import busesapp.choferapp.beans.Bus;
import busesapp.choferapp.beans.BusDAO;
import busesapp.choferapp.beans.Chofer;
import busesapp.choferapp.beans.ChoferDAO;
import busesapp.choferapp.beans.Ciudad;
import busesapp.choferapp.beans.CiudadDAO;
import busesapp.choferapp.beans.Empresa;
import busesapp.choferapp.beans.EmpresaDAO;
import busesapp.choferapp.beans.HaceDAO;
import busesapp.choferapp.beans.ParaderoDAO;
import busesapp.choferapp.beans.Recorrido;
import busesapp.choferapp.beans.RecorridoDAO;
import busesapp.choferapp.beans.Usuario;
import busesapp.choferapp.beans.UsuarioDAO;

public class PrincipalActivity extends AppCompatActivity {
    private Spinner spnOrigen,spnDestino;
    private ImageButton imgBTN;
    private SQLiteDatabase db;
    private Button btnEnviar;
    private EditText etPatente1,etPatente2,etPatente3;
    private TextView tvCorreo;
    private String correo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        correo = getIntent().getStringExtra("Correo");
        tvCorreo = (TextView) findViewById(R.id.tvCorreo);
        tvCorreo.setText(correo);


        //DataHelper dataHelper = new DataHelper(this);
        CiudadDAO ciudad = new CiudadDAO(this);
        UsuarioDAO usuario = new UsuarioDAO(this);
        ParaderoDAO paradero=new ParaderoDAO(this);




        ArrayList<String> listaCiudad = ciudad.listadoCiudad();
        spnOrigen = (Spinner) findViewById(R.id.spnOrigen);
        spnDestino = (Spinner) findViewById(R.id.spnDestino);
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaCiudad);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnOrigen.setAdapter(adapter);
        spnDestino.setAdapter(adapter);
        spnDestino.setSelection(1);



    }

    public void onClickEnviar(View v) {
        btnEnviar = (Button) findViewById(R.id.btnEnviar);
        etPatente1 = (EditText) findViewById(R.id.txtPatente1);
        etPatente2 = (EditText) findViewById(R.id.txtPatente2);
        etPatente3 = (EditText) findViewById(R.id.txtPatente3);
        spnOrigen = (Spinner) findViewById(R.id.spnOrigen);
        spnDestino = (Spinner) findViewById(R.id.spnDestino);
        String txt1;
        String txt2;
        String txt3;
        String patente = "";
        String origen = "";
        String destino = "";

        try {
            Date date = new Date();
            DateFormat hourFormat = new SimpleDateFormat("HH:mm");


            for (int i = 0; i < 3; i++) {

                if (i == 0) {
                    txt1 = etPatente1.getText().toString();
                    patente = patente + txt1 + "-";
                }
                if (i == 1) {
                    txt2 = etPatente2.getText().toString();
                    patente = patente + txt2 + "-";
                }
                if (i == 2) {
                    txt3 = etPatente3.getText().toString();
                    patente = patente + txt3;
                }


            }
            String laHoraDeOrigen = "";
            String laHoraDeDestino = "";


            origen = spnOrigen.getSelectedItem().toString();
            destino = spnDestino.getSelectedItem().toString();

            if(origen.equals(destino)){
                Toast.makeText(this, "Ingrese ciudades válidas", Toast.LENGTH_LONG);
            }



            long ahora = System.currentTimeMillis();
            Date fecha = new Date(ahora);
            DateFormat df = new SimpleDateFormat("dd/MM/yy");
            String salida = df.format(fecha);
            Calendar calendario = Calendar.getInstance();
            calendario.setTimeInMillis(ahora);
            int horaOrigen = calendario.get(Calendar.HOUR_OF_DAY);
            int minutoOrigen = calendario.get(Calendar.MINUTE);
            int minutoDestino = minutoOrigen + 45;
            int horaDestino = horaOrigen;
            if (minutoDestino >= 60) {
                horaDestino = horaDestino + 1;
                minutoDestino = minutoDestino - 60;
            }
            try {
                String hour1 = String.valueOf(horaOrigen);
                String minute1 = String.valueOf(minutoOrigen);
                laHoraDeOrigen = hour1 + ":" + minute1;
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                String hour2 = String.valueOf(horaDestino);
                String minute2 = String.valueOf(minutoDestino);
                laHoraDeDestino = hour2 + ":" + minute2;
            } catch (Exception e) {
                e.printStackTrace();
            }

            Date now = new Date();
            String format1 = new SimpleDateFormat("EEE", Locale.ENGLISH).format(now);
            String dia = traductorDia(format1);

            CiudadDAO ciudadQ = new CiudadDAO(this);
            Ciudad ciuori = ciudadQ.buscarNombre(origen);
            Ciudad ciudes = ciudadQ.buscarNombre(destino);
            int ciudadOrigenID = ciuori.getId();
            int ciudadDestinoID = ciudes.getId();

            BusDAO busQ = new BusDAO(this);
            Bus bus = busQ.buscar(patente);
            int busID = bus.getId();
            int busEMp = bus.getEmpresa_Id();

            String correo = "";
            Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
            Account[] accounts = AccountManager.get(this).getAccounts();
            for (Account account : accounts) {
                if (emailPattern.matcher(account.name).matches()) {
                    correo = (account.name).toString();

                }
            }
            correo="daniel.carrillo05@hotmail.com";


            UsuarioDAO usu = new UsuarioDAO(this);
            Usuario usuario = usu.buscar(correo);
            int user = usuario.getId();
            int enterprise = 0;

            try {
                EmpresaDAO emp = new EmpresaDAO(this);
                Empresa empresa = emp.buscar(busEMp);
                enterprise = empresa.getId();

            } catch (Exception e) {
                e.printStackTrace();
            }

            ChoferDAO choQ = new ChoferDAO(this);
            Chofer cho = choQ.buscar(user);
            int choferOn = 1;
            //choQ.modificar(cho.getId(), user, busID, enterprise, choferOn,contraseña);

            RecorridoDAO recorridoQ = new RecorridoDAO(this);
            Recorrido recorr = recorridoQ.buscarDosCiudades(ciudadOrigenID, ciudadDestinoID);
            int recorridoID = recorr.getId();

            HaceDAO hace_recorrido = new HaceDAO(this);

            boolean bool = hace_recorrido.insertar(laHoraDeOrigen, laHoraDeDestino, busID, recorridoID);
            btnEnviar = (Button) findViewById(R.id.button);
            if (bool == true) {
                btnEnviar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(PrincipalActivity.this, MenuActivity.class);
                        startActivity(intent);
                    }
                });
            } else {
                Toast.makeText(this, "Error en el envío", Toast.LENGTH_LONG);
            }
        }catch(Exception e){
            e.printStackTrace();
            Toast toast = new Toast(this).makeText(this,"Error en el ingreso de datos", Toast.LENGTH_LONG);
            toast.show();
        }





/*
        btnEnviar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(PrincipalActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });*/
    }


    @Override
    public void onBackPressed() {
    }

    public String traductorDia(String day){
        String dia = null;

        if(day.equals("Mon")){
            dia = "Lunes";
        }
        if(day.equals("Thu")){
            dia = "Martes";
        }
        if(day.equals("Wed")){
            dia = "Miercoles";
        }
        if(day.equals("Tue")){
            dia = "Jueves";
        }
        if(day.equals("Fri")){
            dia = "Viernes";
        }
        if(day.equals("Sat")){
            dia = "Sabado";
        }
        if(day.equals("Sun")){
            dia = "Domingo";
        }

        return dia;

    }


}
