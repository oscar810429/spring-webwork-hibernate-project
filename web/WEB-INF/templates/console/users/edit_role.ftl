<#include "/common/macros.ftl">
<@startPage title="Edit user role" heading="Edit roles for user: ${user.nickname?html}" />

<head>
	<script type="text/javascript" src="${media_root}/scripts/pn.dragdrop.js?v20070206.js"></script>
	<style type="text/css" media="screen">
		#roleForm {text-align:right;}
		.green {border:1px solid #C5DEA1;background-color: #ECF3E1;}
		.orange {border:1px solid #E8A400;background-color: #FFF4D8;}
		.over {background-color: #f5f5f5;}
		#roleEditor { width: 500px; padding-top: 1em; }
		#roleEditor h3 { margin-bottom: 0.5em; }
		.sortable { width: 200px; height: 170px; padding: 2px; }
		.sortable div { margin-bottom: 2px; cursor:move; }
		#selected_list { margin-left: 70px; }
		.selected { width: 280px; }
		.available { width: 200px; }
		.selected, .available { float: left; margin-right: 10px; }
		
		#bandwidthEditor {
			margin-top: 3em;
			padding: 2em;
			background-color: #f5f5f5;
		}
		#userTypeEditor {
			margin-top: 3em;
			padding: 2em;
			background-color: #f5f5f5;
		}
	</style>
</head>

<div id="roleEditor">
	<div class="selected">
		<h3>Roles of ${user.nickname?html}</h3>
		<a href="<@userHome user=user/>" rel="contact_${user.id}"><img class="BuddyIcon" src="<@userIcon user=user/>" alt="${user.nickname?html}" width="48" height="48" /></a>
		<div class="sortable" id="selected_list">
			<#list user.roles as r>
			<div class="role green" id="role_${r.name}">${r.name?cap_first}</div>
			</#list>
		</div>
	</div>
	
	<div class="available">
		<h3>Available roles</h3>
		<div class="sortable" id="available_list">
			<#list roles as r>
			<div class="role orange" id="role_${r.name}">${r.name?cap_first}</div>
			</#list>
		</div>
	</div>
	
	<div class="clear"></div>
	
	<form id="roleForm" name="roleForm" action="${urls.getURL('/console/users/save_role')}" method="post" onsubmit="return saveRole();">
		<input type="hidden" name="person" value="${user.id}" />
		<input type="hidden" id="hidden_role" name="role" value="" />
		<input type="submit" value="SAVE" class="Btn"/>		
	</form>
</div>

<div id="userTypeEditor" class="nForm">
	<h1>Set User Types</h1>

	<form id="userTypeForm" name="userTypeForm" action="${urls.getURL('/console/users/save_type')}" method="post">
		<input type="hidden" name="person" value="${user.id}" />
		<dl>
		
			<dt>Types:</dt>
			<dd>
				<input type="hidden" name="id" value="${user.id}" />
				<input type="radio" value="0" name="type" id="type_free" <#--<#if user.userType.value()==0>checked="checked"</#if>--> /><label for="type_free">free</label>
			</dd>
			
		</dl>

  		<dl>
			<dt>&nbsp;</dt>
			<dd class="submit">
				<input type="submit" value="Save" class="Btn" />
			</dd>
		</dl>
	</form>
</div>


<div id="userTypeEditor" class="nForm">
	<h1>Delete User buddyicon</h1>

	<form id="deleteUserBuddyiconForm" name="deleteUserBuddyiconForm" action="${urls.getURL('/console/users/delete_buddyicon')}" method="post">
		<input type="hidden" name="person" value="${user.id}" />
  		<dl>
			<dt>&nbsp;</dt>
			<dd class="submit">
				<input type="submit" value="Delete buddyicon" class="Btn" />
			</dd>
		</dl>
	</form>
</div>

<@script>
var RoleSelector = {
	init: function() {
		this.dragOptions = {
			snap: 3,
			ghost: true,
			zIndex: 1000,
			droppables: '.sortable'
		};
		
		$$('.role').each(function(el) {
			el.makeDraggable(this.dragOptions);
		}, this);
		
		$$('.sortable').each(function(el) {
			el.addEvent("over", function(){this.addClass('over')});
			el.addEvent("leave", function(){this.removeClass('over')});
			el.addEvent("drop", function(el,drag){
				this.removeClass('over');
				if(el.getParent() != this) this.adopt(el.remove());
			});
		});
	}
};

RoleSelector.init();

function saveRole() {
	var selected = [];
	$('selected_list').getChildren().each(function(el) {
		selected.push(el.id.substring(5));
	});
	if (selected.length == 0) {
		alert("You must at least select one role.");
		return false;
	}
	$("hidden_role").value = selected.join(",");
	return true;
} 
</@script>