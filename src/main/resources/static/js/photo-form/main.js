$(document).ready(function() {
	const MAX_UPLOAD_SIZE = 1048576;
	const $preview = $('#entity-photo');
	const $photo = $('#photo');
	const $error = $photo.next();
	
	$photo.change(function() {
		let file = this.files[0];
		$preview.removeClass('error');
		$error.addClass('hidden');
		if (file.size > MAX_UPLOAD_SIZE) {
			$preview.addClass('error');
			$error.removeClass('hidden');
			$preview.attr('src', '/images/articles/default.jpg');
			$photo.val(null);
			return;
		}
		let reader = new FileReader();
		reader.readAsDataURL(this.files[0]);
		reader.onload = function() {
			$preview.attr('src', this.result);
		}
	});
});