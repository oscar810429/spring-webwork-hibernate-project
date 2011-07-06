admin = {
	dialog : {
		element : null,
		create : function() {
			var div = this.element = new Element('div');
			div.setStyles( { 
				"left" : "380px", 
				"top" : "75px", 
				"position" : "absolute", 
				"z-index" : "1009", 
				"display" : "none",
				"padding" : "5px",
				"border" : "2px solid #D3DEFA",
				"background-color" : "#EEF2FD" 
			});
			document.body.appendChild(div);
			this.bgIframe = new BackgroundIframe(div);
		},
		show : function(msg) {
			if (!this.element) { this.create(); }
			this.element.setHTML(msg);
			this.bgIframe.onResized();
			this.element.show();
		},
		hide : function() {
			if (this.element) {
				this.element.hide();
			}
		}
	},
	
	recommend : {
		photo : function(photo_id, score) {
			admin.dialog.show(lang['msg_saving']);
			api.setPhotoScore(photo_id, score, this.onRecommend);
		},
		album : function(album_id, score) {
			admin.dialog.show(lang['msg_saving']);
			api.setAlbumScore(album_id, score, this.onRecommend);
		},
		group : function(group_id, score) {
			admin.dialog.show(lang['msg_saving']);
			api.setGroupScore(group_id, score, this.onRecommend);
		},
		user : function(user_id, score) {
			admin.dialog.show(lang['msg_saving']);
			api.setUserScore(user_id, score, this.onRecommend);
		},
		
		onRecommend: function(rspTxt) {
			admin.dialog.hide();
			var rsp = Json.evaluate(rspTxt);
			var success = (rsp && rsp.stat == 'ok') ? true : false;
			if (!success) {
				alert(lang['msg_error'] + ':' + rspTxt);
			} else {
				//Element.toggle('btn_recommend', 'btn_cancel_recommend');
			}
		}
	}
};
