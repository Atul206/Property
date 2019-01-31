package ui.activity;

import android.Manifest;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.yarolegovich.lovelydialog.LovelyInfoDialog;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.MessageButtonBehaviour;
import agency.tango.materialintroscreen.SlideFragmentBuilder;
import survey.property.roadster.com.surveypropertytax.R;
import ui.HomeActivity;

public class LoginIntroActivity extends MaterialIntroActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = LoginIntroActivity.class.getSimpleName();

    private FirebaseAuth firebaseAuth;

    private FirebaseAuth.AuthStateListener mListener;

    private GoogleApiClient mGoogleClientApi;

    private static final int RC_SIGN_IN = 1;

    private  GoogleSignInOptions gso;

    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        mListener = firebaseAuth -> {

            if (firebaseAuth.getCurrentUser() != null) {
                //startActivity(new Intent(LoginActivity.this, MainActivity.class));
                //finish();
            }
        };

        initGSignInstace();
        initGoogleClientApiInstace();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.addAuthStateListener(mListener);


        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.colorPrimary)
                        .buttonsColor(R.color.colorAccent)
                        .image(R.drawable.property_icon)
                        .title("Murfi Express")
                        .description("App provide property tax survey")
                        .build(),
                new MessageButtonBehaviour(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(firebaseAuth.getCurrentUser() == null) {
                            signIn();
                            showMessage("Please wait ! connecting ...");
                        }else{
                            startActivity(new Intent(LoginIntroActivity.this, HomeActivity.class));
                            onFinish();
                            //open new activity
                        }
                    }
                },  firebaseAuth.getCurrentUser() != null ? getString(R.string.enter) : getString(R.string.login)));

        enableLastSlideAlphaExitTransition(false);
    }

    public void initGSignInstace(){
        if(null == gso) {
            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
        }
    }

    public void initGoogleClientApiInstace(){
        if(null == mGoogleClientApi){
            mGoogleClientApi = new GoogleApiClient.Builder(getApplicationContext()).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                @Override
                public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                    Snackbar.make(findViewById(R.id.main_layout), "Something went wrong!!.", Snackbar.LENGTH_SHORT).show();

                }
            })
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }
    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleClientApi);
        showMessage("fetching account...");
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        showMessage("Please select account...");
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            // Google Sign In was successful, authenticate with Firebase
            GoogleSignInAccount account = result.getSignInAccount();
            firebaseAuthWithGoogle(account);
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount account){
        if(account == null || account.getId() == null) {
            Snackbar.make(findViewById(R.id.main_layout), "Please sign in first", Snackbar.LENGTH_SHORT).show();
            return;
        }
        Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
        showMessage("Authenticating...");
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            //Snackbar.make(findViewById(R.id.main_layout), "Authentication Success.", Snackbar.LENGTH_SHORT).show();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            showMessage("Authentication success");
                            new LovelyStandardDialog(context, LovelyStandardDialog.ButtonLayout.VERTICAL)
                                    .setTopColorRes(android.R.color.white)
                                    .setIcon(R.drawable.ic_success)
                                    .setTitle("Login success")
                                    .setMessage(user.getEmail())
                                    .setPositiveButton(android.R.string.ok, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            startActivity(new Intent(LoginIntroActivity.this, HomeActivity.class));
                                            onFinish();
                                        }
                                    })
                                    .setNegativeButton(android.R.string.no, null)
                                    .show();
                            //updateUI(user);
                        } else {
                            showMessage("Authenticaton fail! ");
                            //startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            //updateUI(null);
                            //finish();
                        }
                    }
                });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onFinish() {
        super.onFinish();
        this.finish();
    }
}
