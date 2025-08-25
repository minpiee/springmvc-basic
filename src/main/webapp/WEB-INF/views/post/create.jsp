<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>게시글 등록</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/common.css' />" />
  </head>
  <body>
  
    <%-- 서버에서 전달된 message 값이 있으면 alert 창으로 표시한다 --%>
    <c:if test="${not empty message}">
      <script>
        alert('<c:out value="${message}"/>');
      </script>
    </c:if>

    <form:form modelAttribute="post" action="${pageContext.request.contextPath}/posts" method="post">
      <div>
        <label for="title">제목</label>
        <form:input path="title" id="title" placeholder="제목을 입력하세요." />
      </div>
      <div>
        <label for="content">내용</label>
        <form:textarea path="content" id="content" rows="5" placeholder="내용을 입력하세요."></form:textarea>
      </div>
      <div>
        <button type="submit">등록</button>
        <a href="<c:url value='/posts' />">취소</a>
      </div>
    </form:form>
  </body>
</html>