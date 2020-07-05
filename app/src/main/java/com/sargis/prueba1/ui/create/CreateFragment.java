package com.sargis.prueba1.ui.create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.sargis.prueba1.R;
import com.sargis.prueba1.model.Article;
import com.sargis.prueba1.repository.FirestoreRepository;

public class CreateFragment extends Fragment {
    private ExtendedFloatingActionButton fab;
    private  EditText title,description;
    private FirebaseAuth auth=FirebaseAuth.getInstance();


    public static CreateFragment newInstance() {
        return new CreateFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.create_fragment, container, false);
         title=(EditText) v.findViewById(R.id.et_title);
         description=(EditText) v.findViewById(R.id.et_desc);
         fab =(ExtendedFloatingActionButton)v.findViewById(R.id.fab_extend);
        return v;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirestoreRepository.createArticle(new Article(
                        title.getText().toString(),
                        description.getText().toString(),
                        auth.getUid(),
                        auth.getCurrentUser().getEmail()
                ));
                removeThisFragment();

            }
        });

    }

    private void removeThisFragment(){
        getParentFragmentManager().popBackStack();
    }

}

