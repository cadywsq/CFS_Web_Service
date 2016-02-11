<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="template-customer-top.jsp" />

<div class="row">
	<div class="col-md-offset-1 col-md-9">
		<h2 align="center">Transaction History</h2>
			<table class="table table-bordered table-hover table-striped">
				<thead>
					<tr>
						<th>S No.</th>
						<th>Transaction Date</th>
						<th>Operation</th>
						<th>Fund Name</th>
						<th>Shares</th>
						<th>Share Price</th>
						<th>Amount</th>
					</tr>
				</thead>
				<tbody id="myTable">
					<c:set var="serialnumber" value="${1}" />
					<c:set var="pending" value="${Pending}" />
					<c:forEach var="h" items="${historyRecord}">
						<c:set var="type" value="${h.getExecuteDate()}" />
						
						<tr>
							<td>${serialnumber }</td>
							<td><c:choose>
									<c:when test="${ (type eq pending) }">
										<span style="color: red">Pending</span>
									</c:when>
									<c:otherwise>
									 ${ fn:substring(type,0,10) }
								</c:otherwise>
								</c:choose></td>
							<td>${ h.getTransactionType() }</td>
							<td>${ h.getFundName() }</td>
							<td>${ h.getShares() }</td>
							<td>${ h.getSharePrice() }</td>
							<td>${ h.getAmount() }</td>
						</tr>
						<c:set var="serialnumber" value="${serialnumber + 1}" />
					</c:forEach>
				</tbody>
			</table>
		<div class="col-md-12 text-center">
			<ul class="pagination pagination-lg pager" id="myPager"></ul>
		</div>
	</div>
</div>

<jsp:include page="template-buttom.jsp" />