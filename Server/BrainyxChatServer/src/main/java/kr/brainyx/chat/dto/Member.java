package kr.brainyx.chat.dto;

public class Member
{
	int user_seq = 0;
	String user_id = "";
	String user_name = "";
	// String userNick = "";
	String user_photo = "";
	String user_stmsg = "";
	String user_phone = "";
	String user_regdate = "";
	String user_os = "";
	String useR_pushkey = "";
	String user_email = "";
	String country_code = "";
	public String getCountry_code()
	{
		return country_code;
	}

	public void setCountry_code(String country_code)
	{
		this.country_code = country_code;
	}

	public String getUser_email()
	{
		return user_email;
	}

	public void setUser_email(String user_email)
	{
		this.user_email = user_email;
	}

	// String pushAllow = "";
	public int getUser_seq()
	{
		return user_seq;
	}

	public void setUser_seq(int user_seq)
	{
		this.user_seq = user_seq;
	}

	public String getUser_id()
	{
		return user_id;
	}

	public void setUser_id(String user_id)
	{
		this.user_id = user_id;
	}

	public String getUser_name()
	{
		return user_name;
	}

	public void setUser_name(String user_name)
	{
		this.user_name = user_name;
	}

	public String getUser_photo()
	{
		return user_photo;
	}

	public void setUser_photo(String user_photo)
	{
		this.user_photo = user_photo;
	}

	public String getUser_stmsg()
	{
		return user_stmsg;
	}

	public void setUser_stmsg(String user_stmsg)
	{
		this.user_stmsg = user_stmsg;
	}

	public String getUser_phone()
	{
		return user_phone;
	}

	public void setUser_phone(String user_phone)
	{
		this.user_phone = user_phone;
	}

	public String getUser_regdate()
	{
		return user_regdate;
	}

	public void setUser_regdate(String user_regdate)
	{
		this.user_regdate = user_regdate;
	}

	public String getUser_os()
	{
		return user_os;
	}

	public void setUser_os(String user_os)
	{
		this.user_os = user_os;
	}

	public String getUseR_pushkey()
	{
		return useR_pushkey;
	}

	public void setUseR_pushkey(String useR_pushkey)
	{
		this.useR_pushkey = useR_pushkey;
	}

}
