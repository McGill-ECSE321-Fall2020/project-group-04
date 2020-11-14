<template>
  <body class="registration">
    <div id="registration">
      <hr style="height:4px;border-width:0;color:gray;background-color:black">	
      <br>
      <h1>
        <span style="font-size:38px; font-family: fantasy"> Please fill out the following fields to create an account  </span>
      </h1>
      <br>
      <h3>
        <span style="font-size:28px; font-family: fantasy"> Username&nbsp;&nbsp;  </span>
        <input type="text" placeholder="Username"> </h3>
      <h3>
        <span style="font-size:28px; font-family: fantasy"> Email&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  </span>
        <input type="text" placeholder="Email"> </h3>
      <h3>
        <span style="font-size:28px; font-family: fantasy"> Password&nbsp;&nbsp;&nbsp;  </span>
        <input type="text" placeholder="Password"> </h3>
      <h3>
        <span style="font-size:28px; font-family: fantasy"> Account type  </span>
        <select v-model="accountType">
          <option>Customer</option>
          <option>Artist</option>
        </select>
        <select v-model="paymentType">
          <option>credit</option>
          <option>paypal</option>
        </select>
      </h3>
      <br>
      <h3> 
        <button 
          class="button2"
          v-on:click="createProfile(accountType, username, password, email, paymentType)"
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

<script src="./javascript/Registration.js">
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
.registration {
  background-color: #e8f4ff;
    text-align: center;
}
</style>