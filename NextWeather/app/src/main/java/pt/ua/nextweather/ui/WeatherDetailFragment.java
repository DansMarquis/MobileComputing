package pt.ua.nextweather.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import pt.ua.nextweather.R;
import pt.ua.nextweather.datamodel.City;
import pt.ua.nextweather.datamodel.MenuList;
import pt.ua.nextweather.datamodel.Weather;
import pt.ua.nextweather.datamodel.WeatherType;
import pt.ua.nextweather.network.CityResultsObserver;
import pt.ua.nextweather.network.ForecastForACityResultsObserver;
import pt.ua.nextweather.network.IpmaWeatherClient;
import pt.ua.nextweather.network.WeatherTypesResultsObserver;

public class WeatherDetailFragment extends Fragment {

    IpmaWeatherClient client = new IpmaWeatherClient();
    private HashMap<String, City> cities;
    private HashMap<Integer, WeatherType> weatherDescriptions;
    int position = 0;
    String city = "";
    TextView tvTitle;
    TextView tvDetails;
    TextView firstdate;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null){
            if(getArguments() != null) {
                position = getArguments().getInt("position", 0);
                city = getArguments().getString("city");

            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.weather_details, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvDetails = (TextView) view.findViewById(R.id.tvDetails);
        firstdate = (TextView) view.findViewById(R.id.tvDetails2);

        tvTitle.setText(MenuList.cities[position]);
        callWeatherForecastForACityStep1(city);
    }

    public void updateView(int position){
        tvTitle.setText(MenuList.cities[position]);
    }

    public void callWeatherForecastForACityStep1(String city) {
        tvDetails.append("\nGetting forecast for: " + city); tvDetails.append("\n");

        // call the remote api, passing an (anonymous) listener to get back the results
        client.retrieveWeatherConditionsDescriptions(new WeatherTypesResultsObserver() {
            @Override
            public void receiveWeatherTypesList(HashMap<Integer, WeatherType> descriptorsCollection) {
                weatherDescriptions = descriptorsCollection;
                callWeatherForecastForACityStep2(city);
            }
            @Override
            public void onFailure(Throwable cause) {
                tvDetails.append("Failed to get weather conditions!");
            }
        });

    }

    public void callWeatherForecastForACityStep2(String city) {
        client.retrieveCitiesList(new CityResultsObserver() {

            @Override
            public void receiveCitiesList(HashMap<String, City> citiesCollection) {
                cities = citiesCollection;
                City cityFound = cities.get(city);
                if( null != cityFound) {
                    int locationId = cityFound.getGlobalIdLocal();
                    callWeatherForecastForACityStep3(locationId);
                } else {
                    tvDetails.append("unknown city: " + city);
                }
            }

            @Override
            public void onFailure(Throwable cause) {
                tvDetails.append("Failed to get cities list!");
            }
        });
    }

    public void callWeatherForecastForACityStep3(int localId) {
        client.retrieveForecastForCity(localId, new ForecastForACityResultsObserver() {
            @Override
            public void receiveForecastList(List<Weather> forecast) {
                for (Weather day : forecast) {
                    tvDetails.append(day.toString());
                        Log.i("TAG", day.toString());

                    tvDetails.append("\t");
                }
            }
            @Override
            public void onFailure(Throwable cause) {
                tvDetails.append( "Failed to get forecast for 5 days");
            }
        });

    }

}
