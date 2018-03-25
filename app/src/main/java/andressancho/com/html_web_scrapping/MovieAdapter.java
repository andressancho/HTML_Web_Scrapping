package andressancho.com.html_web_scrapping;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by Usuario on 24/03/2018.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {
    Context context;
    int layoutResourceId;
    Movie[] data=null;

    public MovieAdapter(@NonNull Context context, int resource, @NonNull Movie[] objects) {
        super(context, resource, objects);
        this.context=context;
        layoutResourceId=resource;
        data=objects;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View row=convertView;
        MoviesHolder holder=null;
        if(row==null){
            LayoutInflater inflater=((Activity)context).getLayoutInflater();
            row=inflater.inflate(layoutResourceId,parent,false);
            holder= new MoviesHolder();
            holder.image=row.findViewById(R.id.portada);
            holder.metascore=row.findViewById(R.id.metascore);
            holder.rating=row.findViewById(R.id.rating);
            holder.tirulo=row.findViewById(R.id.titulo);
            row.setTag(holder);
        }else{
            holder=(MoviesHolder)row.getTag();
        }
        Movie m=data[position];
        holder.tirulo.setText(m.getNombre());
        holder.rating.setRating(m.getRating()/2);
        holder.metascore.setText("Metascore: "+m.getMetascore());
        holder.image.setImageBitmap(m.getImagen());

        return row;

    }
    static class MoviesHolder{
        ImageView image;
        RatingBar rating;
        TextView metascore;
        TextView tirulo;
    }
}