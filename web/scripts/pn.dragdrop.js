/**
 * @author Zola Zhou
 * @version $Id: yp.dragdrop.js 2065 2007-08-07 09:09:45Z zola $
 */
var yp = window.yp || new Object();

/*
Class: yp.Drag
	Modify two css properties of an element based on the position of the mouse.

Arguments:
	el - the $(element) to apply the transformations to.
	options - optional. The options object.

Options:
	handle - the $(element) to act as the handle for the draggable element. defaults to the $(element) itself.
	modifiers - an object. see Modifiers Below.
	onStart - optional, function to execute when the user starts to drag (on mousedown);
	onComplete - optional, function to execute when the user completes the drag.
	onDrag - optional, function to execute at every step of the drag
	limit - an object, see Limit below.
	snap - optional, the distance you have to drag before the element starts to respond to the drag. defaults to false

	modifiers:
		x - string, the style you want to modify when the mouse moves in an horizontal direction. defaults to 'left'
		y - string, the style you want to modify when the mouse moves in a vertical direction. defaults to 'top'

	limit:
		x - array with start and end limit relative to modifiers.x
		y - array with start and end limit relative to modifiers.y
*/
yp.Drag = new Class({
	options: {
		handle: false,
		unit: 'px',
		onStart: Class.empty,
		onBeforeStart: Class.empty,
		onComplete: Class.empty,
		onSnap: Class.empty,
		onDrag: Class.empty,
		limit: false,
		modifiers: {x: 'left', y: 'top'},
		snap: 6
	},

	initialize: function(el, options){
		this.setOptions(options);
		this.element = $(el);
		this.handle = $(this.options.handle) || this.element;
		this.mouse = {'now': {}, 'pos': {}};
		this.value = {'start': {}, 'now': {}};
		this.bound = {'down': this.down.bindWithEvent(this)};
		this.attach();
		if (this.options.initialize) this.options.initialize.call(this);
	},
	
	attach: function(){
		this.handle.addEvent('mousedown', this.bound.down);
	},
	
	down: function(event) {
		this.mouse.down = event.page;
		this.bound.drag = this.drag.bindWithEvent(this);
		this.bound.stop = this.stop.bind(this);
		this.bound.move = this.options.snap ? this.checkAndDrag.bindWithEvent(this) : this.bound.drag;
		document.addEvent('mousemove', this.bound.move);
		document.addEvent('mouseup', this.bound.stop);
		event.stop();
	},

	start: function(event){
		this.fireEvent('onBeforeStart', this.element);
		this.prepare();
		this.mouse.start = event.page;
		var limit = this.options.limit;
		this.limit = {'x': [], 'y': []};
		for (var z in this.options.modifiers){
			this.value.now[z] = this.element.getStyle(this.options.modifiers[z]).toInt();
			this.mouse.pos[z] = event.page[z] - this.value.now[z];
			if (limit && limit[z]){
				for (var i = 0; i < 2; i++){
					if ($chk(limit[z][i])) this.limit[z][i] = limit[z][i].apply ? limit[z][i].call(this) : limit[z][i];
				}
			}
		}
		this.dragging = true;
		this.fireEvent('onStart', this.element);
		event.stop();
	},
	
	prepare: function() {
		// empty hook
	},

	checkAndDrag: function(event){
		var distance = Math.round(Math.sqrt(Math.pow(event.page.x - this.mouse.down.x, 2) + Math.pow(event.page.y - this.mouse.down.y, 2)));
		if (distance > this.options.snap){
			document.removeEvent('mousemove', this.bound.move);
			this.bound.move = this.bound.drag;
			document.addEvent('mousemove', this.bound.move);
			this.drag(event);
			this.fireEvent('onSnap', this.element);
		}
		event.stop();
	},

	drag: function(event){
		if (!this.dragging) this.start(event);
		this.out = false;
		this.mouse.now = event.page;
		for (var z in this.options.modifiers){
			this.value.now[z] = this.mouse.now[z] - this.mouse.pos[z];
			if (this.limit[z]){
				if ($chk(this.limit[z][1]) && (this.value.now[z] > this.limit[z][1])){
					this.value.now[z] = this.limit[z][1];
					this.out = true;
				} else if ($chk(this.limit[z][0]) && (this.value.now[z] < this.limit[z][0])){
					this.value.now[z] = this.limit[z][0];
					this.out = true;
				}
			}
			this.element.setStyle(this.options.modifiers[z], this.value.now[z] + this.options.unit);
		}
		this.fireEvent('onDrag', this.element);
		event.stop();
	},
	
	detach: function(){
		this.handle.removeEvent('mousedown', this.bound.start);
	},

	stop: function(){
		document.removeEvent('mousemove', this.bound.move);
		document.removeEvent('mouseup', this.bound.stop);
		if (this.dragging) {
			this.fireEvent('onComplete', this.element);
			this.dragging = false;
		}
	}

});

yp.Drag.implement(new Events);
yp.Drag.implement(new Options);

Element.extend({

	/*
	Property: makeResizable
		Makes an element resizable (by dragging) with the supplied options.

	Arguments:
		options - see <yp.Drag> for acceptable options.
	*/

	makeResizable: function(options){
		return new yp.Drag(this, Object.extend(options || {}, {modifiers: {x: 'width', y: 'height'}}));
	}

});

/*
Class: yp.DragDrop
	Extends <yp.Drag>, has additional functionality for dragging an element, support snapping and droppables.
	DragDrop supports either position absolute or relative. If no position is found, absolute will be set.

Arguments:
	el - the $(element) to apply the drag to.
	options - optional. see Options below.

Options:
	all the drag.Base options, plus:
	container - an element, will fill automatically limiting options based on the $(element) size and position. defaults to false (no limiting)
	droppables - an array of elements you can drop your draggable to.
*/
yp.DragDrop = yp.Drag.extend({
    getExtended: function() {
		return {
            zIndex: false,
            ghost: false,
			droppables: [],
			container: false,
			overflown: [],
			onDragStart: function(drag, element, ghost){
				ghost.setStyle('opacity', 0.5);
			},
			onDragComplete: Class.empty
		}
	},

	initialize: function(el, options) {
		this.setOptions(this.getExtended(), options);
		this.element = $(el);
		this.droppables = $$(this.options.droppables);
		this.position = this.element.getStyle('position');
		if (!['absolute', 'relative'].test(this.position)) this.position = 'absolute';
		this.parent(this.element, this.options);
	},

	down: function(event) {
		this.container = $(this.options.container);
		if (this.container) {
			var cont = this.container.getCoordinates();
			var el = this.element.getCoordinates();
			if (this.position == 'absolute'){
				this.options.limit = {
					'x': [cont.left, cont.right - el.width],
					'y': [cont.top, cont.bottom - el.height]
				};
			} else {
				var diffx = el.left - this.element.getStyle('left').toInt();
				var diffy = el.top - this.element.getStyle('top').toInt();
				this.options.limit = {
					'y': [-(diffy) + cont.top, cont.bottom - diffy - el.height],
					'x': [-(diffx) + cont.left, cont.right - diffx - el.width]
				};
			}
		}
		this.parent(event);
	},
	
	prepare: function() {
        if (this.options.ghost) {
            if (!this.ghost) {
                this.ghost = this.element.clone();
                if (this.element.getProperty('id')) {
                	this.ghost.setProperty('id', this.element.getProperty('id') + '_ghosting');
                }
                this.addEvent('onComplete', function(element) {
                    this.ghost.remove();
                    this.element = this._element;
                    this._element = null;
                    this.fireEvent('onDragComplete', [this, this.element, this.ghost]);
                }.bind(this));
            }
            var coord = this.element.getCoordinates();
            this._element = this.element;
            this.element = this.ghost.setStyles({
                'position': 'absolute',
                'top': coord.top+'px',
                'left': coord.left+'px',
                'width': coord.width+'px',
                'height': coord.height+'px'
            }).injectInside(document.body);
            this.fireEvent('onDragStart', [this, this._element, this.ghost]);
        } else {
            var top = this.element.getStyle('top').toInt();
    		var left = this.element.getStyle('left').toInt();
            this.oldStyles = { top: top, left: left, position: this.element.getStyle('position') };
            if (this.position == 'absolute'){
    			top = $chk(top) ? top : this.element.getTop();
    			left = $chk(left) ? left : this.element.getLeft();
    		} else {
    			top = $chk(top) ? top : 0;
    			left = $chk(left) ? left : 0;
    		}
    		this.element.setStyles({
    			'top': top+'px',
    			'left': left+'px',
    			'position': this.position
    		});
    		this.addEvent('onComplete', function(element){
                this.element.setStyles(this.oldStyles);
    		}.bind(this));
		}
		if (this.options.zIndex) {
    		this.element.setStyle('zIndex', this.options.zIndex);
		}
	},

	drag: function(event) {
		this.parent(event);
		if (this.out) return this;
		this.droppables.each(function(drop){
			if (this.checkAgainst($(drop))){
				if (!drop.overing) drop.fireEvent('over', [this.element, this]);
				drop.overing = true;
			} else {
				if (drop.overing) drop.fireEvent('leave', [this.element, this]);
				drop.overing = false;
			}
		}, this);
		return this;
	},

	checkAgainst: function(el) {
		el = el.getCoordinates(this.options.overflown);
		return (this.mouse.now.x && this.mouse.now.y && this.mouse.now.x > el.left && this.mouse.now.x < el.right && this.mouse.now.y < el.bottom && this.mouse.now.y > el.top);
	},

	stop: function() {
		this.parent();
		this.timer = $clear(this.timer);
		if (this.out) return this;
		var dropped = false;
		this.droppables.each(function(drop){
			if (this.checkAgainst(drop)){
				drop.fireEvent('drop', [this.element, this]);
				dropped = true;
			}
		}, this);
		if (!dropped) this.element.fireEvent('drop', this);
		return this;
	}
});

Element.extend({

	/*
	Property: makeDraggable
		Makes an element draggable with the supplied options.

	Arguments:
		options - see <yp.DragDrop> and <yp.Drag> for acceptable options.
	*/

	makeDraggable: function(options){
		return new yp.DragDrop(this, options);
	}

});