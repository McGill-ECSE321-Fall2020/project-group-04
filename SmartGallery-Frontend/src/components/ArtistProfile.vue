<template>
<div>
  <h1>Artist Profile <br> <button type="button" name="home"> Home </button>
    <button type="button" name="viewProfile"> View Profile </button>
    <button type="button" name="uploadArtwork"> Upload Artwork </button>
  </h1>
  <div class="userInfo">
    <p id="user">Username: </p>
    <p id="email">Email: </p>
    <p id="dpm">Default payment method: </p>
    <p id="date">Creation Date: </p>
  </div>

  <div class="artwork">
    <h2>Artwork</h2>
  </div>

</div>
</template>

<script>
export default {}
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
