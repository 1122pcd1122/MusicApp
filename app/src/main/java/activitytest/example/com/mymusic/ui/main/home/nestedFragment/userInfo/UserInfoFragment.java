package activitytest.example.com.mymusic.ui.main.home.nestedFragment.userInfo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import activitytest.example.com.mymusic.databinding.FragmentUserInfoBinding;
import activitytest.example.com.mymusic.ui.main.home.HomeViewModel;
import activitytest.example.com.mymusic.ui.main.home.nestedFragment.HomeRepository;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.shape.Shapeable;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import activitytest.example.com.mymusic.R;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


public class UserInfoFragment extends Fragment {

    private HomeViewModel homeViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentUserInfoBinding userInfoBinding = DataBindingUtil.inflate ( inflater, R.layout.fragment_user_info, container, false );
        homeViewModel = new ViewModelProvider ( this ).get ( HomeViewModel.class );
        userInfoBinding.buyVip.setOnClickListener ( v -> {
            @SuppressLint("UseRequireInsteadOfGet")
            SharedPreferences sharedPreferences = Objects.requireNonNull ( getActivity () ).getSharedPreferences ( "userInfo",Context.MODE_PRIVATE );
            @SuppressLint("CommitPrefEdits")
            SharedPreferences.Editor edit = sharedPreferences.edit ();
            edit.putString ( "isVip","1" );
            String phone = sharedPreferences.getString ( "phone", "" );
            edit.apply ();
            LiveData<Boolean> booleanLiveData = HomeRepository.getHomeRepository ().postIsVip ( phone );
            booleanLiveData.observe ( getViewLifecycleOwner (), new Observer<Boolean> () {
                @Override
                public void onChanged(Boolean aBoolean) {
                    homeViewModel.isVip ().postValue ( aBoolean );
                }
            } );
        } );
        @SuppressLint("UseRequireInsteadOfGet")
        SharedPreferences preferences = Objects.requireNonNull ( getContext () ).getSharedPreferences ( "userInfo", Context.MODE_PRIVATE );
        userInfoBinding.userInfoName.setText ( preferences.getString ( "name","" ) );
        userInfoBinding.userInfoSign.setText ( preferences.getString ( "describe","" ) );
        userInfoBinding.userInfoSex.setText ( preferences.getString ( "sex","" ) );
        userInfoBinding.userInfoBir.setText ( preferences.getString ( "birthday","" ) );
        userInfoBinding.userInfoLocation.setText ( preferences.getString ( "area","" ) );
        return userInfoBinding.getRoot ();
    }
}