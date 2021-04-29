package activitytest.example.com.mymusic.ui.lore.register;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import activitytest.example.com.mymusic.bean.UserInfo;
import activitytest.example.com.mymusic.databinding.FragmentRegisterBinding;
import activitytest.example.com.mymusic.network.Resource;
import activitytest.example.com.mymusic.network.Status;
import activitytest.example.com.mymusic.ui.lore.login.register.bean.RegisterBean;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import activitytest.example.com.mymusic.R;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding registerBinding;
    private RegisterViewModel registerViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        registerBinding = DataBindingUtil.inflate ( inflater, R.layout.fragment_register, container, false );
        registerViewModel = new ViewModelProvider ( this ).get ( RegisterViewModel.class );

        return registerBinding.getRoot ();
    }

    @Override
    public void onStart() {
        super.onStart ();

        CheckBox regAgreement = registerBinding.regAgreement;
        Button regButton = registerBinding.regButton;
        EditText regPhoneNumber = registerBinding.regPhoneNumber;
        EditText regPassword = registerBinding.regPassword;
        EditText regConfirmPassWord = registerBinding.regConfirmPassWord;
        EditText regUserName = registerBinding.regUserName;

        regButton.setOnClickListener ( view -> {
            if (regPhoneNumber.getText ().toString ().isEmpty ()){
                Toast.makeText ( getContext (), "请输入手机号", Toast.LENGTH_SHORT ).show ();
                return;
            }else if (regPassword.getText ().toString ().isEmpty ()){
                Toast.makeText ( getContext (), "请输入密码", Toast.LENGTH_SHORT ).show ();
                return;
            }else if (regConfirmPassWord.getText ().toString ().isEmpty ()){
                Toast.makeText ( getContext (), "请再次输入密码", Toast.LENGTH_SHORT ).show ();
                return;
            }else if (regUserName.getText ().toString ().isEmpty ()){
                Toast.makeText ( getContext (), "请输入用户名", Toast.LENGTH_SHORT ).show ();
            } else if (!regPassword.getText ().toString ().equals ( regConfirmPassWord.getText ().toString () )){
                Toast.makeText ( getContext (), "两次密码不一样", Toast.LENGTH_SHORT ).show ();
                return;
            }else if (!regAgreement.isChecked ()){
                Toast.makeText ( getContext (), "请确认用户协议", Toast.LENGTH_SHORT ).show ();
                return;
            }

            RegisterBean registerBean = new RegisterBean ( regPhoneNumber.getText ().toString (),regUserName.getText ().toString (),regPassword.getText ().toString (),regConfirmPassWord.getText ().toString () );
            LiveData<Resource<UserInfo>> resourceLiveData = registerViewModel.registerIng ( registerBean );

            NavController navController = NavHostFragment.findNavController ( this );
            resourceLiveData.observe ( this, new Observer<Resource<UserInfo>> () {
                @Override
                public void onChanged(Resource<UserInfo> userInfoResource) {
                    if (userInfoResource.getStatus ().getCode () == Status.SUCCESS.getCode ()){
                        saveUserInfo ( userInfoResource );
                        navController.navigate ( R.id.navigation_home);
                    }else if (userInfoResource.getStatus ().getCode () == Status.LOADING.getCode ()){
                        Toast.makeText ( getContext (), "注册失败", Toast.LENGTH_SHORT ).show ();
                    }else if (userInfoResource.getStatus ().getCode () == Status.ERROR.getCode ()){
                        Toast.makeText ( getContext (), "网络连接失败", Toast.LENGTH_SHORT ).show ();
                    }
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
            edit.commit ();
        }
    }


    @Override
    public void onStop() {
        super.onStop ();
    }
}