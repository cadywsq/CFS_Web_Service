<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="template-employee-top.jsp" />
<div>

	<div class="row">
		<div class="col-md-offset-2 col-md-8">
		<h3>Welcome, ${ user.firstName } ${ user.lastName }!</h3>	
			<br> <img src="img/banner.jpg" alt="successImg" width="60%" />
		</div>
	</div>
<jsp:include page="template-buttom.jsp" />