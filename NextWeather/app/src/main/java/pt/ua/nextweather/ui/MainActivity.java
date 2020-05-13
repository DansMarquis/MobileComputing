package pt.ua.nextweather.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import android.content.res.Configuration;
import androidx.fragment.app.FragmentTransaction;
import android.widget.Toast;

import pt.ua.nextweather.datamodel.MenuList;
import pt.ua.nextweather.ui.WeatherDetailFragment;
import pt.ua.nextweather.ui.WeatherMenuFragment;
import pt.ua.nextweather.R;
import pt.ua.nextweather.datamodel.City;
import pt.ua.nextweather.datamodel.Weather;
import pt.ua.nextweather.datamodel.WeatherType;
import pt.ua.nextweather.network.CityResultsObserver;
import pt.ua.nextweather.network.ForecastForACityResultsObserver;
import pt.ua.nextweather.network.IpmaWeatherClient;
import pt.ua.nextweather.network.WeatherTypesResultsObserver;

public class MainActivity extends AppCompatActivity implements WeatherMenuFragment.OnItemSelectedListener{

    IpmaWeatherClient client = new IpmaWeatherClient();
    private HashMap<String, City> cities;
    private HashMap<Integer, WeatherType> weatherDescriptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            WeatherMenuFragment firstFragment = new WeatherMenuFragment();

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();// begin  Fragment Transaction
            ft.add(R.id.flContainer, firstFragment);                                // add Fragment
            ft.commit();                                                            // commit Fragment Transaction
        }

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            WeatherDetailFragment secondFragment = new WeatherDetailFragment();
            Bundle args = new Bundle();
            args.putInt("position", 0);
            secondFragment.setArguments(args);          
            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();// begin Fragment Transaction
            ft2.add(R.id.flContainer2, secondFragment);                               // add Fragment
            ft2.commit();                                                            // commit Fragment Transaction
        }
    }

    @Override
    public void onCitySelected(int position) {

        WeatherDetailFragment secondFragment = new WeatherDetailFragment();


        Bundle args = new Bundle();
        args.putInt("position", position);

        args.putString("city", MenuList.cities[position]);
        secondFragment.setArguments(args);          


        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContainer2, secondFragment) 
                    //.addToBackStack(null)
                    .commit();
        }else{
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContainer, secondFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
