<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="template-employee-top.jsp" />

<div class="row">
	<div class="col-md-offset-2 col-md-8">
		<h2 class="page-header">${customer.getFirstName()}
			${customer.getLastName()}</h2>
		<form action="selectCustomer.do">
			<input type="submit" value="View Customer Account" name="username">
			<input type="submit" value="View Transaction History" name="username">
			<input type="hidden" name="customer" value="${username}">
		</form>
	</div>
</div>

<div class="row">
	<div class="col-md-offset-2 col-md-8">
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
					<td>${customer.getCity()} ${customer.getState()} ${customer.getZip()}</td>
				</tr>

			</table>
		</div>

		<h4 align="center">Financial Information</h4>

		<div class="table-responsive">
			<table class="table table-hover table-striped">

				<tr>
					<td><b>Available Cash: </b>(Considering requested checks and
						pending funds)</td>
					<td align="right">${customer.getCash()}</td>
					<td>&nbsp;</td>
				</tr>

			</table>
		</div>
		<br>

		<h4 align="center">Fund Information</h4>
		<table class="table table-bordered table-hover">

			<tr>
				<td>Fund</td>
				<td>Ticker</td>
				<td>Shares</td>
				<td>Total Value</td>

			</tr>


			<c:forEach var="fund" items="${fundList}">
				<tr>
					<td>${ fund.getFundName() }</td>
					<td>${ fund.getTicker() }</td>
					<td align="right">${ fund.getShares() }</td>
					<td align="right">${ fund.getTotal() }</td>
				</tr>
			</c:forEach>

		</table>
		<br>
	</div>
</div>

<jsp:include page="template-buttom.jsp" />