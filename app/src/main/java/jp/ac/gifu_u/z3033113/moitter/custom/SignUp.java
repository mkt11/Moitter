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

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import jp.ac.gifu_u.z3033113.moitter.databinding.ActivitySignUpBinding;

public class SignUp extends AppCompatActivity {
    private ActivitySignUpBinding signup;
    private InputMethodManager Keyboard_checker;
    private LinearLayout forcus;
    private Userdata User_Data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signup =  ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(signup.getRoot());

        Keyboard_checker = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        forcus = signup.mainLayout;
        User_Data = new Userdata(getApplicationContext());
        signup.progressBar.setVisibility(View.INVISIBLE);
        any_button_click_Listeners();

    }

    private void signUp(){
        //ボタンを押したときにキーボードは隠れる
        forcus.requestFocus();
        Keyboard_checker.hideSoftInputFromWindow(forcus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        //例外処理
        if(signup.name.getText().toString().isEmpty()){
            showToast("名前を入力してください");
        }
        else if(signup.name.getText().toString().length()>10){
            showToast("名前は10文字以内で入力してください");
        }
        else if(signup.email.getText().toString().isEmpty()){
            showToast("Emailを入力してください");
        }
        else if(signup.email.getText().toString().length()>20){
            showToast("Emailは20文字以内で入力してください");
        }
        else if(checkEmail(signup.email.getText().toString())){
            showToast("有効なEmailを入力してください");
        }
        else if(signup.password.getText().toString().isEmpty()){
            showToast("パスワードを入力してください");
        }
        else if(signup.password.getText().toString().length()>20){
            showToast("パスワードは20文字以内で入力してください");
        }
        else if(signup.confirmpassword.getText().toString().isEmpty()){
            showToast("確認用パスワードを入力してください");
        }
        else if(!signup.password.getText().toString().equals(signup.confirmpassword.getText().toString())){
            showToast("パスワードと確認用パスワードが一致しません");
        }
        //本題
        else{
        signup.progressBar.setVisibility(View.VISIBLE);
        setUser_Data();



        }

    }


    private Boolean checkEmail(String email){
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return false;
        }else{
            return true;
        }
    }


    private void setUser_Data(){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String,Object> userdata = new HashMap<>();
        userdata.put("Name",signup.name.getText().toString());
        userdata.put("Email",signup.email.getText().toString());
        userdata.put("Password",signup.password.getText().toString());

        //keyの種類
        //Name ::ユーザーネームを保存しておく
        //Email :: メールを保存しておく
        //ID ::Firebase側で生成される個人紐付けIDを保存する
        //Password ::パスワードを保存しておく
        //Signed ::サインインしているかどうかを確認する
        //databaseに登録するのはUsersという名前にしておく　これも記憶しておく

        database.collection("Users").add(userdata).addOnSuccessListener(documentReference -> {
            User_Data.putBoolean_user("Signed",true);
            User_Data.putString_user("Name",signup.name.getText().toString());
            User_Data.putString_user("ID",documentReference.getId());
            signup.progressBar.setVisibility(View.INVISIBLE);
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        });
    }


    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void any_button_click_Listeners(){
        signup.buttonSignup.setOnClickListener(view -> signUp());
        signup.textBack.setOnClickListener(view -> onBackPressed());

    }
}