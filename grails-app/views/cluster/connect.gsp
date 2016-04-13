<%--
  Created by IntelliJ IDEA.
  User: lbm
  Date: 13/04/16
  Time: 22:28
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
</head>

<body>

    <h1>Hosts</h1>
   <g:each in="${hosts}" var="host">
        <div>${host}</div>
   </g:each>
</body>
</html>