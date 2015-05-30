package kr.brainyx.util;

public class AppException extends Exception
{

	/**
	 * @author TuanNA
	 * @since 05-13-2015
	 */
	private static final long serialVersionUID = 1L;
	private String mstrCode;
	public AppException(String strCode,String strMessage)
	{
		super(strMessage);
		setMsgCode(strCode);
	}
	
	public String getMsgCode()
	{
		return mstrCode;
	}
	
	public void setMsgCode(String mstrCode)
	{
		this.mstrCode = mstrCode;
	}
}
