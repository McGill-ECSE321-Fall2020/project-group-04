<template>
  <body class="login">
    <hr style="height:4px;border-width:0;color:gray;background-color:black">	
    <img src="../assets/sglogo.png"  alt="logo">
    <button class="button2" v-on:click="loginWarning()" type="button" name="home"> Home </button>
    <button class="button2" v-on:click="loginWarning()" type="button" name="artworkSearch"> Search Artwork </button>
    <button class="button2" v-on:click="loginWarning()" type="button" name="artistSearch"> Search Artist </button>
    <button class="button2" v-on:click="loginWarning()" type="button" name="viewProfile"> View Profile </button>
    <hr style="height:4px;border-width:0;color:gray;background-color:black">	
    <div id="login">
      <hr style="height:4px;border-width:0;color:gray;background-color:black">	
      <span style="font-size:60px; font-family: fantasy"> Login </span>
      <h3>
        <span style="font-size:28px; font-family: fantasy"> Username&nbsp;&nbsp;&nbsp;  </span>
        <input
          type="text"
          v-model="username"
          placeholder="Username">
      </h3>
      <h3>
        <span style="font-size:28px; font-family: fantasy"> Password&nbsp;&nbsp;&nbsp;&nbsp;  </span>
        <input
          type="text" 
          v-model="password" 
          placeholder="Password"> 
      </h3>
      <h3> 
        <button 
          class="button2"
          v-on:click="login(username, password)"
          type="button"
          name="login"> 
          Login
        </button> 
      </h3>
      <br>
      <br>
      <h3>
        <span style="font-size:28px; font-family: fantasy"> Or if you don't have an account yet:  </span>
      </h3>
      <h3> 
        <button 
          class="button2"
          v-on:click="register"
          type="button" 
          name="register">
          Register 
        </button> 
      </h3>
      <hr style="height:4px;border-width:0;color:gray;background-color:black">	
    </div>
  </body>
</template>

<script src="./javascript/Login.js">
</script>

<style>
.header {
  font-size: 16px;
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
  box-shadow: 0 0 3px 3px rgba(238, 238, 0, 1);
}
.login {
  background-color: #e8f4ff;
    text-align: center;
}
</style>