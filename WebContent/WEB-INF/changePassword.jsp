<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="template-customer-top.jsp" />
<div class="row">
	<div class="col-md-offset-2 col-md-8">
		<h2 class="page-header" align="center">Change Password</h2>
	</div>
</div>
<div class="row">
	<div class="col-md-offset-2 col-md-8">
		<c:choose>
			<c:when test="${ (empty msg) }">
			</c:when>
			<c:otherwise>
				<h3 class="page-header" style="color: green">${msg}</h3>
			</c:otherwise>
		</c:choose>
	</div>
</div>

<div class="row">
	<div class="col-md-offset-2 col-md-8">
		<c:forEach var="error" items="${errors}">
			<h3 style="color: red">${error}</h3>
		</c:forEach>
		<form action="changePwd.do" method="post">
			<table class="table table-hover table-striped">
				<tr>
					<td>Old Password:</td>
					<td><input name="oldPassword" required class="form-control"
						type="password" placeholder="Enter your old Password"></td>
				</tr>
				<tr>
					<td>New Password:</td>
					<td><input name="newPassword" required class="form-control"
						type="password" placeholder="New Password"></td>
				</tr>
				<tr>
					<td>Confirm Password:</td>
					<td><input name="confirmedPassword" required
						class="form-control" type="password"
						placeholder="Confirm new Password"></td>
				</tr>
				<tr>
					<td></td>
					<td align="right"><input type="submit" class="btn btn-default"
						name="action" value="Change Password">
				</tr>
			</table>
		</form>
		<p>
			<br>
		</p>
	</div>
</div>

<jsp:include page="template-buttom.jsp" />