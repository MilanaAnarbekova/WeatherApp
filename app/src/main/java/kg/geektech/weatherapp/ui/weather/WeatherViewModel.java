package kg.geektech.weatherapp.ui.weather;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import kg.geektech.weatherapp.common.Resource;
import kg.geektech.weatherapp.data.models.MainResponse;
import kg.geektech.weatherapp.data.repositories.WeatherRepository;

@HiltViewModel
public class WeatherViewModel extends ViewModel {
    public WeatherRepository repository;

@Inject
    public WeatherViewModel(WeatherRepository repository) {
        this.repository = repository;
    }
    public LiveData<Resource<MainResponse>> weatherLiveData;

    public  void getWeather(String city){
        weatherLiveData = repository.getWeather(city);
    }
}
