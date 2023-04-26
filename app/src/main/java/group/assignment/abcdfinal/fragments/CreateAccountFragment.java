package group.assignment.abcdfinal.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import group.assignment.abcdfinal.MainActivity;
import group.assignment.abcdfinal.R;
import group.assignment.abcdfinal.ReplacerActivity;


public class CreateAccountFragment extends Fragment {

    private EditText nameEt,emailEt,passwordEt,confirmPasswordEt;
    private TextView loginTv;
    private Button signUpBtn;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    public static final String EMAIL_REGEX = "^(.+)@(.+)$";

    public CreateAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        init(view);

        clickListener();
    }



    private void init(View view) {
        nameEt = view.findViewById(R.id.nameET);
        emailEt = view.findViewById(R.id.emailET);
        passwordEt = view.findViewById(R.id.passwordET);
        confirmPasswordEt = view.findViewById(R.id.confrimPasswordET);
        loginTv = view.findViewById(R.id.loginTV);
        signUpBtn = view.findViewById(R.id.signUpBtn);
        progressBar = view.findViewById(R.id.progressBar);

        auth=FirebaseAuth.getInstance();
    }

    private void clickListener() {

        loginTv.setOnClickListener(view -> {
            ((ReplacerActivity)getActivity()).setFragment(new LoginFragment());
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name =nameEt.getText().toString();
                String email = emailEt.getText().toString();
                String password = passwordEt.getText().toString();
                String confirmPass = confirmPasswordEt.getText().toString();

                if (name.isEmpty() || name.equals(" ")){
                    nameEt.setError("Please input a valid name");
                    return;
                }

                if (email.isEmpty() || !email.matches(EMAIL_REGEX)){
                    emailEt.setError("Please input a valid email");
                    return;
                }

                if (password.isEmpty() || password.length() < 8 ){
                    passwordEt.setError("Please input a valid password");
                    return;
                }

                if (!password.equals(confirmPass)){
                    confirmPasswordEt.setError("Please not match");
                    return;
                }

                progressBar.setVisibility(view.VISIBLE);

                createAccount(name,email,password);

            }
        });

    }


    private void createAccount(String name,String email,String password){
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@androidx.annotation.NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            FirebaseUser user = auth.getCurrentUser();

                            UserProfileChangeRequest.Builder request = new UserProfileChangeRequest.Builder();
                            request.setDisplayName(name);
                            user.updateProfile(request.build());

                            user.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(getContext(),"Email verify sent",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                            uploadUser(user,name,password);

                        }else {
                            progressBar.setVisibility(View.GONE);
                            String exception = task.getException().getMessage();
                            Toast.makeText(getContext(),"Error" + exception ,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void uploadUser(FirebaseUser user,String name,String email){
        List<String> list = new ArrayList<>();
        List<String> list1 = new ArrayList<>();

        Map<String,Object> map = new HashMap<>();

        map.put("name",name);
        map.put("email",email);
        map.put("profileImage"," ");
        map.put("uid",user.getUid());
        map.put("status"," ");
        map.put("search", name.toLowerCase());

        map.put("following",list);
        map.put("followers",list1);

        FirebaseFirestore.getInstance().collection("Users").document(user.getUid())
                .set(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@androidx.annotation.NonNull Task<Void> task) {

                        if (task.isSuccessful()){
                            assert getActivity() !=null;
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class ));
                            getActivity().finish();
                        }else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(),"Error" + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });

    }


}