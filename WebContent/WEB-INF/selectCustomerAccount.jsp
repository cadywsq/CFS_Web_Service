<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="template-employee-top.jsp"/>
<div>
    <div class="row">
        <div class="col-md-offset-2 col-md-8">
            <h3>Select Customer Account</h3>
        </div>
    </div>
    <div class="row">
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
    <script>
        function customerUsername() {
            var uName = document.getElementById("usernameViewAccount").value;
            document.getElementById("usernameViewTransaction").value = uName;
        }
    </script>
    <form action="viewCustomer.do" method="POST">
        <div class="row">
            <div class="col-md-offset-2 col-md-8">
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-3">
                            <label>Choose account(Username):</label>
                            <input class="form-control" placeholder="User Name" id="usernameViewAccount" name="username"
                                   value="${username}">
                        </div>
                    </div>
                    <br> <br>
                </div>

                <div class="row">
                    <div class="col-xs-6" align="right">
                        <input type="submit" class="btn btn-default" name="action"
                               value="View Customer Account">
                    </div>
                </div>
            </div>
        </div>
    </form>
    <form action="viewCustomerTransactionHistory.do" method="POST">
        <div class="col-xs-6" align="left">
            <input type="hidden" id="usernameViewTransaction" name="username">
            <input type="submit" class="btn btn-default" name="action"
                   value="View Customer Transaction History" onclick="customerUsername()"> <br> <br> <br>
        </div>
    </form>
</div>
<jsp:include page="template-buttom.jsp"/>