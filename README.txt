アプリ名:Moitter
目標：オンラインで文字でのコミュニケーションができるリアルタイム5chのようなアプリを作る

<参考にしたサイトなど>

:SignIN SignUp
https://firebase.google.com/docs/android/setup?hl=ja
https://firebase.google.com/docs/firestore/query-data/queries
https://firebase.google.com/docs/firestore/quickstart?hl=ja
https://developer.android.com/topic/libraries/view-binding?hl=ja
https://docs.oracle.com/javase/jp/8/docs/api/java/util/HashMap.html

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


