<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<jsp:include page="template-customer-top.jsp" />
<div class="row">
	<div class="col-md-offset-2 col-md-8">
		<h2 class="page-header">${customer.getFirstName()}
			${customer.getLastName()}</h2>
	</div>
</div>
<div class="row">
	<div class="col-md-offset-2 col-md-8">
		<h2 align="center">Research Fund</h2>
		<div class="row">
			<c:forEach var="error" items="${errors}">
				<h3 style="color: red">${error}</h3>
			</c:forEach>
		</div>
			<form method="POST" action="researchFund.do">
				<table class="table table-hover table-striped">
					<tr>
						<td><b>Select the fund:</b></td>
						<td><select class="form-control" name="fundName">
								<option></option>
								<c:choose>
									<c:when test="${ (empty fundList) }"></c:when>
									<c:otherwise>
										<c:forEach var="fund" items="${ fundList }">
											<option>${ fund.getName() }</option>
										</c:forEach>
									</c:otherwise>
								</c:choose>
						</select></td>
					</tr>
					<tr>
						<td></td>
						<td align="right">
							<button type="submit" name="action" value="Fund History"
								class="btn btn-default">Get Details</button>
						</td>
					</tr>
				</table>
			</form>
			<br>
			<c:choose>
				<c:when test="${ (empty msg) }">
				</c:when>
				<c:otherwise>
					<div id="#display">
						<h3 align="center"><b>Fund Details</b></h3>
						<table class="table table-bordered table-hover table-striped">
							<tr>
								<td><b>Fund Name:</b></td>
								<td>${fund.getName()}</td>
								<td><b>Symbol:</b></td>
								<td>${fund.getSymbol()}</td>
							</tr>
						</table>
						<h3 align="center"><b>Fund Statistics</b></h3>
						<table class="table table-bordered table-hover table-striped">
						<tr>
							<th><b>Date</b></th>
							<th><b>Price</b></th>
						</tr>
						<c:forEach var="fund" items="${fundPriceHistory}">
							<tr>
								<td>${fn:substring(fund.getPriceDate(),0,10)}</td>
								<td><fmt:formatNumber type="number" 
            maxFractionDigits="2" minFractionDigits="2" value="${fund.getPrice()/100}" /></td>
							</tr>
						</c:forEach>
						</table>
						<h3 style="color: blue">${msg}</h3>
					</div>
				</c:otherwise>
			</c:choose>

			<br> <br> <br>
	</div>
</div>

<jsp:include page="template-buttom.jsp" />