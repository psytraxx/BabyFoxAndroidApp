// MainActivity.java
package dynamicflash.de.babyfox;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

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

        LoaderManager.getInstance(this).initLoader(0, null, this).forceLoad();
    }

    @NonNull
    @Override
    public Loader<List<String>> onCreateLoader(int id, @Nullable Bundle args) {
        return new ImageSearchLoader(this, SEARCH_TERM);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<String>> loader, List<String> images) {
        ImageSliderAdapter adapter = new ImageSliderAdapter(this, images);
        binding.viewPager.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<String>> loader) {
        // Handle loader reset if needed
    }
}