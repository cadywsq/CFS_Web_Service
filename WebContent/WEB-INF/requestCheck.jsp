<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="template-customer-top.jsp" />

<div class="row">
	<div class="col-md-offset-2 col-md-8">
		<h2 class="page-header">${customer.getFirstName()}
			${customer.getLastName()}</h2>
	</div>
</div>

<div class="row">
	<div class="col-md-offset-2 col-md-8">

		<c:forEach var="error" items="${errors}">
			<h3 style="color: red">${error}</h3>
		</c:forEach>


		<div>
			<form method="POST" action=""requestCheck.do">
				<table class="table table-hover table-striped">

					<tr>
						<td><b>Available Cash:</b></td>
						<td>$ ${balance }</td>
					</tr>
					
					
					<tr>
						<td><b>Request check amount:</b></td>
						<td><input name="dollarAmount" type="text" required
							class="form-control"
							placeholder="Must be equal to or less than available cash."></td>
					</tr>
					<tr>
						<td><b>Confirm check amount:</b></td>
						<td><input name="confirmAmount" type="text" required
							class="form-control"
							placeholder="Confirm your check amount."></td>
					</tr>
					
					<tr>
						<td></td>
						<td align="right"><button type="submit" name="action"
								value="Request Check" class="btn btn-default">Request Check</button></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>

<jsp:include page="template-buttom.jsp" />