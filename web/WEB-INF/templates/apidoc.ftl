<html>
<head>
  <title>${api}</title>
  <style>
  body {margin:0;padding:0;}
  iframe {margin:0;width:100%;}
  #apidoc_top {background:#E55000;color:#fff;border-bottom:2px solid #ccc;font-size:10.5pt;padding:2px 2px;}
  #apidoc_top a {color:#fff;text-decoration:none;}
  #apidoc_top a:hover {color:#ff0;}
  #apidoc_top span {float:right;font-family:"Lucida Grande",Calibri,Arial,sans-serif;color:#DDD;}
  #apidoc_top span a {padding-right:5px;color:#FCC;}
  </style>
</head>
<body>
  <div id="apidoc_top">
  <span>
    热门：
    <a href="?api=android_1_5_r2">Android文档</a>

    <a href="?api=javase-6-doc-api-zh_CN">JDK 1.6 中文版</a>
    <a href="?api=j2se_1.6%2Fapi">JDK 1.6 英文版</a>
    <a href="?api=j2ee_5.0.03">J2EE</a>
    <a href="?api=struts-2.1.2">Struts</a>
    <a href="?api=spring-framework-2.5.5">Spring</a>
    <a href="?api=hibernate-core-3.3.1.GA">Hibernate</a>

    <a href="?api=commons-dbutils-1.1">DbUtils</a>
    <a href="?api=lucene-2.4.0">Lucene</a>
    <a href="?api=commons-httpclient-3.1">HttpClient</a>
    <a href="?api=jfreechart-1.0.10">jFreeChart</a>
    <a href="?api=jquery">jQuery</a>
  </span>

  <a href="/">派牛网开源社区</a>
  </div>
  <table width='100%'><tr><td>
  <iframe id='iframe_doc' frameborder='0' src='/apidoc/${api}/index.html' onload='this.height=document.body.clientHeight-30;'></iframe>
  </td></tr></table>
</body>
</HTML>
