%${partner_name}注册成功通知

${user.nickname?html}，你好！
	
恭喜你注册成功，以下是你的信息：

登录名：${user.email?html}
密码：${password?html}

请去快速入门轻松上路：

${media_root}/tour/

你的${partner_name}个人主页：
http://${user.username?html}.${app_domain}/


Ok,咱们${partner_name}上见
	

-${partner_name}团队

${media_root}


(这是一封自动产生的email，请勿回复。)