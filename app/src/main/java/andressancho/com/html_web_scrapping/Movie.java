package andressancho.com.html_web_scrapping;

import android.graphics.Bitmap;

/**
 * Created by Usuario on 24/03/2018.
 */

public class Movie {
    private String nombre;
    private float rating;
    private String metascore;
    private String ImageURL;
    private Bitmap imagen;

    public Movie() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getMetascore() {
        return metascore;
    }

    public void setMetascore(String metascore) {
        this.metascore = metascore;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }
}
