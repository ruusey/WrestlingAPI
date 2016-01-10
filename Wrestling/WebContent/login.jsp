<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="/js/kickstart.js"></script> <!-- KICKSTART -->
<script src="/js/scripts.js"></script> <!-- KICKSTART -->
<link rel="stylesheet" href="/css/kickstart.css" media="all" /> 
<link rel="stylesheet" href="/css/fonts/css/font-awesome.css" media="all" /> <!-- KICKSTART --><!-- KICKSTART -->
<link rel="stylesheet" href="/css/fonts/css/font-awesome.min.css" media="all" /> <!-- KICKSTART --><!-- KICKSTART -->
<link rel="stylesheet" type="text/css" media="only screen and (max-device-width: 600px)" href="/css/mobile.css" />
<title>Wrestling Manager Login</title>
</head>
<body>
<a href="/" class="button large" style="float:left;position:absolute"><i class="fa fa-home"></i>Home</a>
<div class="col_4 column">

</div>
    <div id="login_div" class="login col_4 column center" style="background:#00573c">
      <h4 style="color:white"><u>Login To Manage Team</u></h4>
     <form method="POST" action='<%= response.encodeURL("j_security_check") %>' >
        <p><input type="text" name="j_username" value="" placeholder="Username"></p>
        <p><input type="password" name="j_password" value="" placeholder="Password"></p>
        <p class="submit">
        <button type="submit" name="commit" value="Log In">Log In</button>
        </p>
      </form>
    </div>
<div class="col_4 column"></div>
</body>
</html>