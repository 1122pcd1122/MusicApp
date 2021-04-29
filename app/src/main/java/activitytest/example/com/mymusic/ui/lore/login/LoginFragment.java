package activitytest.example.com.mymusic.ui.lore.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import activitytest.example.com.mymusic.bean.UserInfo;
import activitytest.example.com.mymusic.databinding.FragmentLoginBinding;
import activitytest.example.com.mymusic.network.Resource;
import activitytest.example.com.mymusic.network.Status;
import activitytest.example.com.mymusic.ui.lore.login.bean.LoginBean;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import activitytest.example.com.mymusic.R;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

public class LoginFragment extends Fragment implements View.OnClickListener {


    private final String TAG = this.getClass ().getName ();
    private FragmentLoginBinding loginBinding;
    private LoginViewModel loginViewModel;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach ( context );
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        loginBinding = DataBindingUtil.inflate ( inflater, R.layout.fragment_login, container, false );

        loginViewModel = new ViewModelProvider ( this ).get ( LoginViewModel.class );

        return loginBinding.getRoot ();
    }

    @Override
    public void onStart() {
        super.onStart ();
        EditText password = loginBinding.Password;
        EditText phoneNumber = loginBinding.phoneNumber;
        Button loginButton = loginBinding.button;
        CheckBox isAgree = loginBinding.checkBox;
        loginButton.setOnClickListener ( view -> {
            if (phoneNumber.getText ().toString ().isEmpty ()){
                Toast.makeText ( getContext (), "请输入手机号", Toast.LENGTH_SHORT ).show ();
                return;
            }else if (password.getText ().toString ().isEmpty ()){
                Toast.makeText ( getContext (), "请输入密码", Toast.LENGTH_SHORT ).show ();
                return;
            }else if (!isAgree.isChecked ()){
                Toast.makeText ( getContext (), "请勾选同意用户协议", Toast.LENGTH_SHORT ).show ();
                return;
            }

            LoginBean loginBean = new LoginBean ( phoneNumber.getText ().toString (),password.getText ().toString () );
            Log.d ( TAG, "开始发送...");
            LiveData<Resource<UserInfo>> userInfoLiveData = loginViewModel.loginIng ( loginBean );
            Log.d ( TAG,"发送完成..." );

            NavController navController = NavHostFragment.findNavController ( this );
            userInfoLiveData.observe ( this, userInfoResource -> {
                Log.d ( TAG,"观察中......" );
                Log.d ( TAG,userInfoResource.getMessage () );
                if (userInfoResource.getStatus ().getCode () == Status.SUCCESS.getCode ()){
                    saveUserInfo ( userInfoResource );
                    navController.navigate ( R.id.navigation_home);
                }else if (userInfoResource.getStatus ().getCode () == Status.LOADING.getCode ()){
                    Toast.makeText ( getContext (), userInfoResource.getMessage (), Toast.LENGTH_SHORT ).show ();
                }else if (userInfoResource.getStatus ().getCode () == Status.ERROR.getCode ()){
                    Toast.makeText ( getContext (), userInfoResource.getMessage (), Toast.LENGTH_SHORT ).show ();
                }
            } );


        } );
    }

    /**
     * 保存用户信息
     * @param userInfoResource 用户信息
     */
    @SuppressLint("ApplySharedPref")
    private void saveUserInfo(Resource<UserInfo> userInfoResource) {
        UserInfo data = userInfoResource.getData ();
        SharedPreferences userInfo = getContext ().getSharedPreferences ( "userInfo", Context.MODE_PRIVATE );
        @SuppressLint("CommitPrefEdits")
        SharedPreferences.Editor edit = userInfo.edit ();
        if (data != null){
            edit.putString ( "phone",data.getPhone () );
            edit.putInt ( "status",data.getStatus () );
            edit.putString ( "name",data.getName () );
            edit.putString ( "sex",data.getSex () );
            edit.putString ( "birthday",data.getBirthday () );
            edit.putString ( "area",data.getArea () );
            edit.putString ( "idCard",data.getIdCard () );
            edit.putString ( "photo",data.getPhoto () );
            edit.putString ( "describe",data.getDescribe () );
            edit.putString ( "isVip",data.getPermissions () );
            edit.commit ();
        }
    }

    @Override
    public void onResume() {
        super.onResume ();
    }

    @Override
    public void onClick(View view) {

    }
}