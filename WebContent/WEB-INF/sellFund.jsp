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

		<div>
			<form method="POST" action="sellFund.do">
				<table class="table table-hover table-striped">
					<tr>
						<td><b>Select the fund:</b></td>
						<td><select id="fuName" class="form-control" name="fundName"
							onchange="this.form.submit()">
								<option></option>
								<c:choose>
									<c:when test="${ (empty fundList) }"></c:when>
									<c:otherwise>
										<c:forEach var="fund" items="${ fundList }">
											<option value="${ fund.getName() }"  ${hmFundBean.get(fund.getName())}>${ fund.getName() }</option>
										</c:forEach>
									</c:otherwise>
								</c:choose>
						</select></td>
					</tr>
					<tr>
						<td><b>Fund Name</b></td>
						<td><div id="fundNameSelected">
								<b>${selectedfundName}</b>
							</div></td>
					</tr>

					<tr>
						<td><b>Fund Symbol</b></td>
						<td><div id="fundSymbol">
								<b>${fundSymbol}</b>
							</div></td>
					</tr>
					<tr>
						<td><b>Shares you own</b></td>
						<td><div id="sharesown">
								<b></b>${sharesown}</div></td>
					</tr>
					<tr>

						<td>Shares to sell</td>
						<td><input type="text" class="form-control" name="shares"
							placeholder="Num must be below or equal shares you own"></td>
					</tr>
					<tr>
					</tr>
					<tr>
						<td></td>
						<td align="right"><button type="submit" name="action"
								value="Sell Fund" class="btn btn-default">Sell Fund</button></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>

<jsp:include page="template-buttom.jsp" />