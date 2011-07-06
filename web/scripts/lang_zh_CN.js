var lang = {
	get: function(key) {
		if (arguments.lenght == 1) {
			return this[key];
		}
		
		var text = this[key];
		if (text) {
			var args = $A(arguments[1]);
			text = text.replace(/\{(\d+)\}/g, function(m, i){
            	return args[i];
        	});
		}
		
		return text;
	},
	
	label_ok:         '确定',
	label_reduction:   '还原',
	label_save:       '保存',
	label_cancel:     '取消',
	label_yes:        '是',
	label_no:         '否', 
	label_delete:     '删除',
	label_or:         '或',
	label_pager:      '分页',
	label_edit:       '编辑',
	label_insert:     '插入',
	label_notice:     '公告',
	
	label_album:      '相册',
	label_group:      '群',
	label_grather:    '第三方应用',
	label_blog:       'blog',
	label_1d1p:       '的黄金周一日一照',
	
	msg_saving:       '正在保存...',
	msg_loading:      '正在加载...',
	msg_error:        '错误',
	msg_error_format: '返回格式错误',
	
	label_create_album: '新建相册',
	label_create_1d1p_album: '新建相册 <label class="hinet">(用户昵称＋“黄金周一日一照”</label>)</label>',
	label_title:        '标题',
	label_description:  '描述',
	
	// editable
	label_click_add:   '点击这里添加',
	label_photo_title: '照片标题',
	label_photo_description: '照片描述',
	
	// notes
	label_add_text_here: '在这里添加文字...',
	msg_deleting_note:   '正在删除标注...',
	msg_saving_note:     '正在保存标注...',
	msg_saving_favorite: '正在保存收藏...',
	msg_removing_favorite: '正在删除收藏...',
	msg_save_recommend: '正在保存推荐...',
	msg_cancel_recommend: '正在取消推荐...',
	
	// context
	label_photo_count:   '张',
	label_view_as:       '查看',
	label_slideshow:     '幻灯片',
	label_photo_stream:        '所有照片',
	label_first_of_context:    '这是第一张照片',
	label_last_of_context:     '这是最后一张照片',
	label_total_photos:  '共{0}张',
	
	// tags
	label_link_globe: '点击这个图标查看所有这个标签的图片',
	label_delete_tag: '删除这个标签?',
	label_click_tag: '点击标签：',
	label_tags_load_more: '加载更多您的标签',
	label_tags_load_all: '加载所有您的标签',
	msg_empty_tags: '请输入要添加的标签',
	msg_tags_delete_confirm: '您确定要删除标签\'{0}\'吗?',
	
	// contacts
	label_contact:    '交往',
	label_friend:     '朋友',
	label_family:     '家人',
	label_tip_contact_change: '<span class="strong">{0}</span>是<span class="strong">{1}</span>, <a id="change_contact" href="javascript:;">修改</a>?',
	label_tip_contact_add:    '<span class="strong">{0}</span>不是你的交往，<a id="add_contact" href="javascript:;">加为交往</a>?',
	label_contact_unblock:  '<span class="strong">{0}</span>已被被你冻结，<label id="add_contact" style="display:none"></label><a href="/contacts/unblock?id={1}">解冻这个人</a>?',
	label_tip_thats_you:      '这不就是<span class="strong">你</span>嘛',
	label_contact_change:     '<span class="strong">{0}</span>目前是你的<span class="strong">{1}</span>。',
	label_contact_add:        '将<span class="strong">{0}</span>加为交往。',
	label_contact_change_hint: '您可以把<span class="strong">{0}</span>更改为交往、好友或者家人。 <br />或者从你的朋友列表中<a id="remove_contact" href="javascript:;">删除</a>他/她.',
	label_contact_add_hint:    '您可以把<span class="strong">{0}</span>加为交往、好友或者家人, （以后也可以修改）。',
	label_contact_remove_confirm: '您确定要把 {0} 从您的交往列表里删除?',
	label_buddy_menu_my_photos:   '照片',
	label_buddy_menu_my_albums:   '相册',
	label_buddy_menu_my_sets:      '专辑',
	label_buddy_menu_my_activity: '我受关注的照片',
	label_buddy_menu_my_comments: '我评论过的照片',
	label_buddy_menu_my_contacts: '交往',
	label_buddy_menu_my_messages: '站内短信',
	label_buddy_menu_my_profile:  '个人档案',
	label_buddy_menu_my_account:  '管理中心',
	label_buddy_menu_my_buddyicon: '修改头像',
	label_buddy_menu_photos: '照片',
	label_buddy_menu_albums: '相册',
	label_buddy_menu_sets:   '专辑',  
	label_buddy_menu_tags:   '标签',
	label_buddy_menu_achives: '存档',
	label_buddy_menu_favorites: '收藏',
	label_buddy_menu_profile: '个人档案',
	label_buddy_menu_contacts: '交往',
	label_buddy_menu_send_testimonial: '发送公开留言',
	label_buddy_menu_send_message: '发送站内短信',
	label_contact_block: '冻结这个人',
	label_contact_block_confirm: '您确定要将此人加入黑名单吗?',
	label_contact_unblock_confirm: '您确定要将此人解除黑名单?',
	
	msg_contact_add_success:      '成功添加交往。',
	msg_contact_edit_success:     '成功更改朋友类型。',
	msg_contact_delete_success:   '成功删除朋友。',
	
	// upload
	label_upload_progress: '上传进度',
	label_processing:      '正在处理照片',
	label_select_album:    '请选择相册',
	label_select_group:    '请选择群',
	label_no_album:        '您目前没有相册',
	label_no_group:        '您没有加入任何群',
	label_loading_albums:  '正在加载您的相册...',
	label_loading_groups:  '正在加载您的群...',
	
	// email config
	label_select_blog:		'请选择Blog',
	label_no_blog:			'您还没有建立关联Blog',
	label_select_blog_category: 	'请选择Blog分类',
	label_select_no_category:		'无法获取Blog分类',
	label_loading_blogs:	'正在加载Blog列表',
	label_loading_blogs_category: 	'正在加载Blog分类',
	
	// lightbox
	label_lightbox_view:    '照片详细信息',
	label_lightbox_comment: '评论',
	label_lightbox_open_in_new_window: '在新窗口中打开',
	label_lightbox_choose_album: '--相册目录--',
	label_lightbox_have_choose: '已选择照片',
	label_light_choose_info: '请从上面的相册列表中选择你想显示的相册,并拖动照片到右侧显示框中',
	label_lightbox_notin_album: '所有不在相册中的照片',
	label_lightbox_next_page: '下一页',
	label_lightbox_prev_page: '上一页',
	label_lightbox_first_page: '首页',
	label_lightbox_last_page: '尾页',
	label_goto_page: '转到',
	label_lightbox_page: '页',
	label_lightbox_allpage: '共',
	label_lightbox_load_photo: '正在读取照片',
	label_lightbox_none_photo: '该相册没有任何照片'
}