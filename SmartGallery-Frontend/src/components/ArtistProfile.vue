<template>
<div>
  <h1>Artist Profile <br> <button type="button" name="home"> Home </button>
    <button type="button" name="viewProfile"> View Profile </button>
    <button type="button" name="uploadArtwork"> Upload Artwork </button>
  </h1>
  <div class="userInfo">
    <p id="user">Username: {{ artist.username }} </p>
    <p id="email">Email: {{ artist.email}} </p>
    <p id="dpm">Default payment method: {{ artist.defaultPaymentMethod }}  </p>
    <p id="date">Creation Date: {{ artist.creationDate }} </p>
  </div>

  <div class="artwork">
    <h2>Artwork</h2>
    <span v-for="artwork in artist.artworks">
      <br>
      Year: {{ artwork.year }}
      <br>
      Style: {{ artwork.artStyle }}
      <br>
      Price: {{ artwork.price }}
    </span>
    <br>
    <br>
  </div>

</div>
</template>

<script src="./javascript/ArtistProfile.js">

</script>

<style lang="css" scoped>
#user{
  padding: 10px;
}
#email{
  padding: 10px;
}
#dpm{
  padding: 10px;
}
#date{
  padding: 10px;
}

.artwork{
  padding: 30px;
}
</style>
