package jp.ac.gifu_u.z3033113.moitter.custom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import jp.ac.gifu_u.z3033113.moitter.R;
import jp.ac.gifu_u.z3033113.moitter.databinding.ActivitySigninBinding;

public class SignIn extends AppCompatActivity {
    //findbyidは古いと聞いたので、練習もかねてDataBindingをつかってみる
    private ActivitySigninBinding signin;
    private InputMethodManager Keyboard_checker;
    private LinearLayout forcus;
    private Userdata User_Data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signin = ActivitySigninBinding.inflate(getLayoutInflater());

        setContentView(signin.getRoot());
        any_button_click_Listeners();
        Keyboard_checker = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        forcus = signin.mainLayout;
        User_Data = new Userdata(getApplicationContext());
        signin.progressBar.setVisibility(View.INVISIBLE);

        if(User_Data.getBoolean_user("Singed")){
            startActivity(new Intent(getApplicationContext(), SignUp.class));
        }

    }

    private void signIn(){
        //キーボードを隠す処理
        forcus.requestFocus();
        Keyboard_checker.hideSoftInputFromWindow(forcus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        //例外処理
        if(signin.email.getText().toString().isEmpty()){
            showToast("Emailを入力してください");
        }
        else if(signin.email.getText().toString().length()>20){
            showToast("Emailは20文字以内で入力してください");
        }
        else if(signin.password.getText().toString().isEmpty()){
            showToast("パスワードを入力してください");
        }
        else if(signin.password.getText().toString().length()>20){
            showToast("パスワードは20文字以内で入力してください");
        }
        else if(checkEmail(signin.email.getText().toString())){
            showToast("有効なEmailを入力してください");
        }
        //本題
        else{
            signin.progressBar.setVisibility(View.VISIBLE);
            check_DataFirebase();
            signin.progressBar.setVisibility(View.INVISIBLE);

        }
    }

    private void any_button_click_Listeners(){
        signin.button.setOnClickListener(view -> signIn());
        signin.textNewaccount.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), SignUp.class)));
    }




    private Boolean checkEmail(String email){
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return false;
        }else{
            return true;
        }
    }

    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void check_DataFirebase(){

        //keyの種類
        //Name ::ユーザーネームを保存しておく
        //Email :: メールを保存しておく
        //ID ::Firebase側で生成される個人紐付けIDを保存する
        //Password ::パスワードを保存しておく
        //Signed ::サインインしているかどうかを確認する
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("Users")
                .whereEqualTo("Email",signin.email.getText().toString())
                .whereEqualTo("Password",signin.password.getText().toString())
                .get()
                .addOnCompleteListener(task -> {
                    task.addOnSuccessListener(runnable -> {
                        if(task.getResult() != null && task.getResult().getDocuments().size()>0) {
                            DocumentSnapshot document = task.getResult().getDocuments().get(0);
                            User_Data.putBoolean_user("Signed", true);
                            User_Data.putString_user("ID", document.getId());
                            User_Data.putString_user("Name", document.getString("Name"));
                            User_Data.putString_user("Email", document.getString("Email"));
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else{
                            showToast("アカウントが存在しません");
                        }
                    });
                });


    }

}
