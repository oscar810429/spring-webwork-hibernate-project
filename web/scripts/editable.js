leopard.Editable=new Class({
	Implements:[Events, Options], 
	options: {inputType:"input", hoverBgColor:"#ffffd3", saveBtnStyle:"btn", cancelBtnStyle:"btn-cancel", showEmpty:false},
	
	initialize:function(container, label, value, options) {
	   if(arguments.length==0) {
	     return;
	   }

	  this.label=label;
  	  this.value=value;
	  this.container=$(container);
	  this.setOptions(options);
	  this._createComponents();
    }, 
    
	setValue: function(value) {
		if (value != this.value) {
			this.value = value;
			this.displayDiv.innerHTML = this.value.nl2br();
			this.input.value = this.value;
		}
	},
    
	getEmptyText:function() {
	   return"点击这里添加"+this.label;
    }, 
    
    
    _createComponents:function() {
	   if(!this.container) {
	    alert("Error: null editor container");
	    return;
       }

      this.displayDiv=new Element("div").inject(this.container);
	  this.editDiv=new Element("div").inject(this.container);
	  if(this.options.displayStyle) {
	    this.displayDiv.addClass(this.options.displayStyle);
       }

      if(this.options.editStyle) {
	    this.editDiv.addClass(this.options.editStyle);
      }

      var st=this.displayDiv.style;
	  this.tmpStyle= {color:st.color, fontStyle:st.fontStyle};
	  this.editDiv.style.display="none";
	  this.input=new Element(this.options.inputType);
	  if(this.options.inputStyle) {
	     this.input.addClass(this.options.inputStyle);
       }

      this.saveBtn=new Element("input",  {type:"button", value:"保存", "class":this.options.saveBtnStyle});
	  this.cancelBtn=new Element("input",  {type:"button", value:"取消", "class":this.options.cancelBtnStyle});
	  
      this.input.injectInside(this.editDiv);
	  //new Element("br").injectInside(this.editDiv);
	  this.saveBtn.injectInside(this.editDiv);
	  new Element("span").setStyles( {"font-size":"12px", padding:"0 1em"}).set("html", "或者").injectInside(this.editDiv);
	  this.cancelBtn.injectInside(this.editDiv);
	  new Element("div").addClass("untitleLine").injectInside(this.editDiv);
	  this.displayDiv.innerHTML=this.value.nl2br();
	  this.input.value=this.value;
	  
	  if(this.options.showEmpty) {
	    this._showEmptyText();
      }

      this.displayDiv.addEvents( {
	    mouseover:this._highlight.bindWithEvent(this), 
	    mouseout:this._mouseOutHandler.bindWithEvent(this),
	    click:this._startEditing.bindWithEvent(this)
      });
      
	  this.saveBtn.addEvent("click", this.saveEdit.bindWithEvent(this));
	  this.cancelBtn.addEvent("click", this.cancelEdit.bindWithEvent(this));
	  this.input.addEvent("keydown", this.keyDown.bindWithEvent(this));
    }, 
    
    _highlight:function() {
    	
	   if(this.hideTimer) {
	    clearTimeout(this.hideTimer);
       }
       var div=this.displayDiv;
	   div.style.backgroundColor=this.options.hoverBgColor;
	   this._showEmptyText();
   }, 
   
   _unhighlight:function() {
	   
	   var div=this.displayDiv;
	   if(this.hideTimer) {
	    clearTimeout(this.hideTimer);
        }

        div.style.backgroundColor="";
	    if(div.innerHTML==this.getEmptyText() && !this.options.showEmpty) {
	      div.innerHTML="&nbsp;";
         }

     }, 

 	_showEmptyText: function() {
 		var div = this.displayDiv;
 		var emptyText = this.getEmptyText();
 		var value = div.innerHTML;
 		
 		if (value != emptyText) {
 			if (value == '' || value == '&nbsp;' || value == ' ' || value.charCodeAt(0) == 160) {
 				div.style.fontStyle = 'italic';
 				div.style.color = '#888888';
 				div.innerHTML = this.getEmptyText();
 			} else {
 				div.style.fontStyle = this.tmpStyle.fontStyle;
 				div.style.color = this.tmpStyle.color;
 			}
 		}
 	},


	_mouseOutHandler: function() {
		if (this.hideTimer) { clearTimeout(this.hideTimer); }
		
		if (window.toolTip && toolTip.showing){
			toolTip.hide();
		}
		
		this.hideTimer = setTimeout(this._unhighlight.bind(this), 1000);
	},
   
	_startEditing:function() {
	   this.editing=true;
	   this._unhighlight();
	   this.displayDiv.hide();
	   this.editDiv.show();
	   this.input.focus();
	   this.input.select();
    }, 

	keyDown: function(event){
 	   var event = new Event(event);
 	   switch(event.key){
 	       case 'enter':
 	           if(this.input.tagName.toLowerCase()=="input") this.saveEdit();
 	           break;
 	   }
 	},
    
 	saveEdit:function() {
	   var value=this.input.value;
	   this.oldValue=this.displayDiv.innerHTML;
	   this.displayDiv.innerHTML=value.nl2br();
	   this.cancelEdit();
	   this.fireEvent("edit", value);
    },
    
    cancelEdit:function() {
	  this.editing=false;
	  this.editDiv.hide();
	  this._showEmptyText();
	  this.displayDiv.show();
    }

});


//~ Class UserEditor =============================================================

leopard.UserEditor = new Class({
	Extends:leopard.Editable,
	initialize: function( container, user, type, options ) {
		this.user = user;
		this.type = type;
		var label, value;
		if (this.type == 'title') {
			label = "空间名称";
			value = this.user.nickname;
		} else {
			label = "空间描述";
			value = this.user.description;
		}
		this.parent(container, label, value, options);
		this.addEvent("edit", this.onEdit.bind(this));
	},
	
	onEdit:function(value) {
		  this.savingValue=value;
		  var c, b;
		  if(this.type=="title") { 
		  if(value==this.user.nickname) {
		    return;
	      }
		  c=value;
		  b="";
	    }else {
		  if(value==this.user.description) {
		  return;
	    }
		//c=this.user.nickname;
		c="";
		b=value;
	}

	$api("painiu.user.setMeta").post( {
		user_id:this.user.id, nickname:c,description:b
	}).addEvents( {
		success:this.onSetMeta.bind(this), error:this.onError.bind(this)
	});
		this.displayDiv.innerHTML="正在保存"+this.label+"...";
	}, 
	
	onSetMeta:function() {
	  if(this.type=="title") {
		this.user.nickname=this.savingValue;
	   }else {
		this.user.description=this.savingValue;
	  }

	     this.value=this.savingValue;
		 this.displayDiv.innerHTML=this.savingValue.nl2br();
		 this.savingValue=null;
	}, 
	
	onError:function(a) {
		this.displayDiv.innerHTML="错误("+a.err.code+"): "+a.err.msg;
		setTimeout(this.setValue(this.oldValue).bind(this), 3000);
	}
	
});


