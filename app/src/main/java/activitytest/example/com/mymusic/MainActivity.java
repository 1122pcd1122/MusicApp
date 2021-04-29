package activitytest.example.com.mymusic;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import activitytest.example.com.mymusic.ui.main.home.HomeViewModel;
import activitytest.example.com.mymusic.ui.main.music.MusicViewModel;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import static android.content.res.Configuration.UI_MODE_NIGHT_MASK;

public class MainActivity extends AppCompatActivity {


    private HomeViewModel homeViewModel;
    private MusicViewModel musicViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        //获取底部导航的id
        BottomNavigationView navView = findViewById ( R.id.nav_view );
        navView.setItemIconTintList ( null );
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager ().findFragmentById ( R.id.nav_host_fragment );
        NavController navController = Objects.requireNonNull ( navHostFragment ).getNavController ();
        NavigationUI.setupWithNavController ( navView, navController );

        homeViewModel = new ViewModelProvider ( this ).get ( HomeViewModel.class );
        musicViewModel = new ViewModelProvider ( this ).get ( MusicViewModel.class );

    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment ( fragment );
    }

    public boolean isDarkTheme(Context context){
        int currentNightMode = getResources ().getConfiguration ().uiMode & UI_MODE_NIGHT_MASK;
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES;
    }

}