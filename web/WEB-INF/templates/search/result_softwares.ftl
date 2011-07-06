<#include "/common/macros.ftl">
<@startPage title="搜索"/>

<content tag="nav">
<a href="/">首页</a> &gt; <a class="selected" href="＃">开源软件</a>
</content>

<div class="sidebar yui-u first">
        <#list categorys as category>
        <div class="sfBox sfBox-blue">
        <h5 class="facet-list-title">${category.name}</h5>
        <ul limit="" class="facet-list parent">
       <#list category.categories as subcategory>
        <li class="facet">
        <a href="?id=${subcategory.id}">${subcategory.name}</a><span class="facet-count">(0)</span>
        </li>
        </#list>
       </ul>        
        </div>
        </#list>
        
        <#--
        <div class="sfBox sfBox-blue">
            <h5 class="facet-list-title">Platform</h5>
            <ul class="facet-list parent">
                                                                            <li class="facet"><a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;platform%5B%5D=Windows">Windows</a></li>
                                                                                                <li class="facet"><a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;platform%5B%5D=Mac">Mac</a></li>
                                                                                                <li class="facet"><a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;platform%5B%5D=Linux">Linux</a></li>
                                                </ul>
            <h5 class="facet-list-title">Dev Status</h5>
            <ul limit="8" class="facet-list parent">
                                        <li class="facet">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A358">Inactive</a><span class="facet-count">(5,184)</span>
        </li>
                                                    <li class="facet">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A12">Mature</a><span class="facet-count">(3,101)</span>
        </li>
                                                    <li class="facet">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A11">Production/Stable</a><span class="facet-count">(34,146)</span>
        </li>
                                                    <li class="facet">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A10">Beta</a><span class="facet-count">(40,534)</span>
        </li>
                                                    <li class="facet">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A9">Alpha</a><span class="facet-count">(30,058)</span>
        </li>
                                                    <li class="facet">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A8">Pre-Alpha</a><span class="facet-count">(28,470)</span>
        </li>
                                                    <li class="facet">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A7">Planning</a><span class="facet-count">(37,541)</span>
        </li>
            </ul>            <h5 class="facet-list-title">Programming Language</h5>
            <ul limit="6" class="facet-list parent limited">
                                        <li class="facet">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A198">Java</a><span class="facet-count">(42,981)</span>
        </li>
                                                    <li class="facet">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A165">C++</a><span class="facet-count">(34,172)</span>
        </li>
                                                    <li class="facet">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A183">PHP</a><span class="facet-count">(28,210)</span>
        </li>
                                                    <li class="facet">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A164">C</a><span class="facet-count">(26,632)</span>
        </li>
                                                    <li class="facet">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A271">C#</a><span class="facet-count">(12,193)</span>
        </li>
                                                    <li class="facet">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A178">Python</a><span class="facet-count">(12,150)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A280">JavaScript</a><span class="facet-count">(10,241)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A176">Perl</a><span class="facet-count">(8,905)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A185">Unix Shell</a><span class="facet-count">(3,612)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A265">Delphi/Kylix</a><span class="facet-count">(3,348)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A186">Visual Basic</a><span class="facet-count">(3,036)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A453">Visual Basic .NET</a><span class="facet-count">(2,489)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A162">Assembly</a><span class="facet-count">(2,278)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A572">JSP</a><span class="facet-count">(1,885)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A293">Ruby</a><span class="facet-count">(1,728)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A254">PL/SQL</a><span class="facet-count">(1,662)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A174">Objective C</a><span class="facet-count">(1,418)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A589">ASP.NET</a><span class="facet-count">(1,334)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A182">Tcl</a><span class="facet-count">(1,240)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A584">ActionScript</a><span class="facet-count">(1,157)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A560">XSL (XSLT/XPath/XSL-FO)</a><span class="facet-count">(1,044)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A450">Lua</a><span class="facet-count">(890)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A175">Pascal</a><span class="facet-count">(690)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A184">ASP</a><span class="facet-count">(630)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A539">BASIC</a><span class="facet-count">(580)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A258">Object Pascal</a><span class="facet-count">(548)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A626">MATLAB</a><span class="facet-count">(513)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A170">Lisp</a><span class="facet-count">(437)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A169">Fortran</a><span class="facet-count">(433)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A543">Groovy</a><span class="facet-count">(302)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A242">Scheme</a><span class="facet-count">(294)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A548">VBScript</a><span class="facet-count">(266)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A177">Prolog</a><span class="facet-count">(191)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A547">AppleScript</a><span class="facet-count">(187)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A454">OCaml (Objective Caml)</a><span class="facet-count">(185)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A163">Ada</a><span class="facet-count">(182)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A267">Zope</a><span class="facet-count">(168)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A262">Cold Fusion</a><span class="facet-count">(161)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A551">VHDL/Verilog</a><span class="facet-count">(155)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A708">Flex</a><span class="facet-count">(146)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A172">Standard ML</a><span class="facet-count">(146)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A573">S/R</a><span class="facet-count">(146)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A451">Haskell</a><span class="facet-count">(144)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A552">D</a><span class="facet-count">(136)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A553">REALbasic</a><span class="facet-count">(123)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A538">AWK</a><span class="facet-count">(123)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A833">Objective-C 2.0</a><span class="facet-count">(119)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A544">Yacc</a><span class="facet-count">(116)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A699">Free Pascal</a><span class="facet-count">(109)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A540">Common Lisp</a><span class="facet-count">(109)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A598">AspectJ</a><span class="facet-count">(109)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A264">Erlang</a><span class="facet-count">(109)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A181">Smalltalk</a><span class="facet-count">(97)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A698">Lazarus</a><span class="facet-count">(94)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A166">Eiffel</a><span class="facet-count">(88)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A168">Forth</a><span class="facet-count">(86)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A542">Emacs-Lisp</a><span class="facet-count">(81)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A624">IDL</a><span class="facet-count">(78)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A545">LabVIEW</a><span class="facet-count">(70)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A179">Rexx</a><span class="facet-count">(66)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A838">Visual Basic for Applications (VBA)</a><span class="facet-count">(64)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A738">AutoIt</a><span class="facet-count">(61)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A704">Scala</a><span class="facet-count">(54)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A717">Project is a programming language</a><span class="facet-count">(54)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A255">PROGRESS</a><span class="facet-count">(50)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A632">COBOL</a><span class="facet-count">(41)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A263">Euphoria</a><span class="facet-count">(37)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A452">Visual FoxPro</a><span class="facet-count">(37)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A261">XBasic</a><span class="facet-count">(35)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A697">GLSL (OpenGL Shading Language)</a><span class="facet-count">(34)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A608">MUMPS</a><span class="facet-count">(32)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A171">Logo</a><span class="facet-count">(26)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A273">Pike</a><span class="facet-count">(20)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A550">Oberon</a><span class="facet-count">(18)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A549">LPC</a><span class="facet-count">(18)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A281">REBOL</a><span class="facet-count">(18)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A625">Simulink</a><span class="facet-count">(17)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A173">Modula</a><span class="facet-count">(16)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A161">APL</a><span class="facet-count">(16)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A707">Curl</a><span class="facet-count">(16)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A706">XBase/Clipper</a><span class="facet-count">(14)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A716">haXe</a><span class="facet-count">(12)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A839">Boo</a><span class="facet-count">(12)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A709">Mathematica</a><span class="facet-count">(12)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A723">Scilab</a><span class="facet-count">(7)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A541">Dylan</a><span class="facet-count">(6)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A714">Kaya</a><span class="facet-count">(5)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A705">BlitzMax</a><span class="facet-count">(5)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A702">Oz</a><span class="facet-count">(3)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A715">Transcript/Revolution</a><span class="facet-count">(3)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A696">Turing</a><span class="facet-count">(3)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A167">Euler</a><span class="facet-count">(3)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A801">LotusScript</a><span class="facet-count">(2)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A848">ALGOL 68</a><span class="facet-count">(2)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A711">Fenix</a><span class="facet-count">(2)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A724">Scicos</a><span class="facet-count">(1)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A853">Clarion</a><span class="facet-count">(1)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A701">PL/I (Programming Language One)</a><span class="facet-count">(1)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A694">Clean</a><span class="facet-count">(1)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A700">Scriptol</a><span class="facet-count">(1)</span>
        </li>
            <li class="showmore">Show all</li></ul>            <h5 class="facet-list-title">License</h5>
            <ul limit="6" class="facet-list parent limited">
                                        <li class="facet">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A15">GNU General Public License (GPL)</a><span class="facet-count">(106,500)</span>
        </li>
                                                    <li class="facet">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A16">GNU Library or Lesser General Public License (LGPL)</a><span class="facet-count">(18,146)</span>
        </li>
                                                    <li class="facet">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A187">BSD License</a><span class="facet-count">(12,395)</span>
        </li>
                                                    <li class="facet">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A197">Public Domain</a><span class="facet-count">(6,395)</span>
        </li>
                                                    <li class="facet">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A679">GNU General Public License version 3.0 (GPLv3)</a><span class="facet-count">(5,803)</span>
        </li>
                                                    <li class="facet">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A401">Apache License V2.0</a><span class="facet-count">(5,739)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A188">MIT License</a><span class="facet-count">(4,921)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A324">Academic Free License (AFL)</a><span class="facet-count">(3,170)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A196">Other License</a><span class="facet-count">(2,609)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A17">Artistic License</a><span class="facet-count">(2,014)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A305">Mozilla Public License 1.1 (MPL 1.1)</a><span class="facet-count">(1,624)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A307">Common Public License 1.0</a><span class="facet-count">(1,463)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A296">Apache Software License</a><span class="facet-count">(1,410)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A388">Open Software License 3.0 (OSL3.0)</a><span class="facet-count">(1,361)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A406">Eclipse Public License</a><span class="facet-count">(1,057)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A680">GNU Library or "Lesser" General Public License version 3.0 (LGPLv3)</a><span class="facet-count">(984)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A399">PHP License</a><span class="facet-count">(929)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A304">Mozilla Public License 1.0 (MPL)</a><span class="facet-count">(882)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A195">zlib/libpng License</a><span class="facet-count">(769)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A630">Common Development and Distribution License</a><span class="facet-count">(635)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A670">Affero GNU Public License </a><span class="facet-count">(524)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A14">OSI-Approved Open Source</a><span class="facet-count">(163,180)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A189">Python Software Foundation License</a><span class="facet-count">(324)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A629">Educational Community License, Version 2.0</a><span class="facet-count">(318)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A190">Qt Public License (QPL)</a><span class="facet-count">(309)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A318">Sun Public License</a><span class="facet-count">(242)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A391">wxWindows Library Licence</a><span class="facet-count">(168)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A671">Microsoft Public License</a><span class="facet-count">(167)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A194">Python License (CNRI Python License)</a><span class="facet-count">(155)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A191">IBM Public License</a><span class="facet-count">(150)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A628">Adaptive Public License</a><span class="facet-count">(142)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A306">Apple Public Source License</a><span class="facet-count">(125)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A320">W3C License</a><span class="facet-count">(122)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A404">Fair License</a><span class="facet-count">(120)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A677">Artistic License 2.0</a><span class="facet-count">(110)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A678">Boost Software License (BSL1.0)</a><span class="facet-count">(97)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A323">University of Illinois/NCSA Open Source License</a><span class="facet-count">(72)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A639">Common Public Attribution License 1.0 (CPAL)</a><span class="facet-count">(72)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A325">Attribution Assurance License</a><span class="facet-count">(71)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A407">NASA Open Source Agreement</a><span class="facet-count">(68)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A322">Zope Public License</a><span class="facet-count">(59)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A731">GNU General Public License with Classpath exception (Classpath::License)</a><span class="facet-count">(56)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A298">Sun Industry Standards Source License (SISSL)</a><span class="facet-count">(56)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A684">Non-Profit Open Software License 3.0 (Non-Profit OSL 3.0)</a><span class="facet-count">(54)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A396">Reciprocal Public License</a><span class="facet-count">(53)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A681">ISC License</a><span class="facet-count">(53)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A299">Intel Open Source License</a><span class="facet-count">(50)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A300">Jabber Open Source License</a><span class="facet-count">(46)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A843">European Union Public License</a><span class="facet-count">(37)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A303">Nethack General Public License</a><span class="facet-count">(36)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A302">Sleepycat License</a><span class="facet-count">(36)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A393">Historical Permission Notice and Disclaimer</a><span class="facet-count">(35)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A686">Simple Public License 2.0</a><span class="facet-count">(34)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A317">X.Net License</a><span class="facet-count">(32)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A631">Computer Associates Trusted Open Source License 1.1</a><span class="facet-count">(31)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A316">Open Group Test Suite License</a><span class="facet-count">(28)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A672">Microsoft Reciprocal License</a><span class="facet-count">(26)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A392">Eiffel Forum License V2.0</a><span class="facet-count">(25)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A301">Nokia Open Source License</a><span class="facet-count">(23)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A400">Frameworx Open License</a><span class="facet-count">(20)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A319">Eiffel Forum License</a><span class="facet-count">(11)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A403">EU DataGrid Software License</a><span class="facet-count">(11)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A685">Reciprocal Public License 1.5 (RPL1.5)</a><span class="facet-count">(9)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A405">Lucent Public License Version 1.02</a><span class="facet-count">(8)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A297">Vovida Software License 1.0</a><span class="facet-count">(7)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A193">Ricoh Source Code Public License</a><span class="facet-count">(7)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A846">Open Font License 1.1 (OFL 1.1)</a><span class="facet-count">(6)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A397">Entessa Public License</a><span class="facet-count">(6)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A395">RealNetworks Public Source License V1.0</a><span class="facet-count">(6)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A398">Lucent Public License (Plan9)</a><span class="facet-count">(4)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A389">Sybase Open Watcom Public License</a><span class="facet-count">(4)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A192">MITRE Collaborative Virtual Workspace License (CVW License)</a><span class="facet-count">(3)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A402">CUA Office Public License Version 1.0</a><span class="facet-count">(3)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A390">OCLC Research Public License 2.0</a><span class="facet-count">(1)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A683">NTP License</a><span class="facet-count">(1)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A321">Motosoto License</a><span class="facet-count">(1)</span>
        </li>
                                                    <li class="facet" style="display: none;">
        <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A844">IPA Font License</a><span class="facet-count">(1)</span>
        </li>
            <li class="showmore">Show all</li></ul>        </div>
           --> 
</div>


<div class="yui-u">
            
            <#-- <div class="ads">
                <div id="fad81"></div>
                <br style="clear: both;">
            </div>-->
        
        <div style="padding-bottom: 0pt; margin-bottom: 1em; margin-top: -10px;" class="sfBox sfBox-blue feed b-hot">
            <div class="pageselect header yui-gc">
                <div class="yui-u first">
                                            <span class="search_showing">Searching  gives <strong>${result.total}</strong> results</span>
                                    </div>
                <div style="text-align: right;" class="yui-u">
                                            <form action="" method="get">
<input type="hidden" value="soft" name="type_of_search">
<input type="hidden" value="trove:141" name="fq[]">
                                        Sort by:&nbsp;<select onchange="submit()" name="sort">
                        <option value="score">Relevance</option>
                        <option value="num_downloads_week">Downloads</option>
                        <option value="latest_file_date" selected="selected">Latest Release Date</option>
                        <option value="rating">Rating</option>
                    </select><input type="hidden" value="desc" name="sortdir">&nbsp;&nbsp;View:&nbsp;<select onchange="submit()" name="limit" class="limitselect">
                        <option>10</option>
                        <option selected="selected">25</option>
                        <option>50</option>
                        <option>100</option>
                    </select>
                    </form>
                </div>
            </div>
            <div class="sub_header clearfix">
                                            <form action="" method="get">
<input type="hidden" value="soft" name="type_of_search">
<input type="hidden" value="trove:141" name="fq[]">
                                        <input type="text" value="enter keyword" name="words" id="words" style="font-size: 108%; margin-left: 30%; margin-right: 0.5em; height: 18px; width: 30%; float: left;" class="text clear hint blur">
                    <span style="display: block;" class="button button-green">
                        <span>
                            <button value="Search" name="search" type="submit">Search</button>
                        </span>
                    </span>
                </form>
            </div>

            
            <table cellspacing="0" cellpadding="0" border="0" summary="" id="searchtable">
<caption>Search Results</caption>


<!-- Lucene Query:  //-->

    <thead>
            <form action="" method="get" name="search_results"></form>
<input type="hidden" value="soft" name="type_of_search">
<input type="hidden" value="trove:141" name="fq[]">
    </thead>

<!-- 0 --><tfoot>
</tfoot><tbody>
      </tbody>
      <#list result.data as software>
      <tbody <#if (software_index+1)!=result.size>class="dotted"</#if>>
      
       <tr>
       <td class="description">
       <h2><a href="${urls.getURL('/software/view?id='+software.id)}">${software.name}</a>&nbsp;<small>Updated 2010-05-26</small>    </h2>
        ClusterShell is a event-based python library to execute commands on local or distant cluster nodes in parallel depending on the selected engine and worker mechanisms.            </td>
           
      <td class="stats">
       <div class="recommended"><a href="#">Post a review</a></div>
       <div class="downloads">23 weekly downloads</div>
       <a href="/projects/clustershell/files/latest" title="Download this file from SourceForge.net" class="downloadnow"><span>Download Now</span><span class="arrow">↓</span></a>
      </td>
      </tr>
      
    </tbody>
    </#list>
    
    </table>

<div style="margin: 0pt -11px 0pt -14px;" class="pageselect header clearfix yui-gc">
    <div style="text-align: left;" class="yui-u first">
    Showing&nbsp;1&nbsp;&ndash;&nbsp;25&nbsp;of&nbsp;693&nbsp;results</div>
<div style="text-align: right;" class="yui-u">
<#if (result.totalPage > 1)>
    <@pn.pager result="result" url="${urls.getURL('/home')}?page=$PAGE" />
</#if>
    <#--<strong class="pagebox">1</strong> <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A141" class="pagebox">2</a> <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=50&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A141" class="pagebox">3</a>  ... <a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=675&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A141" class="pagebox">28</a> &nbsp;&nbsp;<a href="/softwaremap/?words=&amp;sort=latest_file_date&amp;sortdir=desc&amp;offset=25&amp;type_of_search=soft&amp;fq%5B%5D=trove%3A141">Next →</a> --> 
</div>
</div>



        </div>

            </div>

