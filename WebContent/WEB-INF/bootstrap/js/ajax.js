	function getXMLHttpRequest() {
		var xmlHttpReq = false;
		// to create XMLHttpRequest object in non-Microsoft browsers
		if (window.XMLHttpRequest) {
			xmlHttpReq = new XMLHttpRequest();
		} else if (window.ActiveXObject) {
			try {
				// to create XMLHttpRequest object in later versions
				// of Internet Explorer
				xmlHttpReq = new ActiveXObject("Msxml2.XMLHTTP");
			} catch (exp1) {
				try {
					// to create XMLHttpRequest object in older versions
					// of Internet Explorer
					xmlHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
				} catch (exp2) {
					xmlHttpReq = false;
				}
			}
		}
		return xmlHttpReq;
	}

	function makeRequest() {
		var xmlHttpRequest = getXMLHttpRequest();
		xmlHttpRequest.onreadystatechange = getReadyStateHandler(xmlHttpRequest);
		var selectedFund = document.getElementById("fuName").value;
		xmlHttpRequest.open("GET", "sellFund.do?fundName="+selectedFund, true);
		xmlHttpRequest.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		xmlHttpRequest.send(null);
	}
	/*
	 * Returns a function that waits for the state change in XMLHttpRequest
	 */
	function getReadyStateHandler(xmlHttpRequest) {

		// an anonymous function returned
		// it listens to the XMLHttpRequest instance
		return function() {
			if (xmlHttpRequest.readyState == 4) {
				if (xmlHttpRequest.status == 200) {
					var something = '${fundSymbol}';
					alert(something);
					document.getElementById("fundSymbol").innerHTML = '${fundSymbol}';
					document.getElementById("sharesown").innerHTML = 'chi';
				} else {
					alert("HTTP error " + xmlHttpRequest.status + ": "
							+ xmlHttpRequest.statusText);
				}
			}
		};
	}