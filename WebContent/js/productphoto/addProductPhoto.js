$("#productphoto_photo").on('change',function() {
			$(".pic_img").remove();
			var reader = new FileReader();
			reader.readAsDataURL(this.files[0])
			reader.addEventListener("load", function(e) {
				console.log(reader);
				var base64 = e.target.result;
				$(".productphoto_photo_display").append(
						'<img src="' + base64 + '" class="pic_img">')
			})
		})