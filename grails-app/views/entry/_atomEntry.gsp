<entry xmlns='http://www.w3.org/2005/Atom'>
  <author>
    <name>${entryInstance.author.name}</name>
  </author>
  <published><g:longDate>${entryInstance.dateCreated}</g:longDate></published>
  <updated><g:longDate>${entryInstance.lastUpdated}</g:longDate></updated>
  <link href="http://localhost:8080/blogito/blog/${entryInstance.author.login}/${entryInstance.title.encodeAsUnderscore()}" 
        rel="alternate" 
        title="${entryInstance.title}" 
        type="text/html" />
  <id>tag:blogito.org,2012:/blog/${entryInstance.author.login}/${entryInstance.title.encodeAsUnderscore()}</id>
  <title type="text">${entryInstance.title}</title>
  <content type="xhtml">
    <div xmlns="http://www.w3.org/1999/xhtml">
      ${entryInstance.summary}
    </div>
  </content>
</entry>