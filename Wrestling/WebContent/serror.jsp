<%--
 Licensed to the Apache Software Foundation (ASF) under one or more
  contributor license agreements.  See the NOTICE file distributed with
  this work for additional information regarding copyright ownership.
  The ASF licenses this file to You under the Apache License, Version 2.0
  (the "License"); you may not use this file except in compliance with
  the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--%>
<html>
<head>
<title> Login Error </title>
<script src="js/addWrestlerSend.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="js/kickstart.js"></script> <!-- KICKSTART -->
<script src="js/scripts.js"></script> <!-- KICKSTART -->
<link rel="stylesheet" href="css/kickstart.css" media="all" /> 
<link rel="stylesheet" href="css/fonts/css/font-awesome.css" media="all" /> <!-- KICKSTART --><!-- KICKSTART -->
<link rel="stylesheet" href="css/fonts/css/font-awesome.min.css" media="all" /> <!-- KICKSTART --><!-- KICKSTART -->
</head>
<body>
<h1>Invalid username and/or password, please try</h1>
<a href='<%= response.encodeURL("/") %>'>again</a>.
</body>
</html>
