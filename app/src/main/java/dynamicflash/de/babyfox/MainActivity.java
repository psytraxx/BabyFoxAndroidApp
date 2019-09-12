package dynamicflash.de.babyfox;

import android.os.Bundle;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.DefaultSliderView;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements ImageSearchTask.ImageSearchTaskCompleted {


    private static final String SEARCH_TERM = "Cute Baby Fox";

    private SliderLayout mDemoSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDemoSlider = findViewById(R.id.slider);
        mDemoSlider.setDuration(10000);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);

        ImageSearchTask task = new ImageSearchTask(MainActivity.this);
        task.execute(SEARCH_TERM);

    }

    @Override
    protected void onStop() {
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onTaskCompleted(List<String> urlList) {

        RequestOptions requestOption = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        for (String url : urlList) {
            DefaultSliderView textSliderView = new DefaultSliderView(this);
            textSliderView.image(url);
            textSliderView.setRequestOption(requestOption);
            textSliderView.setProgressBarVisible(true);
            mDemoSlider.addSlider(textSliderView);
        }
    }
}
