<template>
  <body class="login">
    <hr style="height:4px;border-width:0;color:gray;background-color:black">
    <img src="../assets/sglogo.png"  alt="logo">
    <hr style="height:4px;border-width:0;color:gray;background-color:black">
    <div id="login">
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
          type="password"
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
body {
  background-color: #e8f4ff;
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
.login {
  background-color: #e8f4ff;
    text-align: center;
}
</style>
