package kr.brainyx.chat.dto;

public class MemberCategory
{
	int id = 0;
	int user_seq = 0;
	String name = "";
	String code = "";
	private String type = "";
	String status = "";
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

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getCreate_date()
	{
		return create_date;
	}

	public void setCreate_date(String create_date)
	{
		this.create_date = create_date;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

}
