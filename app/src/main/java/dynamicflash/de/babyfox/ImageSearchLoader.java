package dynamicflash.de.babyfox;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by eric on 10/12/16. - all rights reserved
 */

class ImageSearchLoader extends AsyncTaskLoader<List<String>> {

    private final String searchTerm;

    private final List<String> images = new ArrayList<>();

    public ImageSearchLoader(Context context, String searchTerm) {

        super(context);
        this.searchTerm = searchTerm;
    }

    @Nullable
    @Override
    public List<String> loadInBackground() {
        HttpURLConnection urlConnection = null;
        URL url;
        try {
            url = new URL("https://www2.bing.com/images/search?q=" + URLEncoder.encode(this.searchTerm, "UTF-8") + "&form=HDRSC2&first=1&tsc=ImageHoverTitle");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("User-Agent", "xxx");

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            Document doc = Jsoup.parse(in, "UTF-8", "https://www.bing.com/");
            Elements thumbs = doc.select("a[class='iusc']");
            for (Element e : thumbs) {
                String sourceLink = e.attr("href");
                Uri uri = Uri.parse(sourceLink);
                images.add(uri.getQueryParameter("mediaurl"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        return images;
    }
}
