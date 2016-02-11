<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="template-employee-top.jsp" />
<div>
	<div class="row">
		<div class="col-md-offset-2 col-md-8">
			<h2>Reset Password</h2>
		</div>
	</div>
	<div class="row">
		<div class="col-md-offset-2 col-md-8">
		<c:forEach var="error" items="${errors}">
			<h3 style="color: red">${error}</h3>
		</c:forEach>
			<form method="post" action="resetPassword.do">
				<div class="form-group">
					<div class="row">
						<div class="col-xs-4">
							<label>Customer (Username):<span class="astreik">*</span></label>
						</div>
						<div class="col-md-6">
							<select required class="form-control" name="username">
								<option></option>
								<c:choose>
									<c:when test="${ (empty customerList) }"></c:when>
									<c:otherwise>
										<c:forEach var="user" items="${ customerList }">
											<option value="${ user.getUsername() }">${ user.getUsername() }</option>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</select>
						</div>
					<br><br><br>
						<div class="col-xs-4">
							<label>New Password:</label><span class="astreik">*</span>
						</div>
						<div class="col-md-6">
							<input name="newPassword" type="password" required class="form-control"
								placeholder="">
						</div>
					<br><br>
						<div class="col-xs-4">
							<label>Confirm Password:</label><span class="astreik">*</span>
						</div>
						<div class="col-md-6">
							<input name="confirmPassword" type="password" required class="form-control">
						</div>
				</div>
				<br>
				</div>

				<div class="row">
					<div class="col-xs-6" align="right">
					<input type="submit" class="btn btn-default" name="action" value="Reset Password">
						<br> <br> <br>
					</div>
				</div>
			</form>
		</div>
		<jsp:include page="template-buttom.jsp" />