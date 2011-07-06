%你的朋友${invite.from.nickname?html}邀请你加入${partner_name}
<#if invite.name?exists>${invite.name?html}<#else>Hi</#if>，你好!

${invite.from.nickname?html} 想要跟你分享他/她最近拍的照片。（也可以自填信息）

${invite.message?html}

接受邀请点击以下链接加入我们：

${media_root}/invites/response?id=${invite.id}


如果你的email程序不支持链接点击，请拷贝上面链接地址至浏览器(如IE)地址栏就可去Yupoo了。


Ok，咱们${partner_name}上见!


-${partner_name}团队

${media_root}



(这是一封自动产生的email，请勿回复。)