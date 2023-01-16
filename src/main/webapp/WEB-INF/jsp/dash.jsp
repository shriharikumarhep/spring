<html>
<head><title>First JSP</title></head>
<body>
  <%
    double num = Math.random();
    if (num > 0.95) {
  %>
      <h2>You'll have a luck day!</h2><p>(<%= num %>)</p>
  <%
    } else {
  %>
      <h2>Well, life goes on ... </h2><p>(<%= num %>)</p>
  <%
    }
  %>
  <h3>(<%= request.getAttribute("userlist")%>)</h3>
  <h3>(<%= request.getAttribute("test") %>)</h3>
  
    
</body>
</html>