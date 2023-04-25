package group.assignment.abcdfinal.fragments;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import group.assignment.abcdfinal.MainActivity;
import group.assignment.abcdfinal.R;
import group.assignment.abcdfinal.ReplacerActivity;


public class LoginFragment extends Fragment {

    private EditText emailEt,passwordEt;
    private TextView signUpTv,forgotTv;
    private Button loginBtn,googleSignInBtn;
    private ProgressBar progressBar;

    private static final int RC_SIGN_IN =1;

    private FirebaseAuth auth;
    GoogleSignInClient mGoogleSignInClient;


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);


        clickListener();
    }


    private void init(View view){

        emailEt = view.findViewById(R.id.emailET);
        passwordEt = view.findViewById(R.id.passwordET);
        loginBtn = view.findViewById(R.id.loginBtn);
        googleSignInBtn = view.findViewById(R.id.googleSignInBtn);
        signUpTv = view.findViewById(R.id.signUpTV);
        forgotTv = view.findViewById(R.id.forgotTV);
        progressBar = view.findViewById(R.id.progressBar);


        auth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(),gso);

    }

    private void clickListener() {

        forgotTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ReplacerActivity) getActivity()).setFragment(new ForgotPassword());
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = emailEt.getText().toString();
                String password = passwordEt.getText().toString();

                if (email.isEmpty()) {
                    emailEt.setError("Invalid Email");
                    return;
                }
                if (password.isEmpty() || password.length() < 8) {
                    passwordEt.setError("Invalid Password");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    FirebaseUser user = auth.getCurrentUser();

                                    if (!user.isEmailVerified()) {
                                        Toast.makeText(getContext(), "Please Verify Email", Toast.LENGTH_SHORT).show();
                                    }
                                    sendUserToMainActivity();
                                } else {
                                    String exception = "Error: " + task.getException().getMessage();
                                    Toast.makeText(getContext(), exception, Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
            }
        });

        googleSignInBtn.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){
            signIn();
        }
        });

        signUpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ReplacerActivity) getActivity()).setFragment(new CreateAccountFragment());
            }
        });
    }

    private void sendUserToMainActivity(){
        if (getActivity() == null)
            return;

        progressBar.setVisibility(View.GONE);
        startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
        getActivity().finish();
    }

    private void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        //Result returned from launching the Intent form GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                assert account != null;
                firebaseAuthWithGoogle(account.getIdToken());
            }catch (ApiException e){
                e.printStackTrace();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(),new OnCompleteListener<AuthResult>(){

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = auth.getCurrentUser();
                            updateUi(user);
                        }else {
                            Log.w(TAG,"signInWithCredential:failure",task.getException());
                        }
                    }
                });
    }

    private void updateUi(FirebaseUser user){
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());

        Map<String,Object> map = new HashMap<>();

        map.put("name",account.getDisplayName());
        map.put("email",account.getEmail());
        map.put("profileImage",String.valueOf(account.getPhotoUrl()));
        map.put("uid",user.getUid());
        map.put("following",0);
        map.put("followers",0);
        map.put("status"," ");

        FirebaseFirestore.getInstance().collection("Users").document(user.getUid())
                .set(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@androidx.annotation.NonNull Task<Void> task) {

                        if (task.isSuccessful()){
                            assert getActivity() !=null;
                            progressBar.setVisibility(View.GONE);
                            sendUserToMainActivity();

                        }else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(),"Error" + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }

}