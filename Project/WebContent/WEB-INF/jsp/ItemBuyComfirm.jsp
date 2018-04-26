<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品カート一覧</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
</head>
<body>

<div class = "header">
ヘッダー<br>
  <div class = "search_form">

    <form action = "" method = "post">
    <input type = "text" name = "itemSearch">
    <input type = "submit" value = "検索">
    </form>

  </div>

</div>


<div class = "wrapperr">

<h1>商品カート一覧</h1><br>

 <div class = "buy_comfirm_list">

   <div class = "cart_one">
     <p>
     繰り返し<br>
     画像　：画像<br>
     商品名：商品名<br>
     値段　：2000円<br>
     詳細　：詳細<br>
     </p>
   </div>

   <div class = "delivery_detail">
   配送方法<br>
   配送料金<br>
   </div>

   <div class  ="totalprice">
   10000円
   </div>

   <div class = "cart_buy_form">
     <form action = "ItemBuyResult" method = "post">
       <input type = "hidden" name = "buycomfirm">

       <input type = "submit" value = "購入する">
     </form>
   </div>

 </div>

</div>

<br><br>

<div class = "footer">
フッター
</div>

</body>
</html>