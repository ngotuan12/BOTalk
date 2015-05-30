package kr.brainyx.chat.dto;

public class MemberPost
{
	int id = 0;
	int parent_id = 0;
	int user_seq = 0;
	int category_id = 0;
	String status = "";
	String privacy = "";
	String type = "";
	String content = "";
	String create_date = "";

	int num_share = 0;
	String title = "";
	String title_img = "";
	String title_description = "";
	String url = "";
	public int getNum_share()
	{
		return num_share;
	}

	public void setNum_share(int num_share)
	{
		this.num_share = num_share;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getTitle_img()
	{
		return title_img;
	}

	public void setTitle_img(String title_img)
	{
		this.title_img = title_img;
	}

	public String getTitle_description()
	{
		return title_description;
	}

	public void setTitle_description(String title_description)
	{
		this.title_description = title_description;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getParent_id()
	{
		return parent_id;
	}

	public void setParent_id(int parent_id)
	{
		this.parent_id = parent_id;
	}

	public int getUser_seq()
	{
		return user_seq;
	}

	public void setUser_seq(int user_seq)
	{
		this.user_seq = user_seq;
	}

	public int getCategory_id()
	{
		return category_id;
	}

	public void setCategory_id(int category_id)
	{
		this.category_id = category_id;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getPrivacy()
	{
		return privacy;
	}

	public void setPrivacy(String privacy)
	{
		this.privacy = privacy;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
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
