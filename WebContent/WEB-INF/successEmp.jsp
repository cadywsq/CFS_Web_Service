<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="template-employee-top.jsp" />

<div class="row">
    <div class="col-md-offset-2 col-md-8">
<c:choose>
    <c:when test="${ (empty msg) }">
    </c:when>
    <c:otherwise>
        <h3 class="page-header">${msg}</h3>
    </c:otherwise>
</c:choose>
    </div>
</div>

<div class="row">
    <div class="col-md-offset-2 col-md-8">
<h3>Click <a href="employeeMain.do">here </a>
to go back to the welcome page.</h3>
<br>
<img src="img/banner.jpg" alt="successImg" width="60%"/>
</div>
</div>

<jsp:include page="template-buttom.jsp" />