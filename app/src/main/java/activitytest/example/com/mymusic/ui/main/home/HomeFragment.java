package activitytest.example.com.mymusic.ui.main.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import activitytest.example.com.mymusic.databinding.FragmentHomeBinding;
import activitytest.example.com.mymusic.ui.main.home.HomeViewModel;
import activitytest.example.com.mymusic.ui.main.home.nestedFragment.CollectionPagerAdapter;
import activitytest.example.com.mymusic.ui.main.home.nestedFragment.palyList.PlayListFragment;
import activitytest.example.com.mymusic.ui.main.home.nestedFragment.userInfo.UserInfoFragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import activitytest.example.com.mymusic.R;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

public class HomeFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {

//    private final String TAG = this.getClass ().getName ();
    private FragmentHomeBinding homeBinding;
    private BottomNavigationView bottomNavigationView;
    private boolean isLogin = false;
    private CollectionPagerAdapter collectionPagerAdapter;
    private HomeViewModel homeViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
    }

    @SuppressLint("UseRequireInsteadOfGet")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       bottomNavigationView  =  Objects.requireNonNull ( getActivity () ).findViewById ( R.id.nav_view );
       bottomNavigationView.setVisibility ( View.VISIBLE );

       homeBinding = DataBindingUtil.inflate ( inflater, R.layout.fragment_home, container, false );
       homeViewModel = new ViewModelProvider ( this ).get ( HomeViewModel.class );
       homeViewModel.getText ().observe ( getViewLifecycleOwner (), s -> homeBinding.titleHome.setText ( s ) );



        actionMenu(isLogin);
        showUserInfo ();
        Fragment[] fragments = new Fragment[2];
        fragments[0] = new UserInfoFragment ();
        fragments[1] = new PlayListFragment ();
        String[] titles = new String[fragments.length];
        titles[0] = "用户信息";
        titles[1] = "用户歌单";
        collectionPagerAdapter = new CollectionPagerAdapter ( getChildFragmentManager (),fragments ,titles);
        homeBinding.pager.setAdapter ( collectionPagerAdapter );


        return homeBinding.getRoot ();
    }

    /**
     * 显示用户的信息
     */
    @SuppressLint("SetTextI18n")
    private void showUserInfo() {

        homeBinding.vipTag.setVisibility ( View.INVISIBLE );
        SharedPreferences preferences = getContext ().getSharedPreferences ( "userInfo", Context.MODE_PRIVATE );

        if (preferences.contains ( "name" )){
            homeBinding.userName.setText (preferences.getString ( "name" ,"defaultName") );
            homeBinding.userSignature.setText (preferences.getString ( "describe","呼呼哈嘿" ));
            Glide.with ( this ).load ( preferences.getString ( "photo","null" ) ).into ( homeBinding.userIcon );
            //动态改变菜单项
            isLogin = true;
            actionMenu ( isLogin );
            if (preferences.getString ( "isVip","" ).equals ( "1" )){
                homeBinding.vipTag.setVisibility ( View.VISIBLE );
            }else {
                homeBinding.vipTag.setVisibility ( View.INVISIBLE );
            }
        }else {
            homeBinding.userName.setText ("pcd111");
            homeBinding.userSignature.setText ("呼呼哈嘿");
            Glide.with ( this ).load ( R.drawable.user_icon ).into ( homeBinding.userIcon );
            homeBinding.pager.setAdapter ( collectionPagerAdapter );
        }

    }



    /**
     * 弹出菜单
     * @param isLogin
     */
    private void actionMenu(boolean isLogin) {
        int menu;
        if (isLogin){
            menu = R.menu.home_out_menu;
        }else {
            menu = R.menu.home_reandlo_menu;
        }

        ImageButton showMenu = homeBinding.showMenu;
        showMenu.setOnClickListener ( view -> {
            PopupMenu popupMenu = new PopupMenu ( getContext (),view );
            MenuInflater menuInflater = popupMenu.getMenuInflater ();
            menuInflater.inflate ( menu, popupMenu.getMenu () );
            popupMenu.setOnMenuItemClickListener ( this );
            popupMenu.show ();
        } );

    }


    @SuppressLint({"NonConstantResourceId", "ResourceType", "ApplySharedPref"})
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        bottomNavigationView.setVisibility ( View.INVISIBLE );
        NavController navController = NavHostFragment.findNavController ( this );
        switch (menuItem.getItemId ()){
            case R.id.login:
                navController.navigate ( R.id.action_navigation_home_to_loginFragment );
                Toast.makeText ( getContext (), "登录", Toast.LENGTH_SHORT ).show ();
                break;
            case R.id.register:
                navController.navigate ( R.id.action_navigation_home_to_registerFragment );
                Toast.makeText ( getContext (), "注册", Toast.LENGTH_SHORT ).show ();
                break;
            case R.id.outLogin:
                SharedPreferences preferences = getContext ().getSharedPreferences ( "userInfo", Context.MODE_PRIVATE );
                boolean commit = preferences.edit ().clear ().commit ();
                if (commit){
                    Toast.makeText ( getContext (), "退出登录", Toast.LENGTH_SHORT ).show ();
                    //动态改变菜单项
                    isLogin = false;
                    actionMenu ( isLogin );
                    showUserInfo ();
                    bottomNavigationView.setVisibility ( View.VISIBLE );
                }
            default:
                return false;
        }
        return false;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach ( context );
    }
}