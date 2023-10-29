アプリ名:Moitter
目標：オンラインで文字でのコミュニケーションができるアプリを作る

<参考にしたサイト、リファレンスなど>

:SignIN SignUp
https://firebase.google.com/docs/android/setup?hl=ja
https://firebase.google.com/docs/firestore/query-data/queries
https://firebase.google.com/docs/firestore/quickstart?hl=ja
https://developer.android.com/topic/libraries/view-binding?hl=ja
https://docs.oracle.com/javase/jp/8/docs/api/java/util/HashMap.html

特にSharedPreferenceの部分は
https://developer.android.com/training/data-storage/shared-preferences?hl=ja
https://www.youtube.com/watch?v=TXKooC33cec&list=PLam6bY5NszYOhXkY7jOS4EQAKcQwkXrp4&index=3&ab_channel=ChiragKachhadiya
(21:26の部分から、アプリ間で使うSSharedPreferenceのマネージメントの仕方を参考にした。Userdata.classと動画のPreferenceManager.classコード
はほとんど同じになってしまったが、だれが書いても同じようになるプログラムであることを考慮してほしい)

:LogOut処理
トークンの入手方法や、その破棄方法、Logoutにかかわる処理はUserdataと密接にかかわるため、
https://www.youtube.com/watch?v=bm1nvUmCuII&list=PLam6bY5NszYOhXkY7jOS4EQAKcQwkXrp4&index=5&ab_channel=ChiragKachhadiya
この動画の12:56あたりを特に参考にした。　Userdataの運用方法をまねていたら、ほぼ同じコードになってしまったことを考慮してほしい。

:Threadの処理
https://qiita.com/naoi/items/f8a19d6278147e98bbc2
特にリサイクルビューの処理は上を参考にした。

リサイクルビューのクリックlistenerのつけ方は、
https://qiita.com/tkmd35/items/b49070560abbfdfff3d1
を参考にした。

リストビューだけしかやったことがなかったためかなり苦労した。
また、FireStoreの仕様やコードについては
https://firebase.google.com/docs/firestore/query-data/listen
これを大いに参考にした↑は、リアルタイムでデータベースの変更を検知できるlistenerの書き方を学べた。

リストのタイムスタンプを使ったソート方法は、
https://carey.link/java/java-list-join-sort
を参考にして作成した。

