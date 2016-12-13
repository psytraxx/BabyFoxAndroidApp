package dynamicflash.de.babyfox;

import android.os.AsyncTask;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eric on 10/12/16. - all rights reserved
 */

class ImageSearchTask extends AsyncTask<String, Void, List<String>> {

    private final List<String> images = new ArrayList<>();

    private final ImageSearchTaskCompleted taskCompleted;

    public ImageSearchTask(ImageSearchTaskCompleted taskCompleted) {

        this.taskCompleted = taskCompleted;
    }

    @Override
    protected List<String> doInBackground(String... searchTerm) {

        HttpURLConnection urlConnection = null;
        URL url;
        try {
            url = new URL("https://www.bing.com/images/search?q="+ URLEncoder.encode(searchTerm[0],"UTF-8")+"&view=detailv2");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Trident/7.0; rv:11.0) like Gecko");

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            Document doc = Jsoup.parse(in, "UTF-8", "https://www.bing.com/");
            Elements links = doc.select("span[json-data]");
            for (Element e : links) {
                String jsonDataEndoded = e.attr("json-data");
                String jsonData = URLDecoder.decode(jsonDataEndoded,"UTF-8");
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(jsonData).getAsJsonObject();
                JsonObject jsonArray = jsonObject.getAsJsonObject("web");
                Integer i = 0;
                while (jsonArray.get(i.toString()) != null)  {
                    images.add(jsonArray.get(i+"").getAsJsonObject().get("imgUrl").getAsString());
                    i++;
                }
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
