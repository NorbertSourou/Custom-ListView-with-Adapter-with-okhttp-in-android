package com.example.custom_listview_adapter_with_json_data;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfilAnsehen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_ansehen);


        Intent intent = getIntent();
        String mitglied_benutzername = intent.getStringExtra("mitglied_benutzername");
        String mitglied_id = intent.getStringExtra("mitglied_id");
        String profilbild = intent.getStringExtra("profilbild");

        TextView benutzername = (TextView) findViewById(R.id.textViewBenutzername);
        benutzername.setText(mitglied_benutzername);

        TextView mitglied_idV = (TextView) findViewById(R.id.textViewMitgliedId);
        mitglied_idV.setText("Mitglied-ID:" + mitglied_id);

        ImageView imageV = (ImageView) findViewById(R.id.imageView2);


    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_profil_ansehen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

}
