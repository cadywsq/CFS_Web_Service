<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="template-employee-top.jsp" />
<div>
	<div class="row">
		<div class="col-md-offset-2 col-md-8">
			<h2>Create Customer Account</h2>
		</div>
	</div>
	<div class="row">
		<div class="col-md-offset-2 col-md-8">
			<c:forEach var="error" items="${errors}">
				<h3 style="color: red">${error}</h3>
			</c:forEach>
			<form method="post" action="createCustomer.do">
				<div class="form-group">
					<div class="row">
						<div class="col-xs-2">
							<label>User Name:<span class="astreik">*</span></label>
						</div>
						<div class="col-md-2">
							<input name="username" style="width: 170%" required class="form-control" value="${form.username}">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-2">
						<label>First Name:<span class="astreik">*</span></label>
					</div>
					<div class="col-md-3">
						<input name="firstName" required class="form-control"
							value="${form.firstName}">
					</div>
					<div class="col-xs-2">
						<label>Last Name:<span class="astreik">*</span></label>
					</div>
					<div class="col-md-3">
						<input name="lastName" required class="form-control"
							value="${form.lastName}">
					</div>
				</div>
				<br>
				<div class="row">
					<div class="col-xs-2">
						<label>Password:<span class="astreik">*</span></label>
					</div>
					<div class="col-md-3">
						<input name="password" type="password" required
							class="form-control">
					</div>
					<div class="col-xs-2">
						<label>Confirm Password:<span class="astreik">*</span></label>
					</div>
					<div class="col-md-3">
						<input name="confirmPassword" type="password" required
							class="form-control" placeholder="This is case sensitive field.">
					</div>
				</div>
				<div class="form-group">
					<div class="row">
						<div class="col-xs-5">
							<label>Address line 1:<span class="astreik">*</span></label> <input
								class="form-control" name="addrLine1"
								placeholder="Address Line 1" required 
								value="${form.addrLine1}">
						</div>
						<div class="col-xs-5">
							<label>Address line 2:</label> <input
								class="form-control" name="addrLine2"
								placeholder="Address Line 2 (Optional)"
								value="${form.addrLine2}">
						</div>
					</div>
				</div>
				<br>

				<div class="form-group">
					<div class="row">
						<div class="col-xs-1">
							<label>City:<span class="astreik">*</span></label>
						</div>
						<div class="col-xs-2">
							<input class="form-control" required style="width: 130%"  name="city" value="${form.city}">
						</div>
						<div class="col-xs-1">
							<label>State:<span class="astreik">*</span>
							</label>
						</div>
						<div class="col-xs-2">
							<input name="state" class="form-control" required style="width: 120%" maxlength="2" value="${form.state}">
						</div>
						<div class="col-xs-2">
							<label>Zip*:<span class="astreik">*</span>
							</label>
						</div>
						<div class="col-xs-2">
							<input name="zip" class="form-control" required
								placeholder="Ex: 15217" style="width: 130%" maxlength="5"
								value="${form.zip}">
						</div>
					</div>
				</div>
				<br>
				<div class="row">
					<div class="col-xs-6" align="right">
						<input type="submit" class="btn btn-default" name="action"
							value="Create Customer">
					</div>
				</div>
			</form>
			<br><br><br>
		</div>
		<jsp:include page="template-buttom.jsp" />