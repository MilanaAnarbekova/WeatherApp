package kg.geektech.weatherapp.ui.weatherCity;

import android.view.View;

import kg.geektech.weatherapp.Prefs;
import kg.geektech.weatherapp.base.BaseFragment;
import kg.geektech.weatherapp.databinding.FragmentWeatherBinding;
import kg.geektech.weatherapp.databinding.FragmentWeatherCityBinding;
import kotlin.jvm.internal.PackageReference;


public class WeatherCityFragment extends BaseFragment<FragmentWeatherCityBinding> {


    @Override
    protected FragmentWeatherCityBinding bind() {
        return FragmentWeatherCityBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setupObservers() {

    }

    @Override
    protected void setupListeners() {
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Prefs prefs = new Prefs(requireContext());
                prefs.saveCity(binding.editCity.getText().toString());
                navController.navigateUp();
            }
        });
    }

    @Override
    protected void setupViews() {

    }

    @Override
    protected void callRequests() {

    }
}