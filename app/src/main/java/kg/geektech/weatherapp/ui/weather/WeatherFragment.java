package kg.geektech.weatherapp.ui.weather;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.function.DoubleToIntFunction;

import dagger.hilt.android.AndroidEntryPoint;
import kg.geektech.weatherapp.Prefs;
import kg.geektech.weatherapp.R;
import kg.geektech.weatherapp.base.BaseFragment;
import kg.geektech.weatherapp.common.Resource;
import kg.geektech.weatherapp.data.models.Main;
import kg.geektech.weatherapp.data.models.MainResponse;
import kg.geektech.weatherapp.databinding.FragmentWeatherBinding;

@AndroidEntryPoint
public class WeatherFragment extends BaseFragment<FragmentWeatherBinding> {

    private WeatherViewModel viewModel;




    @Override
    protected FragmentWeatherBinding bind() {
        return FragmentWeatherBinding.inflate(getLayoutInflater());
    }


    @Override
    protected void setupObservers() {
viewModel.weatherLiveData.observe(getViewLifecycleOwner(), new Observer<Resource<MainResponse>>() {
    @Override
    public void onChanged(Resource<MainResponse> resource) {
        switch (resource.status){
            case SUCCESS:{
               setupUI(resource.data);
                break;
            }
            case ERROR:{
                Toast.makeText(requireActivity(), "error!", Toast.LENGTH_SHORT).show();
                break;
            }
            case LOADING:{
                break;
            }
        }
    }
});
    }


    @SuppressLint("SetTextI18n")
    private void setupUI(MainResponse weather ) {
        double temp = weather.getMain().getTemp();
        double tempMin = weather.getMain().getTempMin();
        double tempMax = weather.getMain().getTempMax();
        String wind = (int) Math.round(weather.getWind().getSpeed()) + " km/h";
        String humidity = weather.getMain().getHumidity() + "%";
        String pressure = weather.getMain().getPressure() + "mBar";
        String textWeather = weather.getWeather().get(0).getMain();
        String tempNow = String.valueOf((int) Math.round(weather.getMain().getTemp()));
        String urlImg = "https://openweathermap.org/img/wn/"
                + weather.getWeather().get(0).getIcon() + ".png";
        int tempInt = (int) temp;
        int tempIntMin = (int) tempMin;
        int tempIntMax = (int) tempMax;
        Glide.with(requireActivity()).load(urlImg).into(binding.imageWeather);
        binding.textTemp.setText(tempInt+"");
        binding.minTemp.setText(tempIntMin + "°С↓");
        binding.maxTemp.setText(tempIntMax + "°С↑");
        binding.textCity.setText(weather.getName());
        binding.textWindNumber.setText(wind);
        binding.textHumidityNumber.setText(humidity);
        binding.textTemp.setText(tempNow);
        binding.textPressureNumber.setText(pressure);
        binding.textWeather.setText(textWeather);
        binding.textSunriseNumber.setText(getTime(weather.getSys().getSunrise(), "hh:mm a"));
        binding.textSunsetNumber.setText(getTime(weather.getSys().getSunset(), "hh:mm a"));
        binding.textDaytimeNumber.setText(getTime(weather.getDt(), "hh:mm"));


    }




    @Override
    protected void setupListeners() {
        binding.textCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.weatherCityFragment);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Calendar calendar = Calendar.getInstance();
        int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay>=18 && timeOfDay<5){
            binding.imageCity.setImageResource(R.drawable.city_night);
        }else {
            binding.imageCity.setImageResource(R.drawable.city_day);
        }
    }
    public static String getTime(Integer milliSeconds, String dateFormat) {

        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    @Override
    protected void setupViews() {
        viewModel = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);

    }

    @Override
    protected void callRequests() {
        viewModel.getWeather(getCity());
    }
    private String getCity(){
        Prefs prefs = new Prefs(requireActivity());
        String city = prefs.getCity();
        return  city;
    }

}