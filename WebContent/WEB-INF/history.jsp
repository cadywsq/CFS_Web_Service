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
		<h2 align="center">Transaction History</h2>
		<div class="table-responsive">
			<table class="table table-bordered table-hover table-striped">
				<tr>
					<td>Date</td>
					<td>Operation</td>
					<td>Fund Name</td>
					<td>Shares</td>
					<td>Share Price</td>
					<td>Amount</td>
				</tr>
				<c:forEach var="h" items="${history}">
					<tr>
						<c:if test="${empty h.getExecuteDate()}">
							<td>Pending</td>
						</c:if>
						<c:if test="${not empty h.getExecuteDate()}">
							<td>${ h.getExecuteDate() }</td>
						</c:if>

						<td>${ h.getTransactionType() }</td>
						<td>${ historyFundName.get(history.indexOf(h))) }</td>

						<c:if test="${empty h.getShares()}">
							<td>N/A</td>
						</c:if>
						<c:if test="${not empty h.getShares()}">
							<td>${ h.getShares() }</td>
						</c:if>

						<c:if test="${empty h.getExecuteDate()}">
							<td align="right">N/A</td>
						</c:if>
						<c:if test="${not empty h.getExecuteDate()}">
							<td align="right">$${ h.getAmount()/h.getshares() }</td>
						</c:if>

						<c:if test="${empty h.getAmount()}">
							<td align="right">N/A</td>
						</c:if>
						<c:if test="${not empty h.getAmount()}">
							<td align="right">$${ h.getAmount() }</td>
						</c:if>

					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</div>

<jsp:include page="template-buttom.jsp" />