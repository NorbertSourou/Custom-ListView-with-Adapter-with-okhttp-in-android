package com.example.custom_listview_adapter_with_json_data;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    // ArrayList of type "Person". We are making not a list of string, sondern eine list of persons
    ArrayList<Person> arrayOfWebData = new ArrayList<Person>();

    class Person {
        public String person_id;
        public String name;
        public String birthday;
        public String favorite_color;
        public String profilbild;
    }

    // This is our new Adapter:
    FancyAdapter aa = null;

    // For each row we returned until we use the array to create our person obj
    static ArrayList<String> resultRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        run();

    }

    // Params,Progress,Result
    run() {
        @Override
        protected Void doInBackground (Void...arg0){
            OkHttpClient client = new OkHttpClient();
            String url = "http://192.168.1.107:8000/api/patient";
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        final String myResponse = response.body().string();

                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("TAG", "run: " + myResponse);
                                try {

                                    //JSONObject json = new JSONObject(myResponse);
                                    // Log.d("TAG", "run: " + json.getJSONObject("data").getString("first_name"));
                                    JSONArray jArray = new JSONArray(myResponse);
                                    Log.d("TAG3", "run: " + jArray.length());
                                    // Log.d("TAG1", "run: " + json.getJSONObject("medecin").getString("nom"));
                                    // txtString.setText(json.getJSONObject("data").getString("first_name")+ " "+json.getJSONObject("data").getString("last_name"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            });
            // Parse JSON:

            try {
                JSONArray jArray = new JSONArray(result);
                for (int i = 0; i < jArray.length(); i++) {
                    // Get our object, this is on persons data:
                    JSONObject json_data = jArray.getJSONObject(i);
                    // Create a new person:
                    Person resultRow = new Person();
                    // Set thats persons attributes:
                    resultRow.person_id = json_data.getString("id");
                    resultRow.name = json_data.getString("nom");
                    resultRow.favorite_color = json_data.getString("prenom");
                    resultRow.birthday = json_data.getString("telephone");
                    resultRow.profilbild = json_data.getString("sexe");
                    arrayOfWebData.add(resultRow);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute (Void result){
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            populateListView();
        }
    }


    public void populateListView() {
        final ListView myListView = (ListView) findViewById(R.id.myListView);
        // Initialize FancyAdapter object:
        aa = new FancyAdapter();
        myListView.setAdapter(aa);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                // Get Person "behind" the clicked item
                Person p = (Person) myListView.getItemAtPosition(position);

                // Log the fields to check if we got the info we want
                Log.i("SomeTag", "Persons name: " + p.name);
                Log.i("SomeTag", "Persons id : " + p.person_id);

                Intent i = new Intent(MainActivity.this, ProfilAnsehen.class);
                i.putExtra("mitglied_id", p.person_id);
                i.putExtra("mitglied_benutzername", p.name);
                i.putExtra("profilbild", p.profilbild);
                startActivity(i);
            }
        });
    }


    class FancyAdapter extends ArrayAdapter<Person> {
        FancyAdapter() {
            super(MainActivity.this, android.R.layout.simple_list_item_1, arrayOfWebData);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.row3, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.populateFrom(arrayOfWebData.get(position));
            return convertView;
        }
    }

    class ViewHolder {
        public TextView name = null;
        public TextView birthday = null;
        public TextView favorite_color = null;
        public ImageView profilbild = null;
        //public ImageLoader imageLoader;


        ViewHolder(View row) {
            name = (TextView) row.findViewById(R.id.name);
            birthday = (TextView) row.findViewById(R.id.birthday);
            favorite_color = (TextView) row.findViewById(R.id.favorite_color);
            profilbild = (ImageView) row.findViewById(R.id.imageView1);
        }

        // Notice we have to change our populateFrom() to take an argument of type "Person"
        void populateFrom(Person r) {
            name.setText(r.name);
            birthday.setText(r.birthday);
            favorite_color.setText(r.favorite_color);

        }

    }