package com.example.youcook.ui.create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youcook.R;
import com.example.youcook.databinding.FragmentCreateBinding;
import com.example.youcook.databinding.FragmentSelectedRecipeBinding;
import com.example.youcook.ui.create.created_recipe_tag_recycler.CreateTagRecyclerAdapter;
import com.example.youcook.ui.recipes.RecipesViewModel;


public class CreateFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //LIVE DATA BINDINGS
        FragmentCreateBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create, container, false);

        CreateViewModel createViewModel = new ViewModelProvider(this).get(CreateViewModel.class);

        binding.setViewModel(createViewModel);
        binding.setLifecycleOwner(this);

        //View used for id resources
        View returningView = binding.getRoot();



        //Recycler for tag display inside create fragment
        RecyclerView recyclerView = returningView.findViewById(R.id.createRecipeTagViewer);


        //Used for optimization purposes, will probably have to be set to false.
        recyclerView.setHasFixedSize(true);

        //Layout and Adapter recycler layout horizontal for tag display
        recyclerView.setLayoutManager(new LinearLayoutManager(returningView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        CreateTagRecyclerAdapter adapter = new CreateTagRecyclerAdapter();
        recyclerView.setAdapter(adapter);


        return returningView;


    }
}