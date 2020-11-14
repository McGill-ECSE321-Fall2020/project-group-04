<script src="./javascript/CustomerProfile.js">
</script>

<template>
<body class="customer">
  <h1 id="customerHeader">Your Profile</h1>
  <br>
  <br>
  <br>
  <div class="userInfo">
    <p id="user">Username: {{ customer.username}} </p>
	<br>
	<br>
	<br>
    <p id="email">Email: {{ customer.email}} </p>
	<br>
	<br>
	<br>
    <p id="dpm">Default payment method: {{ customer.defaultPaymentMethod }}  </p>
	<br>
	<br>
	<br>
    <p id="date">Creation Date: {{ customer.creationDate }} </p>
  </div>

  <img src="../assets/sglogo.png" alt="logo">
</body>
</template>

<style>
img{
	width: 250px;
    height: 200px;
    position: absolute;
    left:0;
    top:0;
}


.customer{
	

  color:black;
  text-align: center;
  background-color: #e8f4ff;
  font-family:fantasy;
}

</style>

