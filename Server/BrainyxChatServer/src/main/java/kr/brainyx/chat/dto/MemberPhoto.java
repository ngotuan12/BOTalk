package kr.brainyx.chat.dto;

public class MemberPhoto
{
	int id = 0;
	int user_seq = 0;
	String url = "";
	String status = "";
	String category_code = "";
	String create_date = "";

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getUser_seq()
	{
		return user_seq;
	}

	public void setUser_seq(int user_seq)
	{
		this.user_seq = user_seq;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getCategory_code()
	{
		return category_code;
	}

	public void setCategory_code(String category_code)
	{
		this.category_code = category_code;
	}

	public String getCreate_date()
	{
		return create_date;
	}

	public void setCreate_date(String create_date)
	{
		this.create_date = create_date;
	}

}
