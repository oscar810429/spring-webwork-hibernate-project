<div id="dialog_create_album" class="create-album">
    <div class="x-dialog-wrap">
        <div class="x-dialog-head clearfix">
            <h5>创建新相册</h5>
        </div>
        <div class="x-dialog-body">
            <div class="x-dialog-body-content clearfix">
                <form id="album_create_form" name="album_create_form" action="/ajax/albums/create/?dialog_id=StickyWin_1269794343904&callback=append_album" method="post" class="x-form">
                    <fieldset>
                        <ol>
                            <li>
                                <label for="album_title">标题:</label>
                                <input id="album_title" name="title" value="" class="txt" style="width: 240px;" />
                            </li>
                            <li>
                                <label for="album_description">描述:</label>
                                <textarea id="album_description" name="description" rows="5" style="width: 240px;"></textarea>
                            </li>
                        </ol>
                    </fieldset>
                </form>
            </div>
        </div>
        <div class="x-dialog-foot clearfix">
            <div class="buttons">
                <input type="button" class="submit btn" value="创建" />
                <input type="button" class="cancel btn-cancel" value="取消" />
            </div>
        </div>
    </div>
</div>


<script type="text/javascript">
// <![CDATA[
(function() {
    var dialog = StickyWin.instances.get('${dialog_id}');
    var callback = '${callback}';
    if (dialog) {
        var el = dialog.win;

        var btnCancel = el.getElement('.cancel');

        btnCancel.addEvent('click', function() {
            dialog.hide();
            leopard.mask.hide();
        });

        dialog.addEvent('close', function() {dialog.destroy()});

        var btnSubmit  = el.getElement('.submit');
        var inputTitle = el.getElement('#album_title');
        var inputDesc  = el.getElement('#album_description');
        var form       = el.getElement('#album_create_form');

        try {
            inputTitle.focus(); // not work, don't known why
        } catch(e) {}

        function finish() {
            leopard.spinner.hide();
            leopard.mask.hide();
        }

        function submitForm() {
            var title = inputTitle.get('value');
            var desc  = inputDesc.get('value');
            if (title.trim() == '') {
                alert('请输入相册标题');
                return;
            }

            dialog.hide();

            leopard.spinner.wait('正在发送请求...');

            $api('mingda.albums.create').post({
                'title': title,
                'description': desc
            }).addEvents({
                'success': function(rsp) { 
                    if (callback && window[callback]) {
                        finish();
                        window[callback](rsp.album);
                    } else {
                        leopard.spinner.success('成功创建相册').addEvent('ok', function(){finish();});
                    }
                },
                'error': function(rsp) { 
                    leopard.spinner.error('错误: ' + rsp.err.msg).addEvent('ok', function(){finish();});
                }
            });
        }

        btnSubmit.addEvent('click', function() {
            submitForm();
        });
        form.addEvent('submit', function() {
            submitForm();
            return false;
        });
    }
})();
// ]]>
</script>
