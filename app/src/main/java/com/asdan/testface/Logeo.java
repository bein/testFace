package com.asdan.testface;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;


public class Logeo extends ActionBarActivity {

    private ProfilePictureView fotoPerfil;
    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.logeo);

        fotoPerfil = (ProfilePictureView) findViewById(R.id.fotoPerfil);
        info = (TextView) findViewById(R.id.info);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends public_profile email");

        if (AccessToken.getCurrentAccessToken() != null) {
            getData();
            getFriends();
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AccessToken.getCurrentAccessToken() != null) {
                    fotoPerfil.setProfileId(null);
                }
            }
        });

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                if (AccessToken.getCurrentAccessToken() != null) {
                    getData();
                    getFriends();
                }
            }

            @Override
            public void onCancel() {
                Toast.makeText(Logeo.this, "Se ha cancelado el inicio de sesión.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(Logeo.this, "Error Se al iniciar sesión.", Toast.LENGTH_LONG).show();
            }
        });

    }

    /**
     * getData
     *
     * Función encargada de obtener un Request de la persona logeada a facebook;
     * aquí es dónde se obtiene Nombre, correo así como id.
     *
     */
    private void getData() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {

                    public void onCompleted(JSONObject object, GraphResponse response) {
                        JSONObject json = response.getJSONObject();
                        try {
                            if (json != null) {
                                String text = "<b>Nombre :</b> " + json.getString("name") + "<br><br><b>Correo :</b> "
                                        + json.getString("email");
                                info.setText(Html.fromHtml(text));
                                fotoPerfil.setProfileId(json.getString("id"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle params = new Bundle();
        params.putString("fields", "id,name,link,email,picture");
        request.setParameters(params);
        request.executeAsync();
    }

    /**
     * getFriends
     *
     * Función encargada de obtener un Request de la persona logeada a fecabook;
     * aquí es donde se obtiene una lista de amigos de la persona logeada.
     *
     */
    public void getFriends() {
        GraphRequestBatch batch = new GraphRequestBatch(GraphRequest
                .newMyFriendsRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONArrayCallback() {
                    @Override
                    public void onCompleted(JSONArray jsonArray, GraphResponse response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject();
                            JSONArray data = (JSONArray) jsonObject.get("data");

                            LinearLayout listAmigos = (LinearLayout) findViewById(R.id.ListadoAmigos);
                            listAmigos.removeAllViews();
                            LayoutInflater inf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                            for (int i = 0; i < data.length(); i++) {
                                View layout = inf.inflate(R.layout.amigo_fila,
                                        (ViewGroup) findViewById(R.layout.amigo_fila));
                                layout.setBackgroundResource(R.color.blanco);

                                JSONObject object = data.getJSONObject(i);
                                TextView nombre = (TextView) layout.findViewById(R.id.infoAmigo);
                                nombre.setText(object.getString("name"));

                                ProfilePictureView fotoPerfilAmigo = (ProfilePictureView) layout
                                        .findViewById(R.id.fotoPerfilAmigo);
                                fotoPerfilAmigo.setProfileId(null);
                                fotoPerfilAmigo.setProfileId(object.getString("id"));

                                listAmigos.addView(layout);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }));
        batch.addCallback(new GraphRequestBatch.Callback() {

            @Override
            public void onBatchCompleted(GraphRequestBatch batch) {

            }
        });
        batch.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}
