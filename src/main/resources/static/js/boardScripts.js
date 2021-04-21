"use strict";

const CONTEXT_PATH = "";
let ajaxResponse;
let dau;

/**
 * @author shirohoo
 * 페이지 초기화
 */
$(function () {
	$.ajax({
		       url        : "/boards?page=0&size=10",
		       type       : "GET",
		       contentType: "application/x-www-form-urlencoded;charset=utf-8",
		       dataType   : "json",
		       success    : function (data) {
			       ajaxResponse = data.pages;
			       dau = data.dau;
			       listRendering()
		       },
		       error      : function () {
			       alert("Error. 관리자에게 문의하십시오.");
		       },
	       });
});

function requestAjax(url, param) {
	try {
		$.ajax({
			       url        : url + param,
			       type       : "GET",
			       contentType: "application/x-www-form-urlencoded;charset=utf-8",
			       dataType   : "json",
			       success    : function (data) {
				       ajaxResponse = data.pages;
				       dau = data.dau;
				       listRendering()
			       },
			       error      : function () {
				       alert("Error. 관리자에게 문의하십시오.");
			       },
		       });

	}
	catch (e) {
		alert("[requestAjax] :: " + e.message);
	}
}

/**
 * 셀렉트 박스 회사 선택시 검색
 */
function selectedCompany() {
	$('#company').val($('#selectCompany').val());
	searchPost();
}

/**
 * 각 페이지 버튼에 페이지 이동기능 추가
 * @param selectedPageNum 이동하려는 페이지 번호
 * @param size 보여주려는 목록의 갯수
 */
function pageMove(selectedPageNum, size) {
	try {
		searchResultList(selectedPageNum, size);
		window.scrollTo({
			                top: 0, left: 0, behavior: 'smooth'
		                });
	}
	catch (e) {
		alert("[pageMove] :: " + e.message);
	}
}

/**
 * 선택한 페이지에 해당하는 게시글을 불러옴
 * @param selectedPageNum 선택한 페이지
 * @param size 보여주려는 목록의 갯수
 */
function searchResultList(selectedPageNum, size) {
	try {
		let url = CONTEXT_PATH + "/boards?";
		let param = $('#searchForm :input').filter(function (idx, element) {
			return $(element).val() != '';
		}).serialize();

		param += param == "" ? "" : "&";
		param += "page=" + selectedPageNum + "&size=" + size;

		requestAjax(url, param);

	}
	catch (e) {
		alert("[searchResultList] :: " + e.message);
	}
}

/**
 * 검색 버튼 클릭시 호출될 함수
 */
function searchPost() {
	try {
		let url = CONTEXT_PATH + "/boards?";
		let currentPageNum = $('#currentPageNum').val() == "" ? 0 : $('#currentPageNum').val();
		let size = $('#renderingCount').val();
		let param = $('#searchForm :input').filter(function (idx, element) {
			return $(element).val() != '';
		}).serialize();

		param += param == "" ? "" : "&";
		param += "page=" + currentPageNum + "&size=" + size;

		requestAjax(url, param);
	}
	catch (e) {
		alert("[searchResultList] " + e.message);
	}
}

/**
 * 게시글 목록 렌더링
 */
function listRendering() {
	try {
		$('#listTbody').empty();
		$('.SHOW-DAU').empty();
		$('.SHOW-DAU').text(dau + '명');

		let postList = ajaxResponse.content;

		if (postList.length <= 0) {
			let eleTr = $("<tr />");
			let eleTd = $("<td />")
				.css("text-align", "center")
				.css("width", "100%")
				.text("No matching search results found");

			$(eleTr).append(eleTd);

			$('#listTbody').append(eleTr);
		} else {
			$.each(postList, function () {
				let regDateArray = this.regDate.split('-');
				let regDate = new Date(regDateArray[0], regDateArray[1] - 1, regDateArray[2])

				let nowDate = new Date();
				let betweenDay = Math.floor((nowDate.getTime() - regDate.getTime()) / 1000 / 60 / 60 / 24);

				let eleTr = $("<tr class='graph_tr1'/>")
					.append($("<td style='text-align:center'/>").append($("<img src=" + this.imgPath + " height='48px' width='96px' title='" + this.company + "'/>")))
					.append($("<td style='text-align:center'/>").append($("<span/>").attr("class", (betweenDay == 0 ? "badge badge-danger" : ""))
					                                                                .text((betweenDay == 0 ? "Today" : "")))
					                                            .css("text-align", "left")
					                                            .css("font-weight", "bold")
					                                            .css("color", "skyblue")
					                                            .append($("<a/>").attr("href", this.link)
					                                                             .attr("target", "blank")
					                                                             .text(" " + this.title)))
					.append($("<td style='text-align:center'/>").text(this.regDate));

				$('#listTbody').append(eleTr);
			});
		}
		$('#totalResultCount').empty().append("TOTAL  : " + ajaxResponse.totalElements);
		renderingPagingArea();
	}
	catch (e) {
		alert("[listRendering] " + e.message);
	}
}

function renderingPagingArea() {
	try {
		$('#pagingArea').empty();

		if (ajaxResponse.first != true) {
			let prePagebutton = $("<li/>").attr("class", "page-item")
			                              .append(
				                              $("<a/>").attr("class", "page-link")
				                                       .attr("href", "javascript:pageMove('"
				                                                     + (ajaxResponse.pageable.pageNumber - 1)
				                                                     + "', " + ajaxResponse.size + ");")
				                                       .text("<")
			                              );
			$('#pagingArea').append(prePagebutton);
		}

		let endPage = Math.ceil((ajaxResponse.pageable.pageNumber + 1) / 10.0) * 10 - 1;
		let trueEndPage = Math.ceil(ajaxResponse.totalElements / ajaxResponse.size);
		let startPage = endPage - 9;

		if (trueEndPage <= endPage) endPage = trueEndPage - 1;

		for (let i = startPage; i <= endPage; i++) {
			let pageNumButton = $("<li/>").attr("class", "page-item");

			if (ajaxResponse.pageable.pageNumber == i) {
				$(pageNumButton).attr("class", "page-item active");
			}

			$(pageNumButton).append(
				$("<a/>").attr("class", "page-link")
				         .attr("href", "javascript:pageMove('"
				                       + i
				                       + "', " + ajaxResponse.size + ");")
				         .text(i + 1)
			);

			$('#pagingArea').append(pageNumButton);
		}

		if (ajaxResponse.last != true) {

			let nextPagebutton = $("<li/>").attr("class", "page-item")
			                               .append(
				                               $("<a/>").attr("class", "page-link")
				                                        .attr("href", "javascript:pageMove('"
				                                                      + (ajaxResponse.pageable.pageNumber + 1)
				                                                      + "', " + ajaxResponse.size + ");")
				                                        .text(">")
			                               );

			$('#pagingArea').append(nextPagebutton);
		}
	}
	catch
		(e) {
		alert("[renderingPagingArea] :: " + e.message);
	}
}

function resetSearchForm(searchFormId) {
	try {
		$("#" + searchFormId)[0].reset();
	}
	catch (e) {
		alert("[ resetSearchForm() ] :: " + e.message);
	}
}

function enterKeyup() {
	if (event.keyCode == 13) {
		searchPost();
	}
}