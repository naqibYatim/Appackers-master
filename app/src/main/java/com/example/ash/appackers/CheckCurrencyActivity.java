package com.example.ash.appackers;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.HttpClientStack;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class CheckCurrencyActivity extends AppCompatActivity {

    private ArrayList<CountryItem> mCountryList;
    private CountryAdapter mAdapter;
    // index to store position of selected item spinner
    private int index;
    private double inputvalue;
	// array to store symbols of country
	public String [] val;
	// index to fetch in array
	public int to;
	public int from;

    TextView editTextAmount;
    TextView TextViewResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_currency);

        // method to create the arrayList and fill it with items
        initList();

        // create references
        editTextAmount = findViewById(R.id.editTextAmount);
        TextViewResult = findViewById(R.id.TextViewResult);
        Button buttonConvert = findViewById(R.id.buttonConvert);

        // reference to our spinner
        Spinner spinnerCountries = findViewById(R.id.spinner_countries_from);

        // spinner for to
        Spinner spinnerCountriesTo = findViewById(R.id.spinner_countries_to);

        // Instance of our custom adapter
        mAdapter = new CountryAdapter(this, mCountryList);

		// set the val array with array from resources
		val  = getResources().getStringArray(R.array.value);

        // set our countryAdapter on our countrySpinner and display our items
        spinnerCountries.setAdapter(mAdapter);

        spinnerCountriesTo.setAdapter(mAdapter);

        // handle click event
        spinnerCountries.setOnItemSelectedListener(new spinOne(1));

        // handle click event
        spinnerCountriesTo.setOnItemSelectedListener(new spinOne(2));

        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				
				// condition to check if user select both country as the same or not
				if(from == to)
				{
					Toast.makeText(getApplicationContext(), "Invalid. "+val[from] + " can't be same", Toast.LENGTH_SHORT).show();
				}
				else
				{										
					  
					TextViewResult.setText("connecting...");
                                                                        // this condition to ensure that if user enter dot, our app will not be crashed
					if(editTextAmount.getText().toString().trim().length() > 0 && !editTextAmount.getText().toString().trim().equals("."));
					// text view to store user input and convert to double
					String textValue = editTextAmount.getText().toString();
					inputvalue = Double.parseDouble(textValue);

					// start task
					new calculate().execute();												
																
				}

            }
        });
	}

	private void initList(){

        mCountryList = new ArrayList<>();
        
		// fill it with items
        mCountryList.add(new CountryItem("The United States", R.drawable.usa_the_united_states));
        mCountryList.add(new CountryItem("Malaysia", R.drawable.mys_malaysia));
        mCountryList.add(new CountryItem("Malta", R.drawable.mli_mali));
        mCountryList.add(new CountryItem("Japan", R.drawable.jpn_japan));
        mCountryList.add(new CountryItem("Poland", R.drawable.pol_poland));
        mCountryList.add(new CountryItem("The United Kingdom", R.drawable.gbr_the_united_kingdom));
        mCountryList.add(new CountryItem("Norway", R.drawable.nor_norway));
        mCountryList.add(new CountryItem("Brazil", R.drawable.bra_brazil));
        mCountryList.add(new CountryItem("Israel", R.drawable.isr_israel));
        mCountryList.add(new CountryItem("The Republic of China", R.drawable.twn_the_republic_of_china));
        mCountryList.add(new CountryItem("Hungary", R.drawable.hun_hungary));
        mCountryList.add(new CountryItem("Russia", R.drawable.rus_russia));
        mCountryList.add(new CountryItem("Hong Kong", R.drawable.hong_kong));
        mCountryList.add(new CountryItem("India", R.drawable.ind_india));
        mCountryList.add(new CountryItem("Mexico", R.drawable.mex_mexico));
        mCountryList.add(new CountryItem("Australia", R.drawable.aus_australia));
        mCountryList.add(new CountryItem("South Korea", R.drawable.kor_south_korea));
        mCountryList.add(new CountryItem("Thailand", R.drawable.tha_thailand));
        mCountryList.add(new CountryItem("Sweden", R.drawable.swe_sweden));
        mCountryList.add(new CountryItem("Canada", R.drawable.can_canada));
        mCountryList.add(new CountryItem("Romania", R.drawable.rou_romania));
        mCountryList.add(new CountryItem("Liechtenstein", R.drawable.lie_liechtenstein));
        mCountryList.add(new CountryItem("Turkey", R.drawable.tur_turkey));
        mCountryList.add(new CountryItem("The Czech Republic", R.drawable.cze_the_czech_republic));
        mCountryList.add(new CountryItem("Bulgaria", R.drawable.bgr_bulgaria));
        mCountryList.add(new CountryItem("Croatia", R.drawable.hrv_croatia));
        mCountryList.add(new CountryItem("Iceland", R.drawable.isl_iceland));
        mCountryList.add(new CountryItem("Singapore", R.drawable.sgp_singapore));
        mCountryList.add(new CountryItem("New Zealand", R.drawable.nzl_new_zealand));
        mCountryList.add(new CountryItem("South Africa", R.drawable.zaf_south_africa));
        mCountryList.add(new CountryItem("Denmark", R.drawable.dnk_denmark));
        mCountryList.add(new CountryItem("The Philippines", R.drawable.phl_the_philippines));
        mCountryList.add(new CountryItem("Indonesia", R.drawable.idn_indonesia));

    }

    // this class going to exchange from asynchronize task because we are going to perform operation
    public  class calculate extends AsyncTask<String, String, String[]>{

		// String to store rate value from user
		String rateApi;
		
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override   // here we are going to connect to internet and get the currency value
        protected String[] doInBackground(String... strings) {


            String uRl;
			try{

                uRl = getJson("https://api.exchangeratesapi.io/latest?base="+val[from]+"&symbols="+val[to]+"");
				// Create JsonObject to parse Json object from api
				JSONObject reader = new JSONObject(uRl);

                // JsonObject to read the rate object from api
                JSONObject rates = reader.getJSONObject("rates");
                // get the results of rate object
                rateApi = rates.getString(val[to]);

			}catch (IOException e) {
                e.printStackTrace();
            }catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }
		
		
        @Override
        protected void onPostExecute(String[] strings) {
			
			double currencyRate, calculatedRate;
			String formatted;
			DecimalFormat df = new DecimalFormat("0.00");

			currencyRate = Double.parseDouble(rateApi);
			calculatedRate = inputvalue * currencyRate;
            formatted = df.format(calculatedRate);
			TextViewResult.setText(formatted);

        }

        // get the java script notation from yahoo
        public String getJson(String url) throws IOException{

            StringBuilder build = new StringBuilder();
            HttpClient client = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(url);
            HttpResponse response = client.execute(httpget);
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            String con;

            while((con = reader.readLine()) !=null){
                build.append(con);
            }
            return build.toString();

        }
    }
	
	// class to determine the index of array selected by user
	private class spinOne implements AdapterView.OnItemSelectedListener
    {
    	int ide;
    	spinOne(int i)
    	{
    		ide =i;
    	}
    	public void onItemSelected(AdapterView<?> parent, View view, int index, long id) {
    		if(ide == 1){
                from = index;
                Toast.makeText(getApplicationContext(), ""+val[from] + " selected", Toast.LENGTH_SHORT).show();
            }else if(ide == 2){
                to = index;
                Toast.makeText(getApplicationContext(), ""+val[to] + " selected", Toast.LENGTH_SHORT).show();
            }

    	}
 
    	public void onNothingSelected(AdapterView<?> arg0) {
    		// TODO Auto-generated method stub	
    	}
    	
    }

}
