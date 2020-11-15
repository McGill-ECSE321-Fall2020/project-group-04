<script src="./javascript/CustomerProfile.js">
</script>

<template>
<body class="customer" style="font-size:25px; font-family: fantasy background-color: #e8f4ff; text-align: center;">
  <hr style="height:4px;border-width:0;color:gray;background-color:black">
  <img src="../assets/sglogo.png" alt="logo">
  <button class="button2" v-on:click="goToHome()" type="button" name="home"> Home </button>
  <button class="button2" v-on:click="goToArtworkSearch()" type="button" name="artworkSearch"> Search Artwork </button>
  <button class="button2" v-on:click="goToArtistSearch()" type="button" name="artistSearch"> Search Artist </button>
  <button class="button2" v-on:click="goToProfile()" type="button" name="viewProfile"> View Profile </button>
  <button class="button2" v-on:click="logout()" type="button" name="logout"> Logout </button>
  <hr style="height:4px;border-width:0;color:gray;background-color:black">

  <h1 id="customerHeader">Your Profile</h1>
  <br>
  <br>
  <br>
  <div class="userInfo">
    <p id="user">Username: {{ customer.username}} </p>
    <br>
    <br>
    <br>
    <p id="email">Email: {{ customer.email}} </p>
    <br>
    <br>
    <br>
    <p id="dpm">Default payment method: {{ customer.defaultPaymentMethod }} </p>
    <br>
    <br>
    <br>
    <p id="date">Creation Date: {{ customer.creationDate }} </p>
  </div>
  </div>
  <button class="button2" type="button" name="updateProfile" v-show="!showPassword" v-on:click="showPassword = !showPassword"> Update password </button>
  <div class="updatePassword" v-show="showPassword">
    <input v-model="oldPasswordInput" placeholder="old password">
    <br><br>
    <input v-model="newPasswordInput" placeholder="new password">
    <br><br>
    <button class="button2" type="button" v-on:click="updatePassword(oldPasswordInput, newPasswordInput)"> Change </button>
    <br><br>
    <button class="button2" type="button" v-on:click="showPassword= !showPassword"> Cancel </button>
  </div>
  <br>
  <br>
  <button class="button2" type="button" name="updateEmail" v-show="!showEmail" v-on:click="showEmail = !showEmail"> Update email </button>
  <div class="updateEmail" v-show="showEmail">
    <input v-model="newEmail" placeholder="new email">
    <br><br>
    <input v-model="passwordInput" placeholder="password">
    <br><br>
    <button class="button2" type="button" v-on:click="updateEmail(newEmail,passwordInput)"> Change </button>
    <br><br>
    <button class="button2" type="button" v-on:click="showEmail= !showEmail"> Cancel </button>
  </div>
  <br>
  <br>
  <button class="button2" type="button" name="deleteCustomer" v-show="!showDelete" v-on:click="showDelete = !showDelete"> Delete Account </button>
  <div class="deleteCustomer" v-show="showDelete">
    <p>Are you sure you want to delete your account?</p>
    <br>
    <button class="button2" type="button" onclick="deleteCustomer()"> Yes </button>
    <br><br>
    <button class="button2" type="button" v-on:click="showDelete= !showDelete"> No </button>
  </div>
  <br>
  <br>
  <img src="../assets/sglogo.png" alt="logo">
  <span style="font-size:60px; font-family: fantasy"> BROWSE HISTORY: </span>
  <div v-for="artwork in browseHistory" v-bind:key="artwork.artworkID">
      <a style="font-weight: bold; text-decoration: underline;" v-bind:href="getListingPageURL(artwork.listing.listingID)">{{ artwork.name }}</a>
      <br>
      Artist(s):
      <span v-for="artist in artwork.artists"  :key="artist.username" >
      <span style="font-style: italic;"> {{ artist.username }}, </span>
      </span>
      <br>
      Year Created:
      <span style="font-style: italic;"> {{ artwork.year }} </span>
      <br>
      Style:
      <span style="font-style: italic;">  {{ artwork.artStyle }} </span>
      <br>
      Price:
      <span style="font-style: italic;"> {{ artwork.price }} $ </span>
      <br>
  </div>

</body>
</template>

<style>
img {
  width: 250px;
  height: 200px;
  position: absolute;
  left: 0;
  top: 0;
}

.button2 {
  font-family: fantasy;
  background-color: #008CBA;
  color: white;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 20px;
  border-radius: 6px;
  border: 2px solid black;
  padding: 8px 40px
}

button:hover {
  background-color: #000000;
  color: white
}

img {
  text-align: center;
  width: 180px;
  height: 105px;
}

.customer {
  color: black;
  text-align: center;
  background-color: #e8f4ff;
  font-family: fantasy;
}
</style>
