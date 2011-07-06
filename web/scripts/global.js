/*	ENVIRONMENT	*/
env = {
	ie  : (navigator.appName == "Microsoft Internet Explorer"),    // Internet Explorer
	ie5 : this.isMSIE && (ua.indexOf('MSIE 5') != -1),
	ie50: this.isMSIE && (ua.indexOf('MSIE 5.0') != -1),
	gk  : /gecko/i.test(navigator.userAgent),   // Gecko based browsers
	ff  : /firefox/i.test(navigator.userAgent), // Firefox browsers
	op  : /opera/i.test(navigator.userAgent),   // Opera
	sf  : /safari/i.test(navigator.userAgent) | /AppleWebKit/i.test(navigator.userAgent)  // Safari
}

/*
log = {
    debug: function(msg) {
        if (!window.console) {
            var el = window.console = new Element("div");
            el.setStyles({background: '#000', 
                        color: 'white', 
                        fontWeight: 'bold', 
                        position: 'absolute', 
                        left: '0', 
                        top: '0', 
                        width: '100%', 
                        padding: '1em', 
                        zIndex: 29991 });
            document.body.appendChild(el);  
            //el.inject(document.body);
        }
        window.console.setHTML(msg);
    }
}
*/

/* MOOTOOLS EXTENSIONS */
String.extend({
    nl2br: function() {
        return this.replace('\n', '<br \/>\n');
	},
	escapeHTML: function() {
        var div = document.createElement('div');
        var text = document.createTextNode(this);
        div.appendChild(text);
        return div.innerHTML;
    },
	len: function() {
		var data = this.toString();
		var len = 0;
		for(var i = 0; i < data.length; i++) {
			var c = data.charCodeAt(i);
			if (c > 0x80)
				len += 2;
			else 
				len += 1;
		}
		return len;
	},
	brief: function(length) {
		var len = 0;
		var buf = '';
		for(var i = 0; i < this.length; i++) {
			var c = this.charCodeAt(i);
			if (c > 0x80)
				len += 2;
			else 
				len += 1;
			if (len <= length)
				buf += this.charAt(i);
			else
				return buf;
		}
		return buf;
	}
});

/*
Function.extend({
	bind: function(bind){
		var fn = this, args = $A(arguments), bind = args.shift();
		return function(){
			return fn.apply(bind, args.concat($A(arguments)));
		};
	},
	bindAsEventListener: function(bind){
		var fn = this, args = $A(arguments), bind = args.shift();
		return function(e){
			fn.apply(bind, [e || window.event].concat(args));
			return false;
		};
	}
});
*/
/*
Window.extend({
	stopEvent: function(e){
		if (e.stopPropagation){
			e.stopPropagation();
			e.preventDefault();
		} else {
			e.returnValue = false;
			e.cancelBubble = true;
		}
		return false;
	}
});
*/

Array.extend({
    /*
	indexOf: function(obj){
		var l = this.length, i = 0;
		for (i; i < l; i++) if (this[i] == obj) return i;
		return -1;
	},
	*/
    collect: function(iterator) {
        var results = [];
        this.each(function(value, index) {
            results.push(iterator(value, index));
        });
        return results;
    },
    findAll: function(iterator) {
        var results = [];
        this.each(function(value, index) {
            if (iterator(value, index))
                results.push(value);
        });
        return results;
    },
    pluck: function(property) {
        return this.collect(function(value, index) { return value[property]; });
    },
    reject: function(iterator) {
        var results = [];
        this.each(function(value, index) {
            if (!iterator(value, index))
                results.push(value);
        });
        return results;
    }
});

Element.extend({
	classNames: function() {
		return this.className.clean().split(/\s+/);
	},
	
	visible: function(element) {
    	return this.getStyle('display') != 'none';
	},

	hide: function() {
		this.setStyle('display', 'none');
		return this;
	},

	show: function() {
		this.setStyle('display', '');
		return this;
	},

	toggle: function() {
		this[this.visible()?'hide':'show']();
		return this;
	},
	
	within: function(x, y) {
		this.offset = [this.getLeft(), this.getTop()];

		return (y >= this.offset[1] &&
				y <  this.offset[1] + this.offsetHeight &&
				x >= this.offset[0] &&
				x <  this.offset[0] + this.offsetWidth);
	},
	
	getOpacity: function() {
		var opacity;
		if (opacity = this.getStyle('opacity'))  
			return parseFloat(opacity);
		if (opacity = (this.getStyle('filter') || '').match(/alpha\(opacity=(.*)\)/))  
  			if(opacity[1]) return parseFloat(opacity[1]) / 100;  
		return 1.0;
	},
	
	fade: function(options) {
		options = Object.extend({
			duration: 200,
			from: 0.0,
			to: 1.0
		}, options || {});
		if (!this.visible()) { 
			this.show();
		}
		this.setStyle("opacity", options.from);
		this.effect("opacity", options).custom(options.from, options.to);
		return this;
	},
	
	fadeOut: function(options) {
		options = Object.extend({
			from: this.getOpacity(),
			to: 0.0
		}, options || {});
		return this.fade(options);
	},
	
	disable: function() {
		$A(this.getElementsByTagName('*')).each(function(el){
			$(el);
			if (['select', 'input', 'textarea'].test(el.getTag())) {
				el.blur();
				el.disabled = true;
			}
		});
	},
	
	enable: function() {
		$A(this.getElementsByTagName('*')).each(function(el){
			$(el);
			if (['select', 'input', 'textarea'].test(el.getTag())) {
				el.disabled = '';
			}
		});
	}
});

Element.visible = function(element) {
	return $(element).visible();	
}
Element.show = function() {
	for (var i = 0; i < arguments.length; i++) {
		$(arguments[i]).show();
    }
}
Element.hide = function() {
	for (var i = 0; i < arguments.length; i++) {
		$(arguments[i]).hide();
    }
}
Element.toggle = function() {
	for (var i = 0; i < arguments.length; i++) {
		$(arguments[i]).toggle();
    }
}

/*
Window.getSizeArray = function() {
	var xScroll = this.getScrollWidth();
	var yScroll = this.getScrollHeight();
	
	var windowWidth = this.getWidth();
	var windowHeight = this.getHeight();	
	
	// for small pages with total height less then height of the viewport
	if(yScroll < windowHeight){
		pageHeight = windowHeight;
	} else { 
		pageHeight = yScroll;
	}
	// for small pages with total width less then width of the viewport
	if(xScroll < windowWidth){	
		pageWidth = windowWidth;
	} else {
		pageWidth = xScroll;
	}
	return [pageWidth, pageHeight, windowWidth, windowHeight];
}

Window.getScrollArray = function() {
	return [this.getScrollLeft(), this.getScrollTop()];
}
*/
function InputHint(element, hintText) {
	element = $(element);
	element.onfocus = function() {
		if (this.value == hintText) this.value = '';
	};
	element.onblur = function() {
		if (this.value == '') this.value = hintText;
	};
}

// common functions --------------------------------------------------------
function hyperlink(txt) {
	if (txt.indexOf('http://') > -1) {
		var txtA = txt.split('\n');
		for (var y=0; y<txtA.length; y++) {
			var subtxtA = txtA[y].split(' ');
			for (var t=0; t<subtxtA.length; t++) {
				var openParen = '';
				if (subtxtA[t].substr(0,1) == '(') {
					// remove open Paren
					subtxtA[t] = subtxtA[t].replace('(', '')
					openParen = '(';
				}
				if (subtxtA[t].substr(0,7) == 'http://') {
					var url = subtxtA[t];
					var paren = '';
					if (url.substr(url.length-1, 1) == ')') {
						url = url.substr(0,url.length-1);
						paren = ')';
					}
					var period = '';
					if (url.substr(url.length-1, 1) == '.') {
						url = url.substr(0,url.length-1);
						period = '.';
					}
					subtxtA[t] = '<a href="'+url+'" onclick="">' + url + '<\/a>'+paren+period;
					
				}
				// add open Paren back in
				subtxtA[t] = openParen+subtxtA[t]
			}
			txtA[y] = subtxtA.join(' ');
			//txtA[y] = txtA[y].replace('> <', '>&nbsp;<');
		}
		txt = txtA.join('<br \/>');
	}

	return txt;
}

// UTF-8 URL encoding 
// code from - www.flickr.com
function escape_utf8(data) {
	if (data == '' || data == null){
		return '';
	}
	data = data.toString();
	var buffer = '';
	for(var i=0; i<data.length; i++){
		var c = data.charCodeAt(i);
		var bs = new Array();

		if (c > 65536){
			// 4 bytes
			bs[0] = 240 | ((c & 1835008) >>> 18);
			bs[1] = 128 | ((c & 258048) >>> 12);
			bs[2] = 128 | ((c & 4032) >>> 6);
			bs[3] = 128 | (c & 63);
		} else {
			// 1 byte
			if( c > 2048) {
				bs[0] = 224 | ((c & 61440) >>> 12);
				bs[1] = 128 | ((c & 4032) >>> 6);
				bs[2] = 128 | (c & 63);
			} else {
				if(c > 128){
					bs[0] = 192 | ((c & 1984) >>> 6);
					bs[1] = 128 | (c & 63);
				} else {
					bs[0] = c;
				}
			}
		}

		if(bs.length > 1){
			for(var j = 0; j < bs.length;j++){
				var b = bs[j];
				var hex = nibble_to_hex((b & 240) >>> 4) + nibble_to_hex(b & 15);
				buffer += "%" + hex;
			}
		}else{
			buffer += encodeURIComponent(String.fromCharCode(bs[0]));
		}
	}

	return buffer;
}

function nibble_to_hex(nibble){
	var chars = '0123456789ABCDEF';
	return chars.charAt(nibble);
}

var urls = {
	scheme: 'http',
	_: function(path) {
		if (window.app_domain && path.indexOf('/') == 0) {
			return urls.scheme + '://www.' + app_domain + path;
		}
		return path;
	},
	_u: function() {
		var args = $A(arguments);
		if (args.length == 0) {
			return urls._u(window.current_user);
		} 
		if (args.length == 1) {
			if (typeof args[0] == 'string') {
				return urls._u(window.current_user, args[0]);
			} else {
				return urls._u(args[0], '/');
			}
		}
		if (args.length == 2) {
			if (window.app_domain && args[1].indexOf('/') == 0) {
				var username = 'www';
				if (args[0]) {
					username = args[0].username;
				}
				return urls.scheme + '://' + username + '.' + app_domain + args[1];
			}
			return args[1];
		}
	},
	_m: function(path) {
		if (window.media_root && path.indexOf('/') == 0) {
			return media_root + path;
		}
		return path;
	}
}

/*
var imageStore = {
	images: {},
	
	load: function(name, location) {
		if (this.images[name]) {return;}
		
		var image = new Image();
		image.src = location;
		this.images[name] = image;
	},
	
	get: function(name) {
		return this.images[name];
	}
};
*/


var overlay = {
	show: function(options) {
		this.el = $('overlay');
		
		if (!this.el) {
			// create overlay
			var body = document.getElementsByTagName("body").item(0);
			
			this.el = new Element("div").setProperty('id', 'overlay').setStyle('display', 'none').injectInside(body);
		}
		
		if (!this.iframe) {
			this.iframe = new BackgroundIframe(this.el);
		}
		
		options = Object.extend({
			from: 0.0, 
			to: 0.8 
		}, options || {});
		
		if (options.onclick) {
            this.el.onclick = options.onclick;
		}
		
		// stretch overlay to fill page and fade in
		this.el.setStyle("height", Window.getScrollHeight() + "px");
		this.el.fade(options);
		
		this.iframe.onResized();
	},
	
	hide: function(options) {
		if (!this.el) { return; }
		
		options = Object.extend({
			onComplete: function() { overlay.el.hide(); }
		}, options || {});
		
		if (this.el.onclick) {
			this.el.onclick = null;
		}
		this.el.fadeOut(options);
	}
}

/**
 * For IE z-index schenanigans
 * Two possible uses:
 *   1. new BackgroundIframe(node)
 *        Makes a background iframe as a child of node, that fills area (and position) of node
 *
 *   2. new BackgroundIframe()
 *        Attaches frame to document.body.  User must call size() to set size.
 */
var BackgroundIframe = new Class({
	initialize: function(node) {
		this.ie = env.ie;
		this.iframe = null;
		
		if(this.ie) {
			var html=
				 "<iframe "
				+"style='position: absolute; left: 0px; top: 0px; width: 100%; height: 100%;"
				+        "z-index: -1; filter:Alpha(Opacity=\"0\");' "
				+">";
			this.iframe = new Element(html);
			if(node) {
				this.domNode = node;
				this.iframe.injectInside(node);
			} else {
				this.iframe.injectInside(document.body);
				this.iframe.hide();
			}
		}
	},
	// TODO: this function shouldn't be necessary but setting width=height=100% doesn't work!
	onResized: function(){
		if(this.iframe && this.domNode){
			var w = this.domNode.offsetWidth;
			var h = this.domNode.offsetHeight;
			if (w == 0 || h == 0 ){
//				setTimeout(this.onResized.bind(this), 50);
				this.onResized.bind(this).delay(50);
				return;
			}
			this.iframe.setStyles({width: w + "px", height: h + "px"});
//			var s = this.iframe.style;
//			s.width = w + "px";
//			s.height = h + "px";
		}
	},
	// Call this function if the iframe is connected to document.body rather
	// than the node being shadowed (TODO: erase)
	size: function(node) {
		if(!this.iframe) { return; }
		
		node = new Element(node);
		var coords = [node.getLeft(), node.getTop(), node.offsetWidth, node.offsetHeight];
		
		if (coords[2] == 0 || coords[3] == 0) {
			var iframe = this;
			setTimeout(function() { iframe.size(node); }, 50);
		} else {
			this.iframe.setStyles({
				width: coords[2] + "px",
				height: coords[3] + "px",
				left: coords[0] + "px",
				top: coords[1] + "px"
			});
		}
	},

	setZIndex: function(node /* or number */) {
		if(!this.iframe) { return; }

		if(isNaN(node)) {
			this.iframe.setStyle("z-index", node.getStyle("z-index") - 1);
		} else {
			this.iframe.setStyle("z-index", node);
		}
	},

	show: function() {
		if(!this.iframe) { return; }
		this.iframe.show();
	},
	
	hide: function() {
		if(!this.iframe) { return; }
		this.iframe.hide();
	},
	
	remove: function() {
		this.iframe.remove();
	}
});

var Dropdown = new Class({
	initialize: function() {
		this.msie = env.ie;
		
		if ($('dropdown').visible()) { this.hide(); }
		$('dropdown-trigger').onclick = this.toggle.bind(this);
		$('dropdown-closer').onclick = this.hide.bind(this);
		this.mouseUpHander = this.mouseUp.bindAsEventListener(this);
		//this.shadow = new Shadow('dropdown-menu');
	},
	show: function() {
		if (this.msie) {
			if (!this.iframe) {
				this.iframe = new BackgroundIframe($("dropdown-menu"));
			}
			this.iframe.onResized();
		}
		$("dropdown").show();
		//var dd = $('dropdown-menu');
		//this.shadow.size(dd.offsetWidth, dd.offsetHeight);
		this.showing = true;
		$(document.body).addEvent("mouseup", this.mouseUpHander);
		return false;
	},
	hide: function() {
		$('dropdown').hide();
		this.showing = false;
		$(document.body).removeEvent("mouseup", this.mouseUpHander);
		return false;
	},
	toggle: function() {
		if (this.showing) {
			this.hide();
		} else {
			this.show();
		}
		return false;
	},
	mouseUp: function(e) {
		if (this.showing) {
			if (!$('dropdown-menu').within(e.clientX, e.clientY)
				&& !$('menu-dropper').within(e.clientX, e.clientY)) {
				this.hide();
			}
		}
	}
});

function initDropdown() {
	if ($('dropdown')) { myDropdown = new Dropdown(); }
}

// init every things
/*
function initOnDomReady() {
	if (window.initDropdown) initDropdown();
	if (window.initBuddyicon) initBuddyicon();
	if (window.initLightbox) initLightbox();
}
*/
//Window.onDomReady(initOnDomReady);

/**
 * SWFObject v1.4.4: Flash Player detection and embed - http://blog.deconcept.com/swfobject/
 *
 * SWFObject is (c) 2006 Geoff Stearns and is released under the MIT License:
 * http://www.opensource.org/licenses/mit-license.php
 *
 * **SWFObject is the SWF embed script formerly known as FlashObject. The name was changed for
 *   legal reasons.
 */
if(typeof deconcept == "undefined") var deconcept = new Object();
if(typeof deconcept.util == "undefined") deconcept.util = new Object();
if(typeof deconcept.SWFObjectUtil == "undefined") deconcept.SWFObjectUtil = new Object();
deconcept.SWFObject = function(swf, id, w, h, ver, c, useExpressInstall, quality, xiRedirectUrl, redirectUrl, detectKey){
	if (!document.getElementById) { return; }
	this.DETECT_KEY = detectKey ? detectKey : 'detectflash';
	this.skipDetect = deconcept.util.getRequestParameter(this.DETECT_KEY);
	this.params = new Object();
	this.variables = new Object();
	this.attributes = new Array();
	if(swf) { this.setAttribute('swf', swf); }
	if(id) { this.setAttribute('id', id); }
	if(w) { this.setAttribute('width', w); }
	if(h) { this.setAttribute('height', h); }
	if(ver) { this.setAttribute('version', new deconcept.PlayerVersion(ver.toString().split("."))); }
	this.installedVer = deconcept.SWFObjectUtil.getPlayerVersion();
	if(c) { this.addParam('bgcolor', c); }
	var q = quality ? quality : 'high';
	this.addParam('quality', q);
	this.setAttribute('useExpressInstall', useExpressInstall);
	this.setAttribute('doExpressInstall', false);
	var xir = (xiRedirectUrl) ? xiRedirectUrl : window.location;
	this.setAttribute('xiRedirectUrl', xir);
	this.setAttribute('redirectUrl', '');
	if(redirectUrl) { this.setAttribute('redirectUrl', redirectUrl); }
}
deconcept.SWFObject.prototype = {
	setAttribute: function(name, value){
		this.attributes[name] = value;
	},
	getAttribute: function(name){
		return this.attributes[name];
	},
	addParam: function(name, value){
		this.params[name] = value;
	},
	getParams: function(){
		return this.params;
	},
	addVariable: function(name, value){
		this.variables[name] = value;
	},
	getVariable: function(name){
		return this.variables[name];
	},
	getVariables: function(){
		return this.variables;
	},
	getVariablePairs: function(){
		var variablePairs = new Array();
		var key;
		var variables = this.getVariables();
		for(key in variables){
			variablePairs.push(key +"="+ variables[key]);
		}
		return variablePairs;
	},
	getSWFHTML: function() {
		var swfNode = "";
		if (navigator.plugins && navigator.mimeTypes && navigator.mimeTypes.length) { // netscape plugin architecture
			if (this.getAttribute("doExpressInstall")) { this.addVariable("MMplayerType", "PlugIn"); }
			swfNode = '<embed type="application/x-shockwave-flash" src="'+ this.getAttribute('swf') +'" width="'+ this.getAttribute('width') +'" height="'+ this.getAttribute('height') +'"';
			swfNode += ' id="'+ this.getAttribute('id') +'" name="'+ this.getAttribute('id') +'" ';
			var params = this.getParams();
			 for(var key in params){ swfNode += [key] +'="'+ params[key] +'" '; }
			var pairs = this.getVariablePairs().join("&");
			 if (pairs.length > 0){ swfNode += 'flashvars="'+ pairs +'"'; }
			swfNode += '/>';
		} else { // PC IE
			if (this.getAttribute("doExpressInstall")) { this.addVariable("MMplayerType", "ActiveX"); }
			swfNode = '<object id="'+ this.getAttribute('id') +'" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="'+ this.getAttribute('width') +'" height="'+ this.getAttribute('height') +'">';
			swfNode += '<param name="movie" value="'+ this.getAttribute('swf') +'" />';
			var params = this.getParams();
			for(var key in params) {
			 swfNode += '<param name="'+ key +'" value="'+ params[key] +'" />';
			}
			var pairs = this.getVariablePairs().join("&");
			if(pairs.length > 0) {swfNode += '<param name="flashvars" value="'+ pairs +'" />';}
			swfNode += "</object>";
		}
		return swfNode;
	},
	write: function(elementId){
		if(this.getAttribute('useExpressInstall')) {
			// check to see if we need to do an express install
			var expressInstallReqVer = new deconcept.PlayerVersion([6,0,65]);
			if (this.installedVer.versionIsValid(expressInstallReqVer) && !this.installedVer.versionIsValid(this.getAttribute('version'))) {
				this.setAttribute('doExpressInstall', true);
				this.addVariable("MMredirectURL", escape(this.getAttribute('xiRedirectUrl')));
				document.title = document.title.slice(0, 47) + " - Flash Player Installation";
				this.addVariable("MMdoctitle", document.title);
			}
		}
		if(this.skipDetect || this.getAttribute('doExpressInstall') || this.installedVer.versionIsValid(this.getAttribute('version'))){
			var n = (typeof elementId == 'string') ? document.getElementById(elementId) : elementId;
			n.innerHTML = this.getSWFHTML();
			return true;
		}else{
			if(this.getAttribute('redirectUrl') != "") {
				document.location.replace(this.getAttribute('redirectUrl'));
			}
		}
		return false;
	}
}

/* ---- detection functions ---- */
deconcept.SWFObjectUtil.getPlayerVersion = function(){
	var PlayerVersion = new deconcept.PlayerVersion([0,0,0]);
	if(navigator.plugins && navigator.mimeTypes.length){
		var x = navigator.plugins["Shockwave Flash"];
		if(x && x.description) {
			PlayerVersion = new deconcept.PlayerVersion(x.description.replace(/([a-zA-Z]|\s)+/, "").replace(/(\s+r|\s+b[0-9]+)/, ".").split("."));
		}
	}else{
		// do minor version lookup in IE, but avoid fp6 crashing issues
		// see http://blog.deconcept.com/2006/01/11/getvariable-setvariable-crash-internet-explorer-flash-6/
		try{
			var axo = new ActiveXObject("ShockwaveFlash.ShockwaveFlash.7");
		}catch(e){
			try {
				var axo = new ActiveXObject("ShockwaveFlash.ShockwaveFlash.6");
				PlayerVersion = new deconcept.PlayerVersion([6,0,21]);
				axo.AllowScriptAccess = "always"; // throws if player version < 6.0.47 (thanks to Michael Williams @ Adobe for this code)
			} catch(e) {
				if (PlayerVersion.major == 6) {
					return PlayerVersion;
				}
			}
			try {
				axo = new ActiveXObject("ShockwaveFlash.ShockwaveFlash");
			} catch(e) {}
		}
		if (axo != null) {
			PlayerVersion = new deconcept.PlayerVersion(axo.GetVariable("$version").split(" ")[1].split(","));
		}
	}
	return PlayerVersion;
}
deconcept.PlayerVersion = function(arrVersion){
	this.major = arrVersion[0] != null ? parseInt(arrVersion[0]) : 0;
	this.minor = arrVersion[1] != null ? parseInt(arrVersion[1]) : 0;
	this.rev = arrVersion[2] != null ? parseInt(arrVersion[2]) : 0;
}
deconcept.PlayerVersion.prototype.versionIsValid = function(fv){
	if(this.major < fv.major) return false;
	if(this.major > fv.major) return true;
	if(this.minor < fv.minor) return false;
	if(this.minor > fv.minor) return true;
	if(this.rev < fv.rev) return false;
	return true;
}
/* ---- get value of query string param ---- */
deconcept.util = {
	getRequestParameter: function(param) {
		var q = document.location.search || document.location.hash;
		if(q) {
			var pairs = q.substring(1).split("&");
			for (var i=0; i < pairs.length; i++) {
				if (pairs[i].substring(0, pairs[i].indexOf("=")) == param) {
					return pairs[i].substring((pairs[i].indexOf("=")+1));
				}
			}
		}
		return "";
	}
}
/* fix for video streaming bug */
deconcept.SWFObjectUtil.cleanupSWFs = function() {
	if (window.opera || !document.all) return;
	var objects = document.getElementsByTagName("OBJECT");
	for (var i=0; i < objects.length; i++) {
		objects[i].style.display = 'none';
		for (var x in objects[i]) {
			if (typeof objects[i][x] == 'function') {
				objects[i][x] = function(){};
			}
		}
	}
}
// fixes bug in fp9 see http://blog.deconcept.com/2006/07/28/swfobject-143-released/
deconcept.SWFObjectUtil.prepUnload = function() {
	__flash_unloadHandler = function(){};
	__flash_savedUnloadHandler = function(){};
	if (typeof window.onunload == 'function') {
		var oldUnload = window.onunload;
		window.onunload = function() {
			deconcept.SWFObjectUtil.cleanupSWFs();
			oldUnload();
		}
	} else {
		window.onunload = deconcept.SWFObjectUtil.cleanupSWFs;
	}
}
if (typeof window.onbeforeunload == 'function') {
	var oldBeforeUnload = window.onbeforeunload;
	window.onbeforeunload = function() {
		deconcept.SWFObjectUtil.prepUnload();
		oldBeforeUnload();
	}
} else {
	window.onbeforeunload = deconcept.SWFObjectUtil.prepUnload;
}
/* add Array.push if needed (ie5) */
if (Array.prototype.push == null) { Array.prototype.push = function(item) { this[this.length] = item; return this.length; }}

/* add some aliases for ease of use/backwards compatibility */
var getQueryParamValue = deconcept.util.getRequestParameter;
var FlashObject = deconcept.SWFObject; // for legacy support
var SWFObject = deconcept.SWFObject;

// TODO: use SWFObject
function generateSlideShow(params, container) {
	var url = "?api_key=" + window.api_key;
	for (var name in params) {
		url += '&' + name + '=' + params[name];
	}
	if (window.api_auth_token) url += "&auth_token=" + api_auth_token;
	if (window.auth_hash) url += "&auth_hash=" + auth_hash;
	
	if(arguments.length == 1) {
		var w = 500;
		var h = 500;
		url = window.urls._("/photos/slideshow/popup") + url;
		window.open(url, "slideShowWin", "width="+w+",height="+h+",top=150,left=70,scrollbars=no, status=no, resizable=no");
	} else {
		var swfdiv = document.getElementById(container);
		if(!swfdiv) {return;}
		
		var bg = "000000";
		var w = "500";
		var h = "500";
		var minW = 762;
		var minH = 350;
		
		url = window.urls._m("/images/slideshow.swf") + url;
		url += "&minH=" + minH;
		url += "&minW=" + minW;
		
		var html = "<OBJECT classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0\" WIDTH=\""+w+"\" HEIGHT=\""+h+"\" id=\"slideShowMovie\" name=\"slideShowMovie\" ALIGN=\"\"><PARAM NAME=movie VALUE=\""+url+"\"><PARAM NAME=quality VALUE=high><PARAM NAME=bgcolor VALUE=#"+bg+"><EMBED src=\""+url+"\" quality=high bgcolor=#"+bg+"  WIDTH=\""+w+"\" HEIGHT=\""+h+"\" id=\"slideShowMovie\" name=\"slideShowMovie\" ALIGN=\"\" TYPE=\"application/x-shockwave-flash\" PLUGINSPAGE=\"http://www.macromedia.com/go/getflashplayer\"></EMBED></OBJECT>";
		
		swfdiv.innerHTML=html;
		
		if (document.slideShowMovie && typeof document.slideShowMovie.focus == "function"){
			document.slideShowMovie.focus();
		}
	}
}

function generateSlideCode(params, htmlContainer, ubbContainer) {
	var url = "?api_key=4a0dfd625c8ad19b1e2105ff44dc962b";
	for (var name in params) {
		url += '&' + name + '=' + params[name];
	}
		
	var htmlObj = document.getElementById(htmlContainer);
	var ubbObj = document.getElementById(ubbContainer);
	if(!htmlObj || !ubbObj) {return;}
	
	var bg = "000000";
	var w = "500";
	var h = "500";
	var minW = 762;
	var minH = 350;
	
	url = window.urls._m("/images/slideshow.swf") + url;
	url += "&minH=" + minH;
	url += "&minW=" + minW;
	
	var html = "<EMBED src=\""+url+"\" quality=\"high\" bgcolor=\"#"+bg+"\" WIDTH=\""+w+"\" HEIGHT=\""+h+"\" id=\"slideShowMovie\" name=\"slideShowMovie\" ALIGN=\"\" TYPE=\"application/x-shockwave-flash\" PLUGINSPAGE=\"http://www.macromedia.com/go/getflashplayer\"></EMBED>";
	var ubb  = "[swf]"+url+"[/swf]";
	
	htmlObj.value = html;
	ubbObj.value = ubb;
}
