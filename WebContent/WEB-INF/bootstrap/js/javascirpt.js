function ticker() {
	$('#ticker li:first').slideUp(function() {
		$(this).appendTo($('#ticker')).slideDown();
	});
}

setInterval(function() {
	ticker();
}, 3000);