package andressancho.com.html_web_scrapping;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    String url="http://www.imdb.com/list/ls064079588/";
    GridView gv;
    ArrayList<Movie> movies= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gv=findViewById(R.id.gv);

        DownloadTask downloadTask= new DownloadTask();
        try {
            String html= downloadTask.execute(url).get();

            Document document = Jsoup.parse(html);

            Elements nombres = document.select("div.lister-item-content h3 a");
            Elements portadas = document.select("div .lister-item-image a img ");
            Elements metascores = document.select("span.metascore ");
            Elements rating = document.select("div.ratings-bar div strong");


            for (int i = 0; i < 90; i++) {
                Movie m= new Movie();
                m.setNombre(nombres.get(i).text());
                m.setRating(Float.parseFloat(rating.get(i).text()));
                m.setImageURL(portadas.get(i).attr("loadlate"));
                m.setMetascore(metascores.get(i).text());
                movies.add(m);



            }

            for(Movie m:movies){
               ImageDownloadTask idt=new ImageDownloadTask();
               m.setImagen(idt.execute(m.getImageURL()).get());

            }

            Movie[] moviess= new Movie[movies.size()];
            int x=0;
            for (Movie m:movies){
                moviess[x]=m;
                x++;
            }

            MovieAdapter adapter= new MovieAdapter(this,R.layout.grid_view_list_item,moviess);
            gv.setAdapter(adapter);



        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;
            String input;

            try {
                url = new URL(urls[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);


                BufferedReader reader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();


                while((input = reader.readLine()) != null){
                    stringBuilder.append("\n");
                    stringBuilder.append(input);
                }

                reader.close();
                inputStreamReader.close();

                result = stringBuilder.toString();
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }  catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

    }
    public class ImageDownloadTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {


            try {
                URL url = new URL(urls[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                return bitmap;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);


        }

    }
}
