<template>
<div>
<h1> Artwork Name : {{ artwork.name }}</h1>
<h1> Artist(s) Name (s) : </h1>
<img src="../assets/PLACEHOLDER.jpg"><br>
<h3>Description </h3>
  <div class="description">
    Year Created : {{artwork.year}}<br>
    Price : {{artwork.price}} <br>
    Style : {{artwork.artStyle}} <br>
    Height: {{artwork.height}} <br>
    Width : {{artwork.width}} <br>
    Weight : {{artwork.weight}}<br>
    Sold : {{newListing.isSold}}<br>
  </div>
  <div class = "purchase">
    Select payment method
    <br><br>
    <select v-model="selected">
    <option >Credit Card</option>
    <option>Paypal</option>
    </select>
    <br><br>
    Select Delivery method
    <br><br>
    <select v-model="delivery">
    <option >Ship</option>
    <option>Pickup</option>
    </select>
    <br><br>
    <button type="button" name="button"> Purchase </button>
  </div>
</div>
</template>


<script src="./javascript/ListingView">
</script>


<style>
.description{
    width:30%;
    max-height: auto;
    min-width: 550px;
    margin:auto;
    margin-top:15px;
    padding:15px;
    text-align: left;
}

.purchase{
  width: 200;
  height: 200;
}
</style>
