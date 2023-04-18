package group.assignment.abcdfinal.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import group.assignment.abcdfinal.R;
import group.assignment.abcdfinal.adapter.HomeAdapter;
import group.assignment.abcdfinal.model.HomeModel;

public class Home extends Fragment {

    private RecyclerView recyclerView;

    HomeAdapter adapter;
    private List<HomeModel> list;
    private FirebaseUser user;

    DocumentReference reference;

    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        reference = FirebaseFirestore.getInstance().collection("Posts").document(user.getUid());

        list = new ArrayList<>();
        adapter = new HomeAdapter(list, getContext());
        recyclerView.setAdapter(adapter);

        loadDataFromFireStore();
    }



    private void init(View view) {

        Toolbar toolbar = view.findViewById(R.id.toolBar);
        if (getActivity() != null)
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        recyclerView.findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseAuth auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
    }

    private void loadDataFromFireStore() {
        list.add(new HomeModel("Sy","01/11/2020","","","000001",100));
        list.add(new HomeModel("Akau","02/01/2020","","","512378",10));
        list.add(new HomeModel("Abu","11/11/2020","","","111111",20));
        list.add(new HomeModel("Ali","09/12/2020","","","123331",8));

        adapter.notifyDataSetChanged();
    }
}