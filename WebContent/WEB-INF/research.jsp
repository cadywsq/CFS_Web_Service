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
		<h4 align="center">Research Fund</h4>
		<div>
			<form method="POST" action=""researchFund.do">
				<table class="table table-hover table-striped">
					<tr>
						<td><b>Select the fund:</b></td>
						<td><select class="form-control" name="fund">
								<option></option>

								<c:choose>
									<c:when test="${ (empty fundList) }"></c:when>
									<c:otherwise>
										<c:forEach var="f" items="${ fundList }">
											<option>${ f.getName() }</option>
										</c:forEach>
									</c:otherwise>
								</c:choose>
						</select></td>
					</tr>
					<tr>
						<td></td>
						<td align="right"><button type="submit" name="action"
								value="buy" class="btn btn-default">Submit</button></td>
					</tr>
				</table>
			</form>
			<br>
			<h4 align="center">Fund History</h4>
			<table class="table table-bordered table-hover table-striped">
				<tr>
					<td><b>Fund Name:</b></td>
					<td>${fundName}</td>
				</tr>
				<tr>
					<td><b>Symbol:</b></td>
					<td>${symbol}</td>
				</tr>
				<tr>
					<td colspan="2" align="center"><b>Fund Statistics</b></td>
				</tr>
				<tr>
					<td><b>Date</b></td>
					<td><b>Price</b></td>
				</tr>
				<c:forEach var="fund" items="${historyList}">
					<tr>
						<td align="right">${ fund.getpriceDate() }</td>
						<td align="right">$${ fund.getPrice() }</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</div>

<jsp:include page="template-buttom.jsp" />