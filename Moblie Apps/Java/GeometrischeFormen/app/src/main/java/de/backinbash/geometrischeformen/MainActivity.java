package de.backinbash.geometrischeformen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import de.backinbash.geometrischeformen.formen.Dreieck;
import de.backinbash.geometrischeformen.formen.GeometrischeFigur;
import de.backinbash.geometrischeformen.formen.Kreis;
import de.backinbash.geometrischeformen.formen.Rechteck;

public class MainActivity extends AppCompatActivity {
    public static Fragment fragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        //TabLayout.Tab firstTab = tabLayout.getTabAt(tabLayout.getSelectedTabPosition());
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()) {
                    case 0:
                        fragment = new TabRechteck();
                        break;
                    case 1:
                        fragment = new TabDreieck();
                        break;
                    case 2:
                        fragment = new TabKreis();
                        break;
                }

                ft.replace(R.id.formOptions, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

        ListView overview = findViewById(R.id.lstOverview);
        ArrayList<GeometrischeFigur> init = new ArrayList<>();
        init.add(new Rechteck(10.0, 11.1));
        init.add(new Dreieck(10.0, 11.1));
        init.add(new Kreis(10.0));


        ArrayAdapter<GeometrischeFigur> adapter =
                new ArrayAdapter<GeometrischeFigur>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, init);
        overview.setAdapter(adapter);

        overview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {
                GeometrischeFigur value = (GeometrischeFigur) adapter.getItemAtPosition(position);
                // assuming string and if you want to get the value on click of list item
                // do what you intend to do on click of listview row
            }
        });

    }
}