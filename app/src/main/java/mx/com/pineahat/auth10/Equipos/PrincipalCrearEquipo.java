package mx.com.pineahat.auth10.Equipos;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import mx.com.pineahat.auth10.DAO.DAOEquipos;
import mx.com.pineahat.auth10.MyAdapter;
import mx.com.pineahat.auth10.R;

public class PrincipalCrearEquipo extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mlayoutManager;
    private ArrayList<Integrantes> misIntegrantes;
    private String idActividad;
    private String idEquipo=null;
    private IntegrantesEquipoAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_crear_equipo);
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerIntegrantes);
        mRecyclerView.setHasFixedSize(true);
        mlayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mlayoutManager);
        String tipo = getIntent().getStringExtra("tipo");
        DAOEquipos miDaoEquipos = new DAOEquipos(this);
        misIntegrantes = new ArrayList<Integrantes>();
        this.idActividad = getIntent().getStringExtra("idActividad");
        if(tipo.equals("nuevo")) {

            this.idActividad = getIntent().getStringExtra("idActividad");
            JSONArray miJsonArray = miDaoEquipos.traerAlumnos(idActividad);
            if(miJsonArray!=null) {
                mAdapter = new IntegrantesEquipoAdapter(miJsonArray,misIntegrantes);
                mRecyclerView.setAdapter(mAdapter);
            }
            else
            {
                //No hay Alumnos
                Toast.makeText(this,"No hay alumnos",Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            String idEquipo = getIntent().getStringExtra("tipo");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal_crear_equipo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        mRecyclerView.setLayoutManager(mlayoutManager);
        if (id == R.id.action_add_team) {
            String nombre = mAdapter.getNombre();
            if(this.idEquipo==null)
            {
                this.idEquipo=crearEquipo(this.idActividad,nombre);
            }
            DAOEquipos daoEquipos = new DAOEquipos(this);
            daoEquipos.actualizarIntegrantes(misIntegrantes,this.idEquipo);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String crearEquipo(String idActividad,String nombre)
    {
        DAOEquipos miDaoEquipos = new DAOEquipos(this);
        String idEquipo = miDaoEquipos.generarEquipo(idActividad,nombre);
        return idEquipo;
    }
}