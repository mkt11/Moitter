package jp.ac.gifu_u.z3033113.moitter.custom;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import jp.ac.gifu_u.z3033113.moitter.databinding.ActivityThreadMessageBinding;
import jp.ac.gifu_u.z3033113.moitter.service.Message;
import jp.ac.gifu_u.z3033113.moitter.service.Message_Adapter;
import jp.ac.gifu_u.z3033113.moitter.service.Thread;
import jp.ac.gifu_u.z3033113.moitter.service.Thread_Adapter;

public class Thread_Message extends AppCompatActivity {

    private ActivityThreadMessageBinding threadMessage;
    private Thread thread;
    private FirebaseFirestore database;
    private List<Message> messages;
    private String threadId;
    private Userdata User_Data;
    private List<Message> messageList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        threadMessage = ActivityThreadMessageBinding.inflate(getLayoutInflater());
        setContentView(threadMessage.getRoot());
        User_Data = new Userdata(getApplicationContext());
        database = FirebaseFirestore.getInstance();
        //クリックしたときに与えられるthreadの情報をここで与える。　別アクティビティに値を渡すときにはこれを使う。
        any_button_click_Listeners();
        thread = (Thread)getIntent().getSerializableExtra("thread");
        //スレッドのidを取得しておく
        threadId = thread.threadId;
        threadMessage.threadTitle.setText(thread.thread_name);
        //リサイクルビューを作る
        getMessage(threadId);
        message_listener(threadId);

    }

    //keyの種類
    //Name ::ユーザーネームを保存しておく
    //Email :: メールを保存しておく
    //ID ::Firebase側で生成される個人紐付けIDを保存する
    //Password ::パスワードを保存しておく
    //Signed ::サインインしているかどうかを確認する
    //databaseに登録するのはUsersという名前にしておく　これも記憶しておく

    private void send_message_button(){
        HashMap<String,Object> message_instance = new HashMap<>();
        message_instance.put("message",threadMessage.messageInput.getText().toString());
        message_instance.put("user_name",User_Data.getString_user("Name"));
        message_instance.put("userId",User_Data.getString_user("ID"));
        message_instance.put("threadId",threadId);
        message_instance.put("date",new Date());
        database.collection("Messages").add(message_instance);
        threadMessage.messageInput.setText(null);
    }


  private void message_listener(String threadId){
      FirebaseFirestore messages = FirebaseFirestore.getInstance();
      messages.collection("Messages").addSnapshotListener(new EventListener<QuerySnapshot>() {
          @Override
          public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
              if(error != null){
                  showToast("エラー");
                  return;
              }

              if(value != null){

                  getMessage(threadId);



              }
          }
      });
  }


    private void getMessage(String thisThreadId){
        FirebaseFirestore messages = FirebaseFirestore.getInstance();
        messages.collection("Messages").get().addOnSuccessListener(runnable ->
        {

            if(!runnable.isEmpty()){
                messageList = new ArrayList<>();
                for (QueryDocumentSnapshot message_data:runnable){

                    if(thisThreadId.equals(message_data.getString("threadId"))) {
                        Message message = new Message();
                        message.message = message_data.getString("message");
                        message.user_name = message_data.getString("user_name");
                        message.userId = message_data.getId();
                        message.threadId = message_data.getString("threadId");
                        message.date = message_data.getTimestamp("date");
                        messageList.add(message);
                    }


                }

                if(messageList.size()>0){
                    //messageリストは時間順にソートされる
                    messageList.sort(Comparator.comparing(Message::getDate));
                    Message_Adapter message_adapter = new Message_Adapter(messageList,thisThreadId);
                    threadMessage.messageDoneRecycler.setAdapter(message_adapter);
                    threadMessage.messageDoneRecycler.smoothScrollToPosition(messageList.size());
                }

            }
        });
    }

    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    private void any_button_click_Listeners(){
        threadMessage.backButton.setOnClickListener(view -> onBackPressed());
        threadMessage.sendButton.setOnClickListener(view -> send_message_button());
    }

}