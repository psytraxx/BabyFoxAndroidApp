package dynamicflash.de.babyfox;

import android.net.Uri;
import android.os.AsyncTask;

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

/**
 * Created by eric on 10/12/16. - all rights reserved
 */

class ImageSearchTask extends AsyncTask<String, Void, List<String>> {

    private final List<String> images = new ArrayList<>();

    private final ImageSearchTaskCompleted taskCompleted;

    ImageSearchTask(ImageSearchTaskCompleted taskCompleted) {

        this.taskCompleted = taskCompleted;
    }

    @Override
    protected List<String> doInBackground(String... searchTerm) {

        HttpURLConnection urlConnection = null;
        URL url;
        try {
            url = new URL("https://www2.bing.com/images/search?q=" + URLEncoder.encode(searchTerm[0],"UTF-8") +"&form=HDRSC2&first=1&tsc=ImageHoverTitle");
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

    public interface ImageSearchTaskCompleted {
        void onTaskCompleted(List<String> response);
    }

    protected void onPostExecute(List<String> result){
        taskCompleted.onTaskCompleted(result);
    }
}
