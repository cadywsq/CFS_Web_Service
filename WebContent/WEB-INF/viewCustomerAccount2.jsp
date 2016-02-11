<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="template-employee-top.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row">
	<div class="col-md-offset-2 col-md-8">
		<br> <br>
	</div>
</div>
<div class="row">
	<div class="col-md-offset-2 col-md-8">
		<c:forEach var="error" items="${errors}">
			<h3 style="color: red">${error}</h3>
		</c:forEach>
	</div>
</div>
<div class="row">
	<div class="col-md-offset-2 col-md-8">
		<form method="POST" action="viewCustomerAccount2.do">
			<table class="table table-hover table-striped">
				<tr>
					<td><b>Select the Customer (Username):</b></td>
					<td><select id="cusName" class="form-control" name="username"
						onchange="this.form.submit()">
							<option></option>
							<c:choose>
								<c:when test="${ (empty customerUserNameList) }"></c:when>
								<c:otherwise>
									<c:forEach var="customer" items="${ customerUserNameList }">
										<option value="${ customer }"
											${hmCustomerUserName.get(customer)}>${ customer }</option>
									</c:forEach>
								</c:otherwise>
							</c:choose>
					</select></td>
				</tr>
			</table>
		</form>
	</div>
</div>
<div class="row">
	<div class="col-md-offset-2 col-md-8">
		<c:choose>
			<c:when test="${ (empty message) }">
			</c:when>
			<c:otherwise>
				<h3>
					<b>${message}</b>
				</h3>
				<a href="viewCustomerTransactionHistory2.do?username=${selectedCustomerUserName}">Get
					Transaction Details</a>
				<br>

				<h4 align="center">Fund Information</h4>
				<table class="table table-bordered table-hover">
					<tr>
						<td>Fund</td>
						<td>Symbol</td>
						<td>Shares</td>
						<td>Total Value</td>
					</tr>


					<c:forEach var="fundItem" items="${fundlist}">
						<tr>
							<td>${fundItem.fundName}</td>
							<td>${fundItem.symbol}</td>
							<td align="right">${fundItem.share}</td>
							<td align="right">$ ${fundItem.amount}</td>
						</tr>
					</c:forEach>

				</table>
				<h4 align="center">Account Information</h4>
				<div class="table-responsive">
					<table class="table table-hover table-striped">

						<tr>
							<td><b>User Name:</b></td>
							<td>${customer.getUsername()}</td>
						</tr>


						<tr>
							<td><b>Address:</b></td>
							<td>${customer.getAddressLine1()} ${customer.getAddressLine2()}</td>
						</tr>
						<tr>
							<td><b>City, State and Zip:</b></td>
							<td>${customer.getCity()}, ${customer.getState()} ${customer.getZip()}</td>
						</tr>

					</table>
				</div>

				<h4 align="center">Financial Information</h4>
				<div class="table-responsive">
					<table class="table table-hover table-striped">
						<tr>
							<td><b>Available Cash: </b></td>
							<td align="right">$ ${cash}</td>
							<td>&nbsp;</td>
						</tr>

					</table>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</div>

<jsp:include page="template-buttom.jsp" />