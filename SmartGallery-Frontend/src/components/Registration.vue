<template>
  <div id="registration">
    <h1>Please fill out the following fields to create an account</h1>
    <br>
    <h3>Username:
    <input type="text" placeholder="Username"> </h3>
    <h3>Email:
    <input type="text" placeholder="Email"> </h3>
    <h3>Password:
    <input type="text" placeholder="Password"> </h3>
    <h3>Account type:
    <select v-model="selected">
    <option>Customer</option>
    <option>Artist</option>
    </select>
    <select v-model="selected">
    <option >Credit Card</option>
    <option>Paypal</option>
    </select>
    </h3>
    <br>
    <h3> <button type="button" name="register"> Register </button> </h3>    
  </div>
</template>

<script>

</script>

<style>

</style>
