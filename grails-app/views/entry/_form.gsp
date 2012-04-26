<%@ page import="blogito.Entry" %>



<div class="fieldcontain ${hasErrors(bean: entryInstance, field: 'title', 'error')} ">
	<label for="title">
		<g:message code="entry.title.label" default="Title" />
		
	</label>
	<g:textField name="title" value="${entryInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: entryInstance, field: 'summary', 'error')} ">
	<label for="summary">
		<g:message code="entry.summary.label" default="Summary" />
		
	</label>
	<g:textArea name="summary" cols="40" rows="5" maxlength="1000" value="${entryInstance?.summary}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: entryInstance, field: 'payload', 'error')} ">
	<label for="payload">
		<g:message code="entry.payload.label" default="File" />
		
	</label>
	<input type="file" id="payload" name="payload"/>
</div>
