package jp.ac.gifu_u.z3033113.moitter.custom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.ac.gifu_u.z3033113.moitter.databinding.ActivityMainBinding;
import jp.ac.gifu_u.z3033113.moitter.service.Thread;
import jp.ac.gifu_u.z3033113.moitter.service.Thread_Adapter;

public class MainActivity extends AppCompatActivity implements Thread_Adapter.Thread_Listener {
    private ActivityMainBinding main;
    private Userdata User_Data;

    //keyの種類
    //Name ::ユーザーネームを保存しておく
    //Email :: メールを保存しておく
    //ID ::Firebase側で生成される個人紐付けIDを保存する
    //Password ::パスワードを保存しておく
    //Signed ::サインインしているかどうかを確認する
    //Token :: トークン

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(main.getRoot());
        User_Data = new Userdata(getApplicationContext());
        any_button_click_Listeners();
        main.name.setText(User_Data.getString_user("Name"));
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(task
                -> New_add_token(task));
        getThread_list();
    }

    private void any_button_click_Listeners(){
        main.logout.setOnClickListener(view -> logout());
    }

    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void getThread_list(){
        FirebaseFirestore threads = FirebaseFirestore.getInstance();
        threads.collection("Threads").get().addOnSuccessListener(runnable ->
        {

            if(!runnable.isEmpty()){
                List<Thread> thread_list = new ArrayList<>();
                for (QueryDocumentSnapshot thread_data:runnable){
                    Thread thread = new Thread();
                    thread.thread_name = thread_data.getString("Thread_Name");
                    thread.counter = thread_data.getString("Thread_Counter");
                    thread.threadId = thread_data.getId();
                    thread_list.add(thread);
                }

                if(thread_list.size()>0){
                    //押されたことがわかるスレッド一覧を生成していく
                    Thread_Adapter thread_adapter = new Thread_Adapter(this,thread_list);
                    main.ThreadList.setAdapter(thread_adapter);
                    showToast("スレッドの読み込みが完了しました");
                }
            }
        });
    }


    private void New_add_token(String token){
        FirebaseFirestore userdata = FirebaseFirestore.getInstance();
        DocumentReference documentReference =
                userdata.collection("Users").document(
                        User_Data.getString_user("ID")
                );
        HashMap<String,Object> user_data = new HashMap<>();
        user_data.put("Token", token);
        documentReference.update(user_data).addOnSuccessListener(view ->
                showToast("トークンを入手")
           );
    }
    //戻るボタンが押されたときにもログアウト処理を行う
    @Override
    public void onBackPressed(){
        logout();
    }

    private void logout(){
        FirebaseFirestore userdata = FirebaseFirestore.getInstance();
        //今ログインしているユーザーのIDで検索をかけて　トークンを削除する
        DocumentReference documentReference =
                userdata.collection("Users").document(
                        User_Data.getString_user("ID")
                );
        HashMap<String,Object> user_data = new HashMap<>();
        user_data.put("Token", FieldValue.delete());
        documentReference.update(user_data).addOnSuccessListener(unused ->
        { User_Data.clear();
            startActivity(new Intent(getApplicationContext(),SignIn.class));
        });
        showToast("サインアウトしています");
    }

    @Override
    public void Thread_click(Thread thread) {
        startActivity(new Intent(getApplicationContext(),Thread_Message.class)
                .putExtra("thread",thread));
    }
}