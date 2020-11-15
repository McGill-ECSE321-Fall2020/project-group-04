<template>
  <body class="registration">
    <hr style="height:4px;border-width:0;color:gray;background-color:black">
    <img src="../assets/sglogo.png"  alt="logo">
    <hr style="height:4px;border-width:0;color:gray;background-color:black">
    <div id="registration">
      <hr style="height:4px;border-width:0;color:gray;background-color:black">
      <br>
      <h1>
        <span style="font-size:38px; font-family: fantasy"> Please fill out the following fields to create an account  </span>
      </h1>
      <br>
      <h3 align="left">
        <span style="font-size:28px; font-family: fantasy; margin-left:500px"> Username&emsp;  </span>
        <input type="text" v-model="username" placeholder="Username"> </h3>
      <h3 align="left">
        <span style="font-size:28px; font-family: fantasy; margin-left:500px"> Email&emsp;  </span>
        <input type="text" v-model="email" placeholder="Email"> </h3>
      <h3 align="left">
        <span style="font-size:28px; font-family: fantasy; margin-left:500px"> Password&emsp;  </span>
        <input type="password" v-model="password" placeholder="Password"> </h3>
      <h3 align="left">
        <span style="font-size:28px; font-family: fantasy; margin-left:500px"> Re-enter password&emsp;  </span>
        <input type="password" v-model="passwordcheck" placeholder="Password"> </h3>
      <h3 align="left">
        <span style="font-size:28px; font-family: fantasy; margin-left:500px"> Account type&emsp; </span>
        <select v-model="accountType">
          <option>Customer</option>
          <option>Artist</option>
        </select>
      </h3>
      <h3 align="left">
        <span style="font-size:28px; font-family: fantasy; margin-left:500px"> Payment method&emsp; </span>
        <select v-model="paymentType">
          <option>Credit</option>
          <option>Paypal</option>
        </select>
      </h3>
      <br>
      <h3>
        <button
          class="button2"
          v-on:click="createProfile(accountType, username, password, passwordcheck, email, paymentType)"
          type="button"
          name="register">
          Register
        </button>
      </h3>
      <br>
      <h3>
        <button
          class="button2"
          v-on:click="backToLogin"
          type="button"
          name="backToLogin">
          Back To Login
        </button>
      </h3>
    <hr style="height:4px;border-width:0;color:gray;background-color:black">
    </div>
  </body>
</template>

<script src="./javascript/Registration.js"></script>

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
  background-color: #000000;
  color: white
}
.registration {
  background-color: #e8f4ff;
  text-align: center;
}
</style>
