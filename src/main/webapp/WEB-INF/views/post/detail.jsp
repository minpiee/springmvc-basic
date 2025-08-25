<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% pageContext.setAttribute("CRLF", "\r\n"); %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>게시글 상세</title>
  </head>
  <body>
    <div>
      <p>${postResponseDTO.title}</p>

      <%-- 날짜를 yyyy/MM/dd 형식으로 변환해 출력 --%>
      <p>${postResponseDTO.updatedAtFormatted}</p>
    </div>

    <%-- 첨부 이미지가 있을 경우 화면에 표시 --%>
    <c:if test="${not empty postResponseDTO.storedFilename}">
      <img src="<c:url value='${postResponseDTO.imageUrl}' />" alt="이미지파일" style="max-width: 200px" />
    </c:if>

    <%-- DB 줄바꿈(\r\n)을, <br />로 변환해 표시 --%>
    <div>${fn:replace(postResponseDTO.content, CRLF ,'<br />')}</div>

    <div>
      <a href="<c:url value='/posts' />">목록</a>
    </div>
  </body>
</html>