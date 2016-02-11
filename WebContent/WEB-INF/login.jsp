<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="author" content="Team Eleven">
<title>Carnegie Financial Services</title>
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="bootstrap/css/sb-admin.css" rel="stylesheet">
<link href="bootstrap/css/gencss.css" rel="stylesheet">
</head>
<body style="background-image: url(img/tartan-background.jpg);">
	<div>
		<!-- Navigation -->
		<div class="container-fluid">
			<div class="row">
				<div class="col-md-offset-1 col-md-11">
					<nav class="navbar" role="navigation">
						<div class="navbar-header">
							<a href="customerMain.html"> 
							<img src="img/logo.JPG" style="height: 130px; width:1000px" hspace="36">
							</a>
						</div>
					</nav>
				</div>
			</div>
		</div>
		<div id="page-wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-md-offset-1 col-md-10">
						<div>
							<br>
							<c:forEach var="error" items="${errors}">
								<h3 style="color: red">${error}</h3>
							</c:forEach>
							<br>
						</div>
						<table>
							<tr>
								<td>
								<form method="post" action="login.do">
									<div class="form-group">
										<label>Username</label>
										<input class="form-control" placeholder="User Name" name="username" value="${username}">
									</div>
									<div class="form-group">
										<label>Password</label>
										<input class="form-control" type="password" placeholder="Case sensitive" name="password">
									</div>
									<input type="submit" class="btn btn-default" name="action" value="Customer Login">
									<input type="submit" class="btn btn-default" name="action" value="Employee Login">
									
								</form>
								</td>
								<td>
									<div class="ticker" style="margin-left: 60px;">
										<h3>Latest Market News</h3>
										<ul id="ticker">
											<li>Asia stocks rise as ECB soothes nervous markets, oil extends bounce</li>
											<li>China shares try to rally on global stimulus hopes <br>
											</li>
											<li>JPMorgan CEO gets 35% pay raise to $27M amid cutbacks <br>
											</li>
											<li>Starbucks CEO confident in China bet despite turmoil <br>
											</li>
										</ul>
									</div>

								</td>
							</tr>
						</table>

						<jsp:include page="template-buttom.jsp" />