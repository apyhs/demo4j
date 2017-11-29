DROP TABLE IF EXISTS sec_user;
CREATE TABLE `sec_user` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL COMMENT '登录名',
  `password` varchar(200) NOT NULL COMMENT '密码',
  `first_name` varchar(10) NOT NULL COMMENT '名字',
  `last_name` varchar(10) NOT NULL COMMENT '姓氏',
  `full_name` varchar(20) NOT NULL COMMENT '全名',
  `email` varchar(200) NOT NULL COMMENT '邮箱',
  `mobile` varchar(50) NOT NULL COMMENT '手机',
  `intro` varchar(255) NOT NULL COMMENT '简介',
  `created_ts` int(11) DEFAULT NULL COMMENT '创建时间戳',
  `updated_ts` int(11) DEFAULT NULL COMMENT '更新时间戳',
  `deleted_ts` int(11) DEFAULT NULL COMMENT '删除时间戳'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

DROP TABLE IF EXISTS sec_role;
CREATE TABLE `sec_role` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '角色名称',
  `value` varchar(50) NOT NULL COMMENT '角色值',
  `intro` varchar(255) NOT NULL COMMENT '简介',
  `pid` int(11) NOT NULL COMMENT '父级id',
  `created_ts` int(11) DEFAULT NULL COMMENT '创建时间戳',
  `updated_ts` int(11) DEFAULT NULL COMMENT '更新时间戳',
  `deleted_ts` int(11) DEFAULT NULL COMMENT '删除时间戳'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

DROP TABLE IF EXISTS sec_user_role;
CREATE TABLE `sec_user_role` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

DROP TABLE IF EXISTS sec_permission;
CREATE TABLE `sec_permission` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '权限名称',
  `value` varchar(255) NOT NULL COMMENT '权限值',
  `intro` varchar(255) NOT NULL COMMENT '简介',
  `method` varchar(50) NOT NULL COMMENT '方法',
  `url` varchar(255) NOT NULL COMMENT 'url地址',
  `pid` int(11) NOT NULL COMMENT '父级id',
  `created_ts` int(11) DEFAULT NULL COMMENT '创建时间戳',
  `updated_ts` int(11) DEFAULT NULL COMMENT '更新时间戳',
  `deleted_ts` int(11) DEFAULT NULL COMMENT '删除时间戳'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

DROP TABLE IF EXISTS sec_role_permission;
CREATE TABLE `sec_role_permission` (
  `id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `permission_id` int(11) NOT NULL COMMENT '权限ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限表';

DROP TABLE IF EXISTS sec_user_permission;
CREATE TABLE `sec_user_permission` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `permission_id` int(11) NOT NULL COMMENT '权限ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户权限表';


-- --------------------------------------------------------
ALTER TABLE `sec_user`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `sec_role`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `sec_user_role`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `sec_permission`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `sec_role_permission`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `sec_user_permission`
  ADD PRIMARY KEY (`id`);


-- --------------------------------------------------------
ALTER TABLE `sec_user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `sec_role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `sec_user_role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `sec_permission`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `sec_role_permission`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `sec_user_permission`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

