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

    <form:form modelAttribute="post"
               action="${pageContext.request.contextPath}/posts"
               method="post"
               enctype="multipart/form-data">
               
      <div>
        <label for="title">제목</label>
        <form:input path="title" id="title" placeholder="제목을 입력하세요." />
      </div>
      
      <div>
        <label for="content">내용</label>
        <form:textarea path="content" id="content" rows="5" placeholder="내용을 입력하세요."></form:textarea>
      </div>

      <%-- 이미지 업로드 + 미리보기 + 삭제 --%>
      <div>
        <img id="preview" src="#" alt="이미지 미리보기" class="hidden" />
        <label id="deleteImageWrapper" class="hidden">
          <input type="checkbox" name="deleteImage" id="deleteImage" value="true" />
          이미지 삭제
        </label>
      </div>
      
      <div>
        <label for="imageFile">이미지 업로드</label>
        <input type="file" name="imageFile" id="imageFile" />
      </div>

      <div>
        <button type="submit">등록</button>
        <a href="<c:url value='/posts' />">취소</a>
      </div>
    </form:form>

    <script>
      // 이미지 업로드를 위한 <input type="file">
      const imageInput = document.getElementById('imageFile');
      // 선택한 이미지를 미리 보여 줄 <img>
      const preview = document.getElementById('preview');
      // 이미지 삭제를 위한 체크박스 및 영역
      const deleteCheckbox = document.getElementById('deleteImage');
      const deleteWrapper = document.getElementById('deleteImageWrapper');

      // 이벤트 등록
      imageInput.addEventListener('change', handleImageChange);
      deleteCheckbox.addEventListener('change', handleImageDelete);

      // 이미지 파일을 업로드할 때 실행되는 함수
      function handleImageChange(e) {
        const file = e.target.files[0]; // 선택된 파일 가져오기
        if (!file) return;              // 선택된 파일이 없으면 함수 종료

        // 파일을 읽기 위한 FileReader 객체 생성
        const reader = new FileReader();
        
        // 파일 읽기가 완료되면 실행
        reader.onload = function (event) {
          preview.src = event.target.result;        // 이미지 파일을 src 속성에 추가
          preview.classList.remove('hidden');       // <img> 표시
          deleteWrapper.classList.remove('hidden'); // 삭제 체크박스 표시
          deleteCheckbox.checked = false;           // 삭제 체크박스 선택 해제
        };
        reader.readAsDataURL(file);
      }

      // 이미지 삭제 체크박스 상태를 변경할 때마다 실행되는 함수
      function handleImageDelete(e) {
        // 체크박스가 선택된 경우
        if (e.target.checked) {
          preview.src = '#';                     // <img>의 src 속성 초기화
          preview.classList.add('hidden');       // <img> 숨김
          deleteWrapper.classList.add('hidden'); // 삭제 체크박스 숨김
          imageInput.value = '';                 // 파일 정보 초기화
        }
      }
    </script>
  </body>
</html>