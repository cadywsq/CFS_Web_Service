<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="template-employee-top.jsp" />
<div>
	<div class="row">
		<div class="col-md-offset-2 col-md-8">
			<h2>Create Fund</h2>
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
		</div>
	</div>
	<div class="row">
		<div class="col-md-offset-2 col-md-8">
			<form method="post" action="createFund.do">
				<div class="form-group">
					<div class="row">
						<div class="col-xs-2">
							<label>Fund Name:<span class="astreik">*</span></label>
						</div>
						<div class="col-md-6">
							<input name="fundName" required class="form-control"
								placeholder="Ex: DSP Blackrock">
						</div>
					</div>
				</div>
				<br>
				<div class="form-group">
					<div class="row">
						<div class="col-xs-2">
							<label>Symbol:<span class="astreik">*</span></label>
						</div>
						<div class="col-md-6">
							<input name="symbol" required class="form-control" maxlength="5"
								placeholder="Max 5 Character Capital Ex: DSPBR">
						</div>
					</div>
				</div>
				<br>
				<div class="row">
					<div class="col-xs-6" align="right">
						<input type="submit" class="btn btn-default" name="action"
							value="Create Fund"> <br> <br> <br>
					</div>
				</div>
			</form>
		</div>
	</div>
	<jsp:include page="template-buttom.jsp" />