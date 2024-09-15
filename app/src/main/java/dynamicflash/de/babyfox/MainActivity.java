package dynamicflash.de.babyfox;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.slidertypes.DefaultSliderView;

import java.util.List;

import dynamicflash.de.babyfox.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<String>> {


    private static final String SEARCH_TERM = "Sleeping gerbils";

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        this.binding = binding;
        setContentView(binding.getRoot());

        binding.slider.setDuration(10000);
        binding.slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        binding.slider.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);

        LoaderManager.getInstance(this).initLoader(0, null, this).forceLoad();
    }

    @Override
    protected void onStop() {
        binding.slider.stopAutoCycle();
        super.onStop();
    }

    @NonNull
    @Override
    public Loader<List<String>> onCreateLoader(int id, @Nullable Bundle args) {
        return new ImageSearchLoader(this, SEARCH_TERM);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<String>> loader, List<String> images) {
        RequestOptions requestOption = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        for (String url : images) {
            DefaultSliderView textSliderView = new DefaultSliderView(this);
            textSliderView.image(url);
            textSliderView.setRequestOption(requestOption);
            textSliderView.setProgressBarVisible(true);
            binding.slider.addSlider(textSliderView);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<String>> loader) {

    }
}
