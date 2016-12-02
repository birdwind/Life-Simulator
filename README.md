# Life-Simulator  ( Just for the final project of our homework )

Features 

1.LogInSystem 使用者系統

輸入暱稱：使用者一開始只要需要輸入暱稱即可登入。

刪除帳號：使用者可以刪除該暱稱的帳號。

使用者：擁有累積經驗值，升等機制。

2. Memo　筆記本

新增：使用者可以新增項目供記載事項且選擇分類或新增分類，並且會記錄下新增日期。

刪除：點選按鈕刪除該項目。

搜尋：根據分類篩選顯示項目。


3. Schedule　行程表

行程表：提供ListView，至08:00AM開始列表，每小時為一個Item。

記錄行程：每個Item供使用者點選，並且輸入行程內容。

時段：讀取系統時間在相對應的時間Item上亮出背景。

刪除：一個按鍵清除所有行程。

儲存：供使用者儲存行程。

載入行程：儲存的項目供使用者載入使用。

是否通知：使用者決定是否要接收推播來預告下個行程。


4.CheckList　待辦事項

儲存待辦事項：為一個多選的ListView，各個Item記載事項以及Deadline。

新增：使用者新增一事項以及Deadline。

刪除：提供多選CheckBox一併刪除多個項目。

是否通知：使用者決定是否要接收推播來預告下個行程。


Regulations

1.@+id/命名規則： 命名規則為適宜名稱+該Activity名稱，筆記本的新增按鈕: @+id/inputBTN_Memo

2.常數規則：當需要傳入常數作為參數來連結各個功能，則先需要定義為類別變數public static final。


Progress

登入系統 ok

遊戲主畫面  ok 

遊戲主畫面 所有元件 (功能的Image Button)

便條紙 UI ok

便條紙TABLE ok

便條紙快取功能TABLE ok

便條紙使用 新增 ok 刪除 ok 編輯 ok 分類搜尋 ok

行程表 UI

行程表 TABLE

行程表快取TABLE

行程表 儲存行程 載入行程 刪除行程 編輯單一行程 亮出時段 通知功能 使用者設定 

CheckList UI

CheckList Table

CheckList 新增 多選刪除 通知功能 編輯


水: 

11/7 Log In System finished

11/10 Start Developing Memo

11/20 Start Developing Schedule


邱:

CheckList先交給你惹，慢慢鑽研xDD

11/6 思考人生
11/8 雛形

11/12 水:

(1) 大改資料庫系統 配合 DB Open Helper (才能讓資料庫更新時自動刪除舊表格)

* 如果你要開一個表格:
    * (1) 開一個class 繼承此類別 命名 Entity名稱_DB_Facade
    * (2) 實踐所有SQL資料操作方法
    * (3) 設私有建構子 傳遞表格名稱 及 createTable()
    * (4) 在你的facade內新增方法 getFacade() 參考 User_DB_Facade
    * (5) 在MyDbHelper中的onUpgrade(..)函數中新增一條指令實踐刪除你的表格 :
    *   你的類別名稱.getFacade().dropTable();

(2) Memo開發進度75%:
   (1)Memo頁面
   (2)Memo編輯頁面
   (3)Memo展現頁面
   (4)Memo資料庫儲存
  
11/15 水：

(1) Weak Entity 測試 Android SQL 根本沒支援 Weak entity 的設置阿 !!!!!
更新SQL語法 

11/17 水：

Memo功能完成。

11/19 水：

Schedule facade介面建立

11/26 水：

增加經驗值系統，增加卡片瀏覽模式




When finished your task, you can add some new features like 倒數日

測試status ~~~




