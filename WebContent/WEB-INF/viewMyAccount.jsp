<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="template-customer-top.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
	<div class="col-md-offset-2 col-md-8">
		<h2 class="page-header">${customer.getFirstName()}
			${customer.getLastName()}</h2>
	</div>
</div>

<div class="row">
	<div class="col-md-offset-2 col-md-8">
	    <h4 align="center">Fund Information</h4>
        <table class="table table-bordered table-hover">

            <thead>
                <th>Fund</th>
                <th>Symbol</th>
                <th>Shares</th>
                <th>Total Value</th>

            </thead>


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
					<td>${customer.getCity()}, ${customer.getState()} 
						${customer.getZip()}</td>
				</tr>

			</table>
		</div>

		<h4 align="center">Financial Information</h4>

		<div class="table-responsive">
			<table class="table table-hover table-striped">

<%-- 				<tr>
					<td><b>Last Trading Day: </b>(mm/dd/yyyy)</td>
					<td align="right">${lastTradingDay}</td>
					<td width="30%">&nbsp;</td>
				</tr>
 --%>
				<tr>
					<td><b>Available Cash: </b></td>
					<td align="right">$ ${cash}</td>
					<td>&nbsp;</td>
				</tr>

			</table>
		</div>
		<br>

		<br>
	</div>
</div>

<jsp:include page="template-buttom.jsp" />