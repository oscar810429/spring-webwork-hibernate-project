<#include "/common/macros.ftl">
<@startPage title="Scheduling New Job" 
		heading="Scheduling New Job" />

<content tag="submenu">
	<ul class="pagesnav">
		<li><a href="${urls.getURL('/console/system/jobs')}">Job List</a></li>
		<li><a href="#" class="current">Create Job</a></li>
	</ul>
</content>

<div class="nForm">
	<@ww.form id="job_form" name="job_form" action="jobs_save" namespace="/console/system" method="post">
		<dl>
			<dt>Name:</dt>
			<dd><@ww.textfield id="job_name" name="name" value="name" /></dd>
		</dl>
		<dl>
			<dt>Group:</dt>
			<dd><@ww.textfield id="job_group" name="group" value="group" /></dd>
		</dl>
		<dl>
			<dt>Target:</dt>
			<dd><@ww.textfield id="job_target" name="target" value="target" /></dd>
		</dl>
		<dl>
			<dt>Method:</dt>
			<dd><@ww.textfield id="job_method" name="method" value="method" /></dd>
		</dl>
		<dl>
			<dt>Cron:</dt>
			<dd><@ww.textfield id="job_cron" name="cron" value="cron" /></dd>
		</dl>
		<dl>
			<dt>Delay:</dt>
			<dd><@ww.textfield id="job_delay" name="delay" value="delay" /></dd>
		</dl>
		<dl>
			<dt>Interval:</dt>
			<dd><@ww.textfield id="job_interval" name="interval" value="interval" /></dd>
		</dl>
		<dl>
			<dt>Description:</dt>
			<dd><@ww.textarea id="job_description" name="description" value="description" cols="45" rows="5" /></dd>
		</dl>
		
  		<dl>
			<dt>&nbsp;</dt>
			<dd class="submit">
				<input id="submit" type="submit" value="Submit" class="Btn" />
			</dd>
		</dl>
	</@ww.form>
</div>
