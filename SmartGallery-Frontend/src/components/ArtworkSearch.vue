<script src="./javascript/artworkSearch.js">
</script>

<template>
    <div>
    <div class="header">
      <h1>SmartGallery - Artwork Search
      <button type="button" name="home"> Purchase </button>
      <button type="button" name="artistSearch"> Purchase </button>
      <button type="button" name="viewProfile"> Purchase </button>
      </h1>
    </div>
    <input v-model="artworkNameInput" placeholder="Artwork name">
    <button type="button" name="search"> Purchase </button>

  <br>
  <input v-model="minPriceInput" placeholder="Min price">
  <input v-model="maxPriceInput" placeholder="Max price">
  <input v-model="artStyleInput" placeholder="Art Style">

</div>
</template>
