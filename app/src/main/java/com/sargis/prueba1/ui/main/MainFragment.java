package com.sargis.prueba1.ui.main;


import androidx.lifecycle.Observer;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.sargis.prueba1.R;
import com.sargis.prueba1.adapter.ArticleAdapter;
import com.sargis.prueba1.model.Article;
import com.sargis.prueba1.model.ArticleFav;
import com.sargis.prueba1.ui.create.CreateFragment;
import com.sargis.prueba1.ui.login.LoginViewModel;

import java.util.List;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private LoginViewModel loginViewModel;
    private RecyclerView rv;
    private FirebaseAuth auth=FirebaseAuth.getInstance();
    private SwipeRefreshLayout swipeRefreshLayout;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.main_fragment, container, false);
       rv=(RecyclerView) v.findViewById(R.id.rv);
        FloatingActionButton fab=(FloatingActionButton)v.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO probar con getParentFragmentManager en lugar del support
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, CreateFragment.newInstance()).addToBackStack(null).commit();
            }
        });

        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setHasFixedSize(true);

        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);


        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loginViewModel = new ViewModelProvider(getActivity()).get(LoginViewModel.class);
        mViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
        mViewModel.setArticleList();
        cargarDatos();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
               cargarDatos();
            }
        });


    }

    private void cargarDatos(){
        mViewModel.getArticleList().observe(getActivity(), new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> articles) {
                mViewModel.setArticleList();
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(),""+articles.size(),Toast.LENGTH_SHORT).show();
                ArticleAdapter adapter=new ArticleAdapter(articles);
                adapter.setOnItemClickListener(new ArticleAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, Article article, int position) {
                        if(!article.getOwnerID().equals(auth.getUid()))
                            loginViewModel.addArticleToFav(new ArticleFav(article.getOwnerID(),article.getTitle()),article.getIdArticle());
                        else Snackbar.make(view, "Este es tu artículo", Snackbar.LENGTH_LONG).show();

                    }
                });
                rv.setAdapter(adapter);
            }
        });
    }

}
