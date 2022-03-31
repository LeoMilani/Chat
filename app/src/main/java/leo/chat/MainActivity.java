package leo.chat;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends Activity{

    private EditText codigo;
    private EditText name;
    private Button button;

    public int versao = 1;
    public String nome = "Leo";

    Gerenciador dbm;
    ListarDados ld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        codigo = findViewById(R.id.cod);
        name = findViewById(R.id.name);
        button = findViewById(R.id.box);

        dbm = new Gerenciador(getApplicationContext());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String c = codigo.getText().toString();
                String n = name.getText().toString();
                dbm.createRecords(c,n);

                codigo.setText("Código");
                name.setText("Nome");

                atualizarlista();
            }
        });
        atualizarlista();
    }
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "Você clicou em: " + ld.getItem(position) + " N: " + position, Toast.LENGTH_SHORT).show();
    }
    public void atualizarlista(){

        ArrayList<String> lNomes = new ArrayList<>();
        Cursor dados = dbm.selectRecords();

        while(!dados.isLast()) {
            String registro = "Cod: "+dados.getString(0);
            registro+= " - Nome:"+dados.getString(1);
            lNomes.add(registro);
            dados.moveToNext();
        }

        RecyclerView recyclerView = findViewById(R.id.lista);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ld = new ListarDados(this,lNomes);
        ld.setClickListener(this::onItemClick);
        recyclerView.setAdapter(ld);
    }
}