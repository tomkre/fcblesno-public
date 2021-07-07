$(document).ready(function() {
	registerPagination();
	registerFormFeedback();
});

function registerPagination() {
	$('#article-type').change(function() {
		let filteredType = $(this).val();
		location = location.toString().replace(/([?].+|$)/, `?page=1&type=${filteredType}`);
	});
}

function registerFormFeedback() {
	let $wrapper = $('#form-feedback-wrapper');
	let $btn = $wrapper.find('.btn.ok');
	let csrfToken = $('meta[name=_csrf]').attr('content');
	$.ajaxSetup({
		headers: { 'X-CSRF-TOKEN': csrfToken }
	});
	$btn.click(function(e) {
		$.post('/admin/rest/removeSessionAttribute/formFeedback');
		$wrapper.hide();
	});
}