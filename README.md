行動裝置程式設計
==
## 課程與程式簡介
- 測量系選修課，3學分，學習使用java建立Android行動裝置應用程式，共有九次個人作業與一次分組期末專題
  
- [作業一](https://github.com/janiceHuuu/113-1-mobile-application-programming/tree/main/F64126147_CH02_practice1)：設計可以對TextView放大、縮小、修改文字顏色、粗體、斜體、清除、還原等功能的App
  - [特殊設計](https://github.com/janiceHuuu/113-1-mobile-application-programming/blob/main/F64126147_CH02_practice1/F64126147_%E8%83%A1%E7%91%80%E7%9C%9F_%E7%B7%B4%E7%BF%92%E4%B8%80%E7%89%B9%E6%AE%8A%E8%A8%AD%E8%A8%88.pdf)
    - 偵測使用者是否有輸入文字的防呆功能
    - 更加美觀的版面設計
      
- [作業二](https://github.com/janiceHuuu/113-1-mobile-application-programming/tree/main/F64126147_CH03_practice2)：設計二、十、十六進位的進位轉換器
  
- [作業三](https://github.com/janiceHuuu/113-1-mobile-application-programming/tree/main/F64126147_CH04_practice3)：設計一個會在輸入數字倍數震動的計數按鈕震動器
  
- [作業四](https://github.com/janiceHuuu/113-1-mobile-application-programming/tree/main/F64126147_CH05_practice4)：設計一個點餐系統
  
- [作業五](https://github.com/janiceHuuu/113-1-mobile-application-programming/tree/main/F64126147_CH06_practice5)：設計一個高鐵票價查詢系統
  - [特殊設計](https://github.com/janiceHuuu/113-1-mobile-application-programming/blob/main/F64126147_CH06_practice5/F64126147_%E8%83%A1%E7%91%80%E7%9C%9F_%E7%B7%B4%E7%BF%92%E4%BA%94%E7%89%B9%E6%AE%8A%E8%A8%AD%E8%A8%88.pdf)
    - 使用兩種監聽器(CompoundButton.OnCheckedChangeListener、TextView.OnEditorActionListener)以完成票種和張數的選擇功能。共有六種票種，分別為全票、敬老票、愛心票、自由座、兒童票和團體票，App可依各票種的優惠計算票價
    - 新增團體票最少需購買11張的防呆功能
      
- [作業六](https://github.com/janiceHuuu/113-1-mobile-application-programming/tree/main/F64126147_CH07_practice6)：設計一個購票系統
  - [特殊設計](https://github.com/janiceHuuu/113-1-mobile-application-programming/blob/main/F64126147_CH07_practice6/F64126147_%E8%83%A1%E7%91%80%E7%9C%9F_%E7%B7%B4%E7%BF%92%E5%85%AD%E7%89%B9%E6%AE%8A%E8%A8%AD%E8%A8%88.pdf)
    - 時間的防呆功能——考慮到「購票」在正常情況下，應該只能買「現在」或「未來」的票，因此當選擇的時間為「過去」時，顯示一個AlertDialog提示使用者重新輸入時間

- [作業七](https://github.com/janiceHuuu/113-1-mobile-application-programming/tree/main/F64126147_CH0809_practice7)：設計一個可開啟對應店家Google搜尋頁面的最愛店家清單
  - [特殊設計](https://github.com/janiceHuuu/113-1-mobile-application-programming/blob/main/F64126147_CH0809_practice7/F64126147_%E8%83%A1%E7%91%80%E7%9C%9F_%E7%B7%B4%E7%BF%92%E4%B8%83%E7%89%B9%E6%AE%8A%E8%A8%AD%E8%A8%88.pdf)
    - 加入一搜尋匡SearchView以供使用者搜尋店家，以避免清單長時，難以找到特定店家的情況
    - 因應使用者不知道該吃什麼的情況，將ListView的最後一個項目設為隨機店家，當使用者按下該項目時，隨機選擇一清單內的店家，開啟該店家的Google搜尋網頁
    - 運用Snackbar提示使用者是否有成功新增店家於清單中
    - 運用Toast提示使用者在新增店家時，店家名稱是否為空，以及在隨機店家時，清單是否為空
      
- [作業八](https://github.com/janiceHuuu/113-1-mobile-application-programming/tree/main/F64126147_CH12_practice8)：設計一個會隨手機晃動，而移動行動軌跡的小精靈遊戲
  - [特殊設計](https://github.com/janiceHuuu/113-1-mobile-application-programming/blob/main/F64126147_CH12_practice8/F64126147_%E8%83%A1%E7%91%80%E7%9C%9F_%E7%B7%B4%E7%BF%92%E5%85%AB%E7%89%B9%E6%AE%8A%E8%A8%AD%E8%A8%88.pdf)
    - 當玩家獲得四分，便可選擇重複遊玩同一關卡或到另一關卡遊玩
    - 增加第二關，其中新增炸彈，當小精靈吃到炸彈即扣分，提高遊戲難度
    
- [作業九](https://github.com/janiceHuuu/113-1-mobile-application-programming/tree/main/F64126147_CH14_practice9)：設計一個查訊地點系統，除在App內顯示Google Map外，也及時向使用者更新查詢地與自己之間的距離、前進方向等
  - [特殊設計](https://github.com/janiceHuuu/113-1-mobile-application-programming/blob/main/F64126147_CH14_practice9/F64126147_%E8%83%A1%E7%91%80%E7%9C%9F_%E7%B7%B4%E7%BF%92%E4%B9%9D%E7%89%B9%E6%AE%8A%E8%A8%AD%E8%A8%88.pdf)
    - 新增搜尋匡的防呆功能，以因應使用者輸入錯誤、未查到地點的情況
    - 當使用者點選Google Map上的地標，則使用Intent打開對應地點的google搜尋頁面
      
- [期中考](https://github.com/janiceHuuu/113-1-mobile-application-programming/tree/main/%E6%9C%9F%E4%B8%AD%E8%80%83)
  - [考題](https://github.com/janiceHuuu/113-1-mobile-application-programming/blob/main/%E6%9C%9F%E4%B8%AD%E8%80%83/%E6%9C%9F%E4%B8%AD%E8%80%83%E9%A1%8C.pdf)
  - [球員卡產生器](https://github.com/janiceHuuu/113-1-mobile-application-programming/tree/main/%E6%9C%9F%E4%B8%AD%E8%80%83/F64126147_7276_1)
    使用者可透過下拉選單選擇運動項目、勾選單選的國籍選項、勾選複選的喜愛食物、運用DatePickerDialog輸入生日，並手動輸入球員姓名、身高與體重，最後按下產生鍵或即時變更任意項目後，即時生成球員卡
  - [演唱會購票系統](https://github.com/janiceHuuu/113-1-mobile-application-programming/tree/main/%E6%9C%9F%E4%B8%AD%E8%80%83/F64126147_7276_2)
    使用者可透過點擊增加、長按減少搖滾區購票數，並藉由點擊增減按鈕選擇看臺區票數，系統需在符合購票規則（如：每人限購6張票、螢光棒購買數量不得大於門票數、同區購票享第二張8折優惠、不分區購票3張送1支螢光棒、不同付款方式享不同優惠或需付手續費等）的情況下，正確計算總金額。最後，使用者確認付款方式，App即出現AlertDialog確認購票訊息，供使用者確認或取消訂單
  
- [期末專題](https://github.com/janiceHuuu/113-1-mobile-application-programming/tree/main/group4_2)：三人一組合作完成，本組主題為「美食店家資訊整合與篩選平台」
  - [專題成果報告書](https://github.com/janiceHuuu/113-1-mobile-application-programming/blob/main/group4_2/%E5%B0%88%E9%A1%8C%E6%88%90%E6%9E%9C%E5%A0%B1%E5%91%8A%E6%9B%B8.pdf)
  - [成果展示影片](https://youtu.be/jHW_42sFfns)
  - [升級版之前往店家功能展示影片](https://youtube.com/shorts/wr_U_NzrJ-Q)
  - 可供使用者新增美食店家於應用程式中，並透過搜尋關鍵字或篩選標籤（如：泰式、日式）的方式選擇店家。接著，可預約去該店家的時間，或點擊前往店家的按鈕，產生引導使用者前進方向的箭頭、地圖、距離等空間資訊
  - 使用者在選擇店家之頁面，可將店家加入收藏，App會統計使用者喜愛的餐廳類別，並匯出喜好度報表，並根據報表隨機推薦店家給使用者
  - 我的負責部分
    - 選擇店家、預約店家、預約清單、店家清單、前往店家、匯出報表之功能與頁面
    - 整合組員負責的程式
