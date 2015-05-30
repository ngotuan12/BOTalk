package com.br.chat.activity;

public class Post {

	String title;
	String content;
	String dateTimePosted;
	String author;
	String link;
	String avatarUrl;
	
	public String getAvatarUrl() {
		return avatarUrl;
	}


	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	int user_seq;
	
	
	
	public String getLink() {
		return link;
	}


	public void setLink(String link) {
		this.link = link;
	}


	public Post(String title, String content, String dateTimePosted, String author, String link, String avatarUrl){
		setTitle(title);
		setContent(content);
		setDateTimePosted(dateTimePosted);
		setAuthor(author);
		setAvatarUrl(avatarUrl);
		setLink(link);
	}
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDateTimePosted() {
		return dateTimePosted;
	}
	public void setDateTimePosted(String dateTimePosted) {
		this.dateTimePosted = dateTimePosted;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getUser_seq() {
		return user_seq;
	}
	public void setUser_seq(int user_seq) {
		this.user_seq = user_seq;
	}
	
}
