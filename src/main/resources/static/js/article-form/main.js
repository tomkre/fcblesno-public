$(document).ready(function() {
	main();
});

function main() {
	initContentField();
	initPublishedAtField();
}

function initContentField() {
	$('.article-content').jqte();
}

function initPublishedAtField() {
	let $publishedAt = $('#publishedAt');
	let $publish = $('#publish');
	if (!!$publishedAt.val()) {
		$publish.attr('checked', 'true');
	} else {
		$publishedAt.attr('type', 'hidden').val(null);
	}
	$publish.change(function() {
		if ($(this).is(':checked')) {
			let now = new Date().toISOString();
			now = now.slice(0, now.lastIndexOf(':')) + ':00';
			let hour = Number(now.match(/T(\d{2})/)[1]) + 2;
			now = now.replace(/T\d{2}/, 'T' + hour);
			$publishedAt.attr('type', 'datetime-local').val(now);
		} else {
			$publishedAt.attr('type', 'hidden').val(null);
		}
	});
}