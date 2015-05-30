package kr.brainyx.basic;

import kr.brainyx.chat.dao.MemberDao;

public class Test
{
	public static void main(String[] args)
	{
		System.out.println(new MemberDao().normalizedPhone("01089900721","82"));
	}
}
