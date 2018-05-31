-- --------------------------------------------------------
CREATE TABLE `system_user` (
  `id` int(11) NOT NULL,
  `nickname` varchar(64) NOT NULL COMMENT '昵称',
  `username` varchar(64) NOT NULL COMMENT '用户名，不能重复',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `gender` int(1) NOT NULL COMMENT '性别',
  `phone` varchar(64) NOT NULL COMMENT '手机',
  `email` varchar(64) NOT NULL COMMENT '邮箱',
  `private_domain` varchar(64) NOT NULL COMMENT '个性域名/私人域名',
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '用户状态：0 正常、1 冻结',
  `group_id` varchar(256) DEFAULT NULL COMMENT '组别ID，以特定格式存多个',
  `role_id` varchar(256) DEFAULT NULL COMMENT '角色ID，以特定格式存多个',
  `perm_id` varchar(256) DEFAULT NULL COMMENT '权限ID，以特定格式存多个',
  `last_modify` int(11) NOT NULL COMMENT '上一次修改密码时间戳',
  `last_login` int(11) NOT NULL COMMENT '上一次登录时间戳',
  `last_address` varchar(64) NOT NULL COMMENT '上一次登录地址信息'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户表';

CREATE TABLE `user_group` (
  `id` int(11) NOT NULL,
  `group_name` varchar(64) NOT NULL COMMENT '组别名称',
  `group_text` varchar(256) NOT NULL COMMENT '组别描述',
  `parent_id` int(11) NOT NULL COMMENT '父组别ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户组别表';

CREATE TABLE `user_role` (
  `id` int(11) NOT NULL,
  `role_name` varchar(64) NOT NULL COMMENT '角色名称',
  `role_text` varchar(256) NOT NULL COMMENT '角色描述',
  `perm_id` varchar(256) DEFAULT NULL COMMENT '权限ID，以特定格式存多个'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

CREATE TABLE `user_permission` (
  `id` int(11) NOT NULL,
  `perm_name` varchar(64) NOT NULL COMMENT '权限名称',
  `perm_text` varchar(256) NOT NULL COMMENT '权限描述',
  `res_id` varchar(256) DEFAULT NULL COMMENT '资源ID，以特定格式存多个'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户权限表';

CREATE TABLE `system_resource` (
  `id` int(11) NOT NULL,
  `res_name` varchar(64) NOT NULL COMMENT '资源名称',
  `res_text` varchar(256) NOT NULL COMMENT '资源描述'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统资源表';

CREATE TABLE `user_friend` (
  `id` int(11) NOT NULL,
  `res_name` varchar(64) NOT NULL COMMENT '用户标识',
  `res_text` varchar(256) NOT NULL COMMENT '好友的用户标识'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户好友表';

CREATE TABLE `user_info_base` (
  `id` int(11) NOT NULL,
  `group_name` varchar(64) DEFAULT NULL COMMENT '真实姓名',
  `group_text` varchar(256) DEFAULT NULL COMMENT '所在地',
  `group_text` varchar(256) DEFAULT NULL COMMENT '性别',
  `group_text` varchar(256) DEFAULT NULL COMMENT '性取向',
  `group_text` varchar(256) DEFAULT NULL COMMENT '感情状况',
  `group_text` varchar(256) DEFAULT NULL COMMENT '生日',
  `group_text` varchar(256) DEFAULT NULL COMMENT '血型',
  `group_text` varchar(256) DEFAULT NULL COMMENT '简介',
  `group_text` varchar(256) DEFAULT NULL COMMENT '预留1',
  `group_text` varchar(256) DEFAULT NULL COMMENT '预留2',
  `group_text` varchar(256) DEFAULT NULL COMMENT '预留3',
  `group_text` varchar(256) DEFAULT NULL COMMENT '预留4',
  `group_text` varchar(256) DEFAULT NULL COMMENT '预留5',
  `group_text` varchar(256) DEFAULT NULL COMMENT '预留6',
  `group_text` varchar(256) DEFAULT NULL COMMENT '预留7',
  `group_text` varchar(256) DEFAULT NULL COMMENT '预留8',
  `parent_id` int(11) NOT NULL COMMENT '父组别ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户基础信息表';

CREATE TABLE `user_info_contact` (
  `id` int(11) NOT NULL,
  `group_text` varchar(256) DEFAULT NULL COMMENT '博客地址',
  `group_text` varchar(256) DEFAULT NULL COMMENT 'MSN',
  `group_text` varchar(256) DEFAULT NULL COMMENT 'QQ',
  `group_text` varchar(256) DEFAULT NULL COMMENT '微博',
  `group_text` varchar(256) DEFAULT NULL COMMENT '微信',
  `group_text` varchar(256) DEFAULT NULL COMMENT '旺旺',
  `group_text` varchar(256) DEFAULT NULL COMMENT '预留1',
  `group_text` varchar(256) DEFAULT NULL COMMENT '预留2',
  `group_text` varchar(256) DEFAULT NULL COMMENT '预留3',
  `group_text` varchar(256) DEFAULT NULL COMMENT '预留4',
  `group_text` varchar(256) DEFAULT NULL COMMENT '预留5',
  `group_text` varchar(256) DEFAULT NULL COMMENT '预留6',
  `group_text` varchar(256) DEFAULT NULL COMMENT '预留7',
  `group_text` varchar(256) DEFAULT NULL COMMENT '预留8',
  `parent_id` int(11) NOT NULL COMMENT '父组别ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户联系信息表';

CREATE TABLE `user_info_edu` (
  `id` int(11) NOT NULL,
  `group_name` varchar(64) NOT NULL COMMENT '学校名称',
  `group_text` varchar(256) NOT NULL COMMENT '院系',
  `group_text` varchar(256) NOT NULL COMMENT '学校地址',
  `group_text` varchar(256) NOT NULL COMMENT '入学时间',
  `group_text` varchar(256) NOT NULL COMMENT '毕业时间',
  `group_text` varchar(256) NOT NULL COMMENT '备注',
  `group_text` varchar(256) NOT NULL COMMENT '创建时间',
  `parent_id` int(11) NOT NULL COMMENT '父组别ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户教育信息表';

CREATE TABLE `user_info_job` (
  `id` int(11) NOT NULL,
  `group_name` varchar(64) NOT NULL COMMENT '公司名称',
  `group_text` varchar(256) NOT NULL COMMENT '职位',
  `group_text` varchar(256) NOT NULL COMMENT '公司地址',
  `group_text` varchar(256) NOT NULL COMMENT '入职时间',
  `group_text` varchar(256) NOT NULL COMMENT '辞职时间',
  `group_text` varchar(256) NOT NULL COMMENT '备注',
  `group_text` varchar(256) NOT NULL COMMENT '创建时间',
  `parent_id` int(11) NOT NULL COMMENT '父组别ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户职业信息表';

CREATE TABLE `user_info_address` (
  `id` int(11) NOT NULL,
  `group_name` varchar(64) NOT NULL COMMENT '收货人/联系人',
  `group_text` varchar(256) NOT NULL COMMENT '所在地区',
  `group_text` varchar(256) NOT NULL COMMENT '详细地址',
  `group_text` varchar(256) NOT NULL COMMENT '邮编',
  `group_text` varchar(256) NOT NULL COMMENT '手机/座机',
  `parent_id` int(11) NOT NULL COMMENT '父组别ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户收货/联系地址表';

-- --------------------------------------------------------
ALTER TABLE `system_user`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `user_group`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `user_role`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `user_permission`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `system_resource`
  ADD PRIMARY KEY (`id`);


-- --------------------------------------------------------
ALTER TABLE `system_user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `user_group`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `user_role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `user_permission`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `system_resource`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;