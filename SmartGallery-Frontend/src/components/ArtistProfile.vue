<template>
<div>
  <hr style="height:4px;border-width:0;color:gray;background-color:black">
  <img src="../assets/sglogo.png" alt="logo">
  <button class="button2" v-on:click="goToHome()" type="button" name="home"> Home </button>
  <button class="button2" v-on:click="goToArtworkSearch()" type="button" name="artworkSearch"> Search Artwork </button>
  <button class="button2" v-on:click="goToArtistSearch()" type="button" name="artistSearch"> Search Artist </button>
  <button class="button2" v-on:click="goToProfile()" type="button" name="viewProfile"> View Profile </button>
  <button class="button2" v-on:click="goToCreateArtwork()" type="button" name="uploadArtwork"> Upload Artwork </button>
  <button class="button2" v-on:click="logout()" type="button" name="logout"> Logout </button>
  <hr style="height:4px;border-width:0;color:gray;background-color:black">
  <span style="font-size:60px; font-family: fantasy"> Artist Profile </span>
  <hr style="height:4px;border-width:0;color:gray;background-color:black">
  <div class="userInfo" style="font-size:25px; font-weight: bold;">
    <p id="user">Username: {{ artist.username }} </p>
    <p id="email">Email: {{ artist.email}} </p>
    <p id="dpm">Default payment method: {{ artist.defaultPaymentMethod }} </p>
    <p id="date">Creation Date: {{ artist.creationDate }} </p>
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
  <button class="button2" type="button" name="deleteArtist" v-show="!showDelete" v-on:click="showDelete = !showDelete"> Delete Account </button>
  <div class="deleteArtist" v-show="showDelete">
    <p>Are you sure you want to delete your account?</p>
    <br>
    <button class="button2" type="button" v-on:click="deleteArtist()"> Yes </button>
    <br><br>
    <button class="button2" type="button" v-on:click="showDelete = !showDelete"> No </button>
  </div>
  <br>
  <br>
  <div class="artwork">
    <hr style="height:4px;border-width:0;color:gray;background-color:black">
    <br>
    <span style="font-size:35px; font-family: fantasy"> ARTWORKS </span>
    <br>
    <br>
    <button class="button2" type="button" name="createListing" v-on:click="goToCreateListing()"> Create Listing </button>
    <button class="button2" type="button" name="updateListing" v-on:click="goToUpdateArtwork()"> Update Listing </button>
    <br>
    <br>
    <hr style="width:1000px;height:2px;border-width:0;color:gray;background-color:black">
    <span v-for="artwork in artist.artworks" v-bind:key="artwork.artworkID" style="font-style: italic;">
      Name: {{ artwork.name}}
      <br>
      Year: {{ artwork.year }}
      <br>
      Style: {{ artwork.artStyle }}
      <br>
      Price: {{ artwork.price }}
      <br>
      <hr style="width:1000px;height:2px;border-width:0;color:gray;background-color:black">
    </span>
  </div>
  <br><br>
  <button class="button2" type="button" v-show="!deleteListing" v-on:click="deleteListing = !deleteListing"> Delete a Listing and Artwork </button>
  <br>
  <div class="deleteListing" v-show="deleteListing">
    Select an artwork
    <br>
    <select v-model="selectedListingDelete" placeholder>
      <option v-for="artwork in artist.artworks" v-bind:value="{ listingid: artwork.listing.listingID}" :key="artwork.name"> {{artwork.name}}</option>
    </select>
    <br><br>
    <button class="button2" type="button" v-on:click="deleteListingAndArtwork(selectedListingDelete.listingid); goToProfile()"> Delete </button>
    <br><br>
    <button class="button2" type="button" v-on:click="deleteListing= !deleteListing"> Cancel </button>
  </div>
  <br><br>
  <hr style="height:4px;border-width:0;color:gray;background-color:black">
  <br>
  <span style="font-size:35px; font-family: fantasy"> BROWSE HISTORY: </span>
  <br>
  <br>
  <hr style="width:1000px;height:2px;border-width:0;color:gray;background-color:black">
  <div v-for="artwork in browseHistory" v-bind:key="artwork.artworkID">
    <a style="font-weight: bold; text-decoration: underline;" v-bind:href="getListingPageURL(artwork.listing.listingID)">{{ artwork.name }}</a>
    <br>
    Artist(s):
    <span v-for="artist in artwork.artists" :key="artist.username">
      <span style="font-style: italic;"> {{ artist.username }}, </span>
    </span>
    <br>
    Year Created:
    <span style="font-style: italic;"> {{ artwork.year }} </span>
    <br>
    Style:
    <span style="font-style: italic;"> {{ artwork.artStyle }} </span>
    <br>
    Price:
    <span style="font-style: italic;"> {{ artwork.price }} $ </span>
    <br>
    <hr style="width:1000px;height:2px;border-width:0;color:gray;background-color:black">
  </div>
  <br>
  <hr style="height:4px;border-width:0;color:gray;background-color:black">
  <br>
  <span style="font-size:35px; font-family: fantasy"> Transactions: </span>
  <div v-for="transaction in transactions">
    <br>
    Date Purchased:
    <span style="font-style: italic;"> {{ transaction.paymentDate }} </span>
    <br>
    Payment Method:
    <span style="font-style: italic;"> {{ transaction.paymentMethod }} </span>
    <br>
    Artwork Name:
    <span style="font-style: italic;"> {{ transaction.listing.artwork.name }} </span>
  </div>
</div>
</template>

<script src="./javascript/ArtistProfile.js">

</script>

<style>
#user {
  padding: 10px;
}

#email {
  padding: 10px;
}

button:hover {
  background-color: #000000;
  color: white
}

#dpm {
  padding: 10px;
}

#date {
  padding: 10px;
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

body {
  background-color: #e8f4ff;
}

.artwork {
  padding: 30px;
}

img {
  text-align: center;
  width: 180px;
  height: 105px;
  left: 10px;
  top: 15px;
}
</style>
