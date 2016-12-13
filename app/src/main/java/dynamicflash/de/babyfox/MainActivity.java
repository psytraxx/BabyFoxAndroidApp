package dynamicflash.de.babyfox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ImageSearchTask.ImageSearchTaskCompleted {


    private static final String SEARCH_TERM = "Cute Baby Fox";

    private SliderLayout mDemoSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDemoSlider = (SliderLayout)findViewById(R.id.slider);
        mDemoSlider.setDuration(10000);
        mDemoSlider.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
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
        for (String url: urlList) {
            DefaultSliderView textSliderView = new DefaultSliderView(this);
            textSliderView.setScaleType(BaseSliderView.ScaleType.CenterInside);
            textSliderView.image(url);
            mDemoSlider.addSlider(textSliderView);
        }
    }
}
