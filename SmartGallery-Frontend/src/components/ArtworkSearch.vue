<script src="./artworkSearch.js">
</script>

<template>
  <div id="artworksearch">
    <h2>Listings</h2>
      <table>
        <tr v-for="listing in Listings" :key="listing.name" >
            <td>{{ listing.name }}</td>
            <td>{{ listing.price}}</td>
        </tr>
    <!-- ... -->
    </table>
  </div>
</template>
