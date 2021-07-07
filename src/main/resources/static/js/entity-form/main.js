$(document).ready(function() {
	main();
});

function main() {
	registerDeletionConfirmation();
}

function registerDeletionConfirmation() {
	let $wrapper = $('#delete-confirmation-wrapper');
	let $btn = $('.btn.delete');
	$btn.click(function(e) {
		e.preventDefault();
		$wrapper.removeClass('hidden');
		$wrapper.find('.yes').click(function() {
			document.location = $btn.attr('href');
			$wrapper.addClass('hidden');
		});
		$wrapper.find('.no').click(function() {
			$wrapper.addClass('hidden');
		});
	});
}