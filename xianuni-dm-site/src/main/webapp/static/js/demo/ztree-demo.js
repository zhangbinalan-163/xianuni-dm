/**
 * Created by Vijay on 15/11/15.
 */
$(function () {
    /* global settings */
    $.jgrid.defaults.styleUI = 'Bootstrap';

    var table4 = ({
        init: function () {
            this.$el = $("#list4");
            this.render();
            this.bindEvents();
            return this
        },
        fetchData: function (data) {
            this.$el.jqGrid("setGridParam", {
                postData: data,
                page: 1
            }).trigger("reloadGrid");
        },
        bindEvents: function () {
            var self = this;
            $('#searchBtn4').on('click', function () {
                self.fetchData({
                    name: $('#searchName4').val().trim(),
                    age: $('#searchAge4').val().trim()
                })
            })
        },
        render: function () {
            this.$el.jqGrid(
                {
                    url: '../static/data/jqgrid1.json',
                    datatype: "json",
                    colNames: ['No', 'Date', 'Client', 'Amount', 'Tax', 'Total', 'Notes'],
                    colModel: [
                        {name: 'id', index: 'id', width: 55},
                        {name: 'invdate', index: 'invdate', width: 90},
                        {name: 'name', index: 'name asc, invdate', width: 100},
                        {name: 'amount', index: 'amount', width: 80, align: "right"},
                        {name: 'tax', index: 'tax', width: 80, align: "right"},
                        {name: 'total', index: 'total', width: 80, align: "right"},
                        {name: 'note', index: 'note', width: 150, sortable: false}
                    ],
                    rowNum: 10,
                    rowList: [10, 20, 30],
                    pager: '#pager4',
                    sortname: 'id',
                    mtype: "get",
                    height: 300,
                    viewrecords: true,
                    sortorder: "desc",
                    caption: "JSON 实例"
                })
                .jqGrid('navGrid', '#pager4', {edit: false, add: false, del: false});
        }
    }).init();

    var tree4 = ({
        init: function () {
            $.fn.zTree.init($("#treeDemo4"), this.config());
        },
        config: function () {
            var self = this;
            return this.setting = {
                data: {
                    simpleData: {
                        enable: true,
                        idKey: "id",
                        pIdKey: "pId",
                        rootPId: 0
                    }
                },
                async: {
                    enable: true,
                    url: "../static/data/treeAll2.json",
                    dataFilter: self.filter,
                    type: "get",
                    autoParam: ["id", "name", "level"]
                },
                view: {
                    showIcon: false
                },
                callback: {
                    onClick: self.updateTable
                }
            }
        },
        updateTable: function (event, treeId, treeNode) {
            //if (!treeNode.isParent) {
            table4.fetchData({
                catId: treeNode.id
            })
            //}
        },
        filter: function (treeId, parentNode, childNodes) {
            if (!childNodes) return null;
            for (var i = 0, l = childNodes.length; i < l; i++) {
                childNodes[i].name = 'Test>' + childNodes[i].name;
            }
            return childNodes;
        }

    }).init();


    /* 基本 示例*/
    var tree1 = (function () {
        var setting = {
            view: {
                showIcon: false,
                selectedMulti: false
            },
            data: {
                simpleData: {
                    enable: true
                }
            }
        };

        var zNodes = [
            {id: 1, pId: 0, name: "父节点1", open: true},
            {id: 11, pId: 1, name: "父节点11"},
            {id: 111, pId: 11, name: "叶子节点111"},
            {id: 112, pId: 11, name: "叶子节点112"},
            {id: 12, pId: 1, name: "父节点12"},
            {id: 121, pId: 12, name: "叶子节点121"},
            {id: 122, pId: 12, name: "叶子节点122"},
            {id: 13, pId: 1, name: "父节点13", isParent: true},
            {id: 2, pId: 0, name: "父节点2"},
            {id: 21, pId: 2, name: "父节点21", open: true},
            {id: 211, pId: 21, name: "叶子节点211"},
            {id: 212, pId: 21, name: "叶子节点212"},
            {id: 22, pId: 2, name: "父节点22"},
            {id: 221, pId: 22, name: "叶子节点221"},
            {id: 222, pId: 22, name: "叶子节点222"},
            {id: 23, pId: 2, name: "父节点23"},
            {id: 231, pId: 23, name: "叶子节点231"},
            {id: 232, pId: 23, name: "叶子节点232"},
            {id: 3, pId: 0, name: "父节点3", isParent: true}
        ];

        $.fn.zTree.init($("#treeDemo1"), setting, zNodes);
    })();

    /* 异步加载全部树结构 */
    var table2 = ({
        init: function () {
            this.$el = $("#list2");
            this.render();
            this.bindEvents();
            return this
        },
        fetchData: function (data) {
            this.$el.jqGrid("setGridParam", {
                postData: data,
                page: 1
            }).trigger("reloadGrid");
        },
        bindEvents: function () {
            var self = this;
            // 搜索
            $('#searchBtn').on('click', function () {
                self.fetchData({
                    name: $('#searchName').val().trim(),
                    age: $('#searchAge').val().trim()
                })
            });
            // 新增 todo
            $('#addBtn').on('click', function () {
                // 选中的节点
                var node = tree2.zTree && tree2.zTree.getSelectedNodes()[0];
                if(!node){
                    layer.alert('请选择一个节点', {icon: 6});
                    return
                }
                layer.open({
                    type: 1, //page层
                    area: ['500px', '300px'],
                    title: '在节点<strong>' + node.name +'</strong>下创建新记录',
                    shade: 0.6, //遮罩透明度
                    shift: 5, //0-6的动画形式，-1不开启
                    btn: ['创建', '取消'],
                    content: '<form class="form-horizontal" style="padding: 30px">' +
                        '<div class="form-group">' +
                        '<label class="control-label col-sm-2">名称</label>' +
                        '<div class="col-sm-10"><input type="text" id="p_name" class="form-control"/></div>' +
                        '</div>' +
                        '</form>',
                    yes: function(index){
                        // todo 读取表单信息 调用创建接口
                        var name = $('#p_name').val().trim();
                        // todo 创建成功后 更新树 或者单独更新或加入节点
                        // tree2.refresh();
                        if(!name){
                            return
                        }
                        layer.alert('创建成功>' + name, {icon: 1});
                        layer.close(index);
                    },
                    no: function(index){
                        layer.close(index);
                    }
                });
            });
        },
        render: function () {

            this.$el.jqGrid(
                {
                    url: '../static/data/jqgrid1.json',
                    datatype: "json",
                    colNames: ['No', 'Date', 'Client', 'Amount', 'Tax', 'Total', 'Notes'],
                    colModel: [
                        {name: 'id', index: 'id', width: 55},
                        {name: 'invdate', index: 'invdate', width: 90},
                        {name: 'name', index: 'name asc, invdate', width: 100},
                        {name: 'amount', index: 'amount', width: 80, align: "right"},
                        {name: 'tax', index: 'tax', width: 80, align: "right"},
                        {name: 'total', index: 'total', width: 80, align: "right"},
                        {name: 'note', index: 'note', width: 150, sortable: false}
                    ],
                    rowNum: 10,
                    rowList: [10, 20, 30],
                    pager: '#pager2',
                    sortname: 'id',
                    mtype: "get",
                    height: 300,
                    viewrecords: true,
                    sortorder: "desc",
                    caption: "JSON 实例"
                })
                .jqGrid('navGrid', '#pager2', {edit: false, add: false, del: false});
        }
    }).init();
    var tree2 = ({
        init: function () {
            this.rMenu = $("#rMenu");
            $.fn.zTree.init($("#treeDemo2"), this.config());
            this.zTree = $.fn.zTree.getZTreeObj("treeDemo2");
            this.bindMenuEvents();
            return this;
        },
        config: function () {
            var self = this;
            return self.setting = {
                data: {
                    simpleData: {
                        enable: true,
                        idKey: "id",
                        pIdKey: "pId",
                        rootPId: 0
                    }
                },
                async: {
                    enable: true,
                    url: "../static/data/treeAll.json",
                    dataFilter: self.filter,
                    type: "get"
                },
                view: {
                    showIcon: false
                },
                callback: {
                    onClick: self.updateTable,
                    onRightClick: self.onRightClick.bind(self),
                    beforeRename: self.beforeRename.bind(self)
                }
            }
        },
        onRightClick: function (event, treeId, treeNode) {
            if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
                this.zTree.cancelSelectedNode();
                this.showRMenu("root", event.clientX, event.clientY);
            } else if (treeNode && !treeNode.noMenu) {
                this.zTree.selectNode(treeNode);
                this.showRMenu("node", event.clientX, event.clientY);
            }
        },
        showRMenu: function (type, x, y) {
            var self = this;
            if (type == "root") {
                self.rMenu.children(".m_del").hide();
            } else {
                self.rMenu.children(".m_del").show();
            }
            self.rMenu.css({"top": (window.scrollY + y) + "px", "left": x + "px", "visibility": "visible"})
                .show();

            $("body").bind("mousedown", self.onBodyMouseDown.bind(this));
        },
        hideRMenu: function () {
            var self = this;
            if (self.rMenu) self.rMenu.css({"visibility": "hidden"});
            $("body").unbind("mousedown", self.onBodyMouseDown.bind(this));
        },
        onBodyMouseDown: function (event) {
            if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length > 0)) {
                this.rMenu.css({"visibility": "hidden"});
            }
        },
        bindMenuEvents: function () {
            var self = this;
            var zTree = self.zTree;
            self.rMenu.on('click', '.m_add', function () {
                self.hideRMenu();
                // todo 请求服务端
                var newNode = { name: "newNode 1"};
                if (zTree.getSelectedNodes()[0]) {
                    zTree.addNodes(zTree.getSelectedNodes()[0], newNode);
                } else {
                    zTree.addNodes(null, newNode);
                }
            });
            self.rMenu.on('click', '.m_del', function () {
                self.hideRMenu();
                var nodes = zTree.getSelectedNodes();
                var removeNode = function () {
                    // todo 请求服务端
                    zTree.removeNode(nodes[0]);
                };
                if (nodes && nodes.length > 0) {
                    if (nodes[0].children && nodes[0].children.length > 0) {
                        var msg = "将删除包含的所有分组？";
                        if (confirm(msg) == true) {
                            removeNode();
                        }
                    } else {
                        removeNode();
                    }
                }
            });
            self.rMenu.on('click', '.m_rename', function () {
                self.hideRMenu();
                var nodes = zTree.getSelectedNodes();
                if (nodes && nodes.length > 0) {
                    zTree.editName(nodes[0]);
                }
            })
        },
        beforeRename: function (treeId, treeNode, newName, isCancel) {
            // todo 请求服务端接口
            if (!isCancel && treeNode.name != newName) {
                alert(treeNode.name + '修改为' + newName);
            }
        },
        updateTable: function (event, treeId, treeNode) {
            //if (!treeNode.isParent) {
            table2.fetchData({
                catId: treeNode.id
            })
            //}
        },
        filter: function (treeId, parentNode, childNodes) {
            if (!childNodes) return null;
            for (var i = 0, l = childNodes.length; i < l; i++) {
                childNodes[i].name = 'Test>' + childNodes[i].name;
                childNodes[i].open = true;
            }
            return childNodes;
        }

    }).init();

    /* 异步分次加载数据 */
    var table3 = ({
        init: function () {
            this.render();
            return this
        },
        render: function () {

        }
    }).init();
    var tree3 = ({
        init: function () {
            this.perCount = 100;
            this.perTime = 100;
            var zNodes = [
                {name: "500个节点", id: "1", count: 500, times: 1, isParent: true},
                {name: "1000个节点", id: "2", count: 1000, times: 1, isParent: true},
                {name: "2000个节点", id: "3", count: 2000, times: 1, isParent: true}
            ];
            $.fn.zTree.init($("#treeDemo3"), this.config(), zNodes);
        },
        config: function () {
            var self = this;
            return this.setting = {
                async: {
                    enable: true,
                    url: self.getUrl
                },
                check: {
                    enable: true
                },
                data: {
                    simpleData: {
                        enable: true
                    }
                },
                view: {
                    expandSpeed: "",
                    showIcon: false
                },
                callback: {
                    beforeExpand: self.beforeExpand.bind(this),
                    onAsyncSuccess: self.onAsyncSuccess.bind(this),
                    onAsyncError: self.onAsyncError.bind(this)
                }
            };
        },
        getTreeObj: function () {
            return $.fn.zTree.getZTreeObj("treeDemo3")
        },
        getUrl: function (treeId, treeNode) {
            var curCount = (treeNode.children) ? treeNode.children.length : 0;
            var getCount = (curCount + this.perCount) > treeNode.count ? (treeNode.count - curCount) : this.perCount;
            var param = "id=" + treeNode.id + "_" + (treeNode.times++) + "&count=" + getCount;
            return "data/treeAll.json?" + param;
        },
        fetchNodes: function (treeNode, reloadType) {
            var zTree = this.getTreeObj();
            if (reloadType == "refresh") {
                treeNode.icon = "/css/plugins/zTree/zTreeStyle/img/loading.gif";
                zTree.updateNode(treeNode);
            }
            zTree.reAsyncChildNodes(treeNode, reloadType, true);
        },
        beforeExpand: function (treeId, treeNode) {
            var self = this;
            if (!treeNode.isAjaxing) {
                treeNode.times = 1;
                self.fetchNodes(treeNode, "refresh");
                return true;
            } else {
                alert("zTree 正在下载数据中，请稍后展开节点。。。");
                return false;
            }
        },
        onAsyncSuccess: function (event, treeId, treeNode, msg) {
            var self = this;
            if (!msg || msg.length == 0) {
                return;
            }
            var zTree = self.getTreeObj(),
                totalCount = treeNode.count;
            // 分页加载
            //if (treeNode.children.length < totalCount) {
            //    setTimeout(function () {
            //        self.fetchNodes(treeNode);
            //    }, self.perTime);
            //} else {
            //    treeNode.icon = "";
            //    zTree.updateNode(treeNode);
            //    zTree.selectNode(treeNode.children[0]);
            //}
        },

        onAsyncError: function (event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo2");
            alert("异步获取数据出现异常。");
            treeNode.icon = "";
            zTree.updateNode(treeNode);
        }
    }).init();
})
;
