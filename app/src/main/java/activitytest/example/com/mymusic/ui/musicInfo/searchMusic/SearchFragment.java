package activitytest.example.com.mymusic.ui.musicInfo.searchMusic;

import android.os.Bundle;

import activitytest.example.com.mymusic.bean.advice.Advice;
import activitytest.example.com.mymusic.bean.advice.Data;
import activitytest.example.com.mymusic.bean.advice.RecordDatas;
import activitytest.example.com.mymusic.databinding.FragmentSearchBinding;
import activitytest.example.com.mymusic.network.Resource;
import activitytest.example.com.mymusic.network.Status;
import activitytest.example.com.mymusic.ui.lore.login.LoginFragment;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import activitytest.example.com.mymusic.R;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;


public class SearchFragment extends Fragment {

    private FragmentSearchBinding fragmentSearchBinding;
    private SearchViewModel searchViewModel;
    private final SearchFragment searchFragment = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentSearchBinding = DataBindingUtil.inflate ( inflater, R.layout.fragment_search, container, false );
        searchViewModel = new ViewModelProvider ( this ).get ( SearchViewModel.class );
        return fragmentSearchBinding.getRoot ();
    }

    @Override
    public void onStart() {
        super.onStart ();

        SearchView searchView = fragmentSearchBinding.searchView;
        searchView.setQueryHint ( "请输入内容" );
        searchView.setIconified ( false );
        searchView.onActionViewExpanded ();
        searchView.setIconifiedByDefault(false);//默认为true在框内，设置false则在框外
        ListView adviceList = fragmentSearchBinding.adviceList;

        searchView.setOnQueryTextListener ( new SearchView.OnQueryTextListener () {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Bundle bundle = new Bundle ();
                bundle.putString ( "content_title",query );
                NavController navController = NavHostFragment.findNavController ( searchFragment );
                navController.navigate ( R.id.action_searchFragment_to_MContentFragment,bundle );
                Navigation.findNavController ( getView () ).navigate ( R.id.MContentFragment,bundle );
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                LiveData<Resource<Advice>> resourceLiveData = searchViewModel.listAdvice ( newText );
                resourceLiveData.observe ( getViewLifecycleOwner (), adviceResource -> {
                    ArrayAdapter<String> arrayAdapter = null;

                    if (adviceResource.getStatus ().getCode () == Status.SUCCESS.getCode ()){
                        List<String> adviceList = new ArrayList<> ();

                        //解析模糊查询集合数据
                        for (Data next : adviceResource.getData ().getData ()) {
                            List<RecordDatas> recordDatas = next.getRecordDatas ();
                            for (int i = 0; i < recordDatas.size (); i++) {
                                adviceList.add ( recordDatas.get ( i ).getHintInfo () );
                            }
                        }

                        //设置适配器
                        arrayAdapter = new ArrayAdapter<> ( getContext (), android.R.layout.simple_list_item_1,adviceList);

                    }

                    adviceList.setAdapter ( arrayAdapter );

                    adviceList.setOnItemClickListener ( (adapterView, view, i, l) -> {
                        TextView textView = (TextView) view;
                        onQueryTextSubmit ( textView.getText ().toString () );
                    } );
                } );



                return false;
            }
        } );
    }
}