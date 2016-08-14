<%--
  Created by IntelliJ IDEA.
  User: stone
  Date: 2016/4/15
  Time: 8:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<footer>
  <div class="footer-infobox">
  </div>
</footer>

<script>
  var date = new Date();
  var year = date.getFullYear();
  var footer = document.getElementsByClassName("footer-infobox")[0];
  console.log(footer);
  footer.innerHTML = "<p>&copy;"+year+" BUPT 902 物联网技术中心</p>";
</script>