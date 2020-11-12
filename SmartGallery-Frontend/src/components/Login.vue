<template>
  <div id="login">
    <h1>
      Welcome!
    </h1>
    <h3>
      Username:
      <input
        type="text"
        v-model="username"
        placeholder="Username">
    </h3>
    <h3>
      Password:
      <input
        type="text" 
        v-model="password" 
        placeholder="Password"> 
    </h3>
    <h3> 
      <button 
        v-on:click="login(username, password)"
        type="button"
        name="login"> 
        Login
      </button> 
    </h3>
    <h3>
      Or, if you don't have an account yet: 
    </h3>
    <h3> 
      <button 
        type="button" 
        name="register">
        Register 
      </button> 
    </h3>
  </div>
</template>

<<<<<<< HEAD
<script src="./javascript/Login.js">
=======
<script src="./javascript/Login">
>>>>>>> 5c949da9f0cb0207d951da2273fb5cdd80e65516
</script>

<style>

</style>