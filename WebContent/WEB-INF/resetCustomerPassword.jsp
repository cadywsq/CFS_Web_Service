<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="template-employee-top.jsp" />
<div class="row">
	<div class="col-md-offset-2 col-md-8">

		<c:choose>
			<c:when test="${ (empty msg) }">
			</c:when>
			<c:otherwise>
				<h3 style="color: blue">${msg}</h3>
			</c:otherwise>
		</c:choose>

		<c:forEach var="error" items="${errors}">
			<h3 style="color: red">${error}</h3>
		</c:forEach>


		<form action="reset.do" method="POST">
			<div class="form-group">
				<h3>
					Reset password for: <font color="blue">${customer} </font>
				</h3>
				<br>
			</div>
			<table class="table table-hover table-striped">
				<tr>
					<td>New Password:</td>
					<td><input required class="form-control" type="password"
						placeholder="New Password"></td>
				</tr>
				<tr>
					<td>Confirm Password:</td>
					<td><input required class="form-control" type="password"
						placeholder="Confirm new Password"> <input type="hidden"
						name="customer" value="${customer}"></td>

				</tr>
				<tr>
					<td></td>
					<td align="right">
					<input type="submit" class="btn btn-default" name="action" value="Change Password">
				</tr>
			</table>
		</form>
		<p>
			<br>
		</p>
	</div>
</div>
<jsp:include page="template-buttom.jsp" />