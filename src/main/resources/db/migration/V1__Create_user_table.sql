create table user
{
   ID INT AUTO_INCREMENT PREPARE KEY  NOT NULL,
   ACCOUNT_ID VARCHAR(100),
   NAME VARCHAR(50),
   TOKEN VARCHAR(36),
   GMT_CREATE BIGINT,
   GMT_MODIFIED BIGINT

};
create table question
(
	id int auto_increment,
	title varchar(50) not null,
	description text not null,
	gmt_create bigint not null,
	gmt_modified bigint not null,
	creator int not null,
	comment_count int default 0,
	view_count int default 0,
	like_count int default 0,
	tag varchar(256) not null,
	constraint question_pk
		primary key (id)
);