package com.example.bro.currencyconvert;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView tryText;
    TextView cadText;
    TextView usdText;
    TextView aedText;
    TextView chfText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tryText=findViewById(R.id.tryText);
        cadText=findViewById(R.id.cadText);
        usdText=findViewById(R.id.usdText);
        aedText=findViewById(R.id.aedText);
        chfText=findViewById(R.id.chfText);
    }


    public  void  getRates(View view){
         DownloadData downloadData=new DownloadData();
         try {
             String url="http://data.fixer.io/api/latest?access_key=c92cf8d8ae178b0751d5cbe421d25716&format=1"; //json formatinda aldigimiz veri linki
             downloadData.execute(url);
         }catch (Exception e){

         }
    }


    private  class DownloadData extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {

            String result="";
            URL url;
            HttpURLConnection httpURLConnection;

            try {
                url=new URL(strings[0]);
                httpURLConnection=(HttpURLConnection)url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader=new InputStreamReader(inputStream);

                 int data=inputStreamReader.read();

                  while(data>0){
                      char character=(char)data;
                      result+=character;

                      data=inputStreamReader.read();
                  }

                return  result;
            }catch (Exception e){
                return null;
            }
        }


        @Override
        protected  void  onPostExecute(String s){
            super.onPostExecute(s);

            try {
                JSONObject jsonObject=new JSONObject(s);
                  String base=jsonObject.getString("base");

                  String rates=jsonObject.getString("rates");
                   JSONObject jsonObject1=new JSONObject(rates);

                String turkishlira=jsonObject1.getString("TRY");
                  tryText.setText("TRY:"+turkishlira);

                String usd=jsonObject1.getString("USD");
                usdText.setText("USD:"+usd);

                String cad=jsonObject1.getString("CAD");
                cadText.setText("CAD:"+cad);

                String chf=jsonObject1.getString("CHF");
                chfText.setText("CHF:"+chf);

                String aed=jsonObject1.getString("AED");
                aedText.setText("AED:"+aed);
            }catch (Exception e){

            }
        }
    }
}
