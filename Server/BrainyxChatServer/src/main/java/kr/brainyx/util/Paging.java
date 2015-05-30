package kr.brainyx.util;

import java.util.ArrayList;
import java.util.List;

public class Paging {
		
	/**
	 * 검색된 항목들의 갯수와 페이지당 아이템 갯수를 통해 마지막 페이지 번호를 계산하여 리턴한다.
	 * @param itemCount : 검색된 항목들의 갯수
	 * @param itemCountPerPage : 페이지당 아이템 갯수
	 * @return int
	 */
	public static int getMaxPage(int itemCount, int itemCountPerPage) {
		return (int) Math.ceil((float)itemCount / (float)itemCountPerPage);
	}
	
	/**
	 * 마지막 페이지 번호, 페이징에 나열될 페이지번호의 갯수, 전역변수에 담겨있는 현재 페이지 번호를 통해
	 * 페이징에 나열될 페이지 번호가 담긴 List를 생성하여 반환한다.
	 * @param page : 현재 페이지 번호
	 * @param maxPage : 마지막 페이지 번호
	 * @param pageCountPerPaging : 페이징에 나열될 페이지번호의 갯수
	 * @return
	 */
	public static List getPagingList(int currentPage, int maxPage, int pageCountPerPaging) {
		List paging = new ArrayList();
		int startPage = (currentPage-1)/pageCountPerPaging*pageCountPerPaging+1;
		int pageNum = startPage;
	
		int i=1;
		while (i <= pageCountPerPaging) {
			paging.add(pageNum);
			pageNum++;
			if (pageNum > maxPage) break;
			i++;
		}
		return paging;
	}
	
	/**
	 * 
	 * @param currentPage : 현재 페이지 번호
	 * @param itemCount : 검색된 항목들의 갯수
	 * @param itemCountPerPage : 페이지당 아이템 갯수
	 * @param pageCountPerPaging : 페이징에 나열될 페이지번호의 갯수
	 * @return
	 */
	public static String getPaging(int currentPage, int itemCount, int itemCountPerPage, int pageCountPerPaging) {
		String paging = "";
		int maxPage = getMaxPage(itemCount, itemCountPerPage);
		List pagingList = getPagingList(currentPage, maxPage, pageCountPerPaging);
		
		paging += "					<div style=\"margin:5px 0;\">" + itemCount +" 건이 검색되었습니다.</div>" + "\n";
		
		paging += "					<div class=\"pagelist\">" + "\n";
		paging += "						<input type=\"hidden\" name=\"currentPage\" value=\""+ currentPage +"\" />" + "\n";
		paging += "						<button type=\"button\" class=\"round gray\" onclick=\"searchList(this.form, 1);\">&nbsp;&lt;&lt;&nbsp;</button>" + "\n";
		paging += "" + "\n";
		if (currentPage > 1) {
			paging += "						<button type=\"button\" class=\"round gray\" onclick=\"searchList(this.form, "+ (currentPage-1) +");\">&nbsp;&lt;&nbsp;</button>" + "\n";
		}
		if (currentPage <= 1 ) {
			paging += "						<button type=\"button\" class=\"round gray\">&nbsp;&lt;&nbsp;</button>" + "\n";
		}
		paging += "" + "\n";
		for (int i=0; i<pagingList.size(); i++) {
			if (Integer.parseInt(pagingList.get(i).toString()) == currentPage) {
				paging += "								<button type=\"button\" class=\"round white\" style=\"font-weight:bold;\">&nbsp;"+pagingList.get(i)+"&nbsp;</button>" + "\n";
			} else {
				paging += "								<button type=\"button\" class=\"round white\" onclick=\"searchList(this.form, "+pagingList.get(i)+");\">&nbsp;"+pagingList.get(i)+"&nbsp;</button>" + "\n";
			}
		}
		paging += "" + "\n";
		if (currentPage < maxPage) {
			paging += "							<button type=\"button\" class=\"round gray\" onclick=\"searchList(this.form, "+ (currentPage+1) +");\">&nbsp;&gt;&nbsp;</button>" + "\n";
		} else {
			paging += "							<button type=\"button\" class=\"round gray\">&nbsp;&gt;&nbsp;</button>" + "\n";
		}
		paging += "" + "\n";
		paging += "						<button type=\"button\" class=\"round gray\" onclick=\"searchList(this.form, "+ maxPage +");\">&nbsp;&gt;&gt;&nbsp;</button>" + "\n";
		paging += "						<div class=\"clear\"></div>" + "\n";
		paging += "					</div>";
		
		return paging;
	}

	public static String getPageInfo(int currentPage, int itemCount, int itemCountPerPage, int pageCountPerPaging) {
		String paging = "";
		int maxPage = getMaxPage(itemCount, itemCountPerPage);
		List pagingList = getPagingList(currentPage, maxPage, pageCountPerPaging);
		
		paging += "						<input type=\"hidden\" name=\"currentPage\" value=\""+ currentPage +"\" />" + "\n";
		paging += "						<input type=\"hidden\" name=\"maxPage\" value=\""+ maxPage +"\" />" + "\n";
		
		return paging;
	}

}
