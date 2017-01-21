<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style type="text/css">
td{
border:1px solid #000;
}
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>welcome</h1>
	<form method="post" autocomplete="off" id="reqform"  action="${requestScope.FPWEB.rootPath }/springmvc/tt/index${requestScope.FPWEB.urlStuff }">
		<div class="table_full">
			<table width="100%">
				<tbody>
					<tr>
						<td>投资金额</td>
						<td><input type="text" name="inv" value="${param.inv }" /></td>
					</tr>
					<!-- Double inv, Integer limit, Double apr, String stime,
			Double dis_r, Double L12, Double L6, Double L0, Double pir -->
					<tr>
						<td>投资期限</td>
						<td><input type="text" name="limit" value="${param.limit }" /></td>
					</tr>
					<tr>
						<td>年化收益率</td>
						<td><input type="text" name="apr" value="${param.apr }" /></td>
					</tr>
					<tr>
						<td>满标终审时间</td>
						<td><input type="text" name="stime" value="${param.stime }" /></td>
					</tr>
					<tr>
						<td>折让率</td>
						<td><input type="text" name="dis_r" value="${param.dis_r }" /></td>
					</tr>
					<tr>
						<td>每月还款金额</td>
						<td>${each_month }</td>
					</tr>
					<tr>
						<td>T大于12个月</td>
						<td><input type="text" name="L12" value="${param.L12 }" /></td>
					</tr>
					<tr>
						<td>12个月~6个月</td>
						<td><input type="text" name="L6" value="${param.L6 }" /></td>
					</tr>
					<tr>
						<td>T小于6个月</td>
						<td><input type="text" name="L0" value="${param.L0 }" /></td>
					</tr>
					<tr>
						<td>分配比例</td>
						<td><input type="text" name="pir" value="${param.pir }" /></td>
					</tr>
				</tbody>
			</table>
		</div>
		<button type="submit" class="btn">提交</button>
	</form>
	<div class="table_full">
	<table>
		<thead>
			<tr>
				<td>转让时间</td>
				<td>当前期数</td>
				<td>待收本金</td>
				<td>应计息天数</td>
				<td>公允价值</td>
				<td>到账金额</td>
				<td>剩余未还期数</td>
				<td>基准年化收益率</td>
				<td>基准转让价格</td>
				<td>受让人分配到的转让收益</td>
				<td>转让价格</td>
				<td>转让时年化收益率</td>
				<!-- <td>激活时间</td> -->
			</tr>
		</thead>
		<!-- 
		        result.put("lim_now",lim_now);
				result.put("lim_left",lim_left);
				result.put("cal_days",cal_days);
				result.put("transtime", DateUtil.timestampToDateString(day.getTimeInMillis()/1000, DateUtil.PATTERN_yyyy_MM_dd));
				result.put("capital",capital);
				result.put("fairv",fairv);
				result.put("actualp",actualp);
				result.put("basev",basev);
				result.put("profit_arranged",profit_arranged);
				result.put("price",price);
				result.put("profit_apr",profit_apr);
		         -->
		<tbody>
			<c:forEach items="${result}" var="item">
				<tr>
					<td>${item.transtime }</td>
					<td>${item.lim_now }</td>
					<td>${item.capital }</td>
					<td>${item.cal_days }</td>
					<td>${item.fairv }</td>
					<td>${item.actualp }</td>
					<td>${item.lim_left }</td>
					<td>${item.base_apr }</td>
					<td>${item.basev }</td>
					<td>${item.profit_arranged }</td>
					<td>${item.price }</td>
					<td>${item.profit_apr }</td>
				</tr>
			</c:forEach>

		</tbody>

	</table>
	</div>
</body>
</html>