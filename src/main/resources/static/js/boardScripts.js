const CONTEXT_PATH = "";
let ajaxResponse;

/**
 * 페이지 최초 로딩 시 첫페이지, 사이즈10 렌더링
 */
$(function () {
	$.ajax({
		       url        : "/collects?page=0&size=10",
		       type       : "GET",
		       contentType: "application/x-www-form-urlencoded;charset=utf-8",
		       dataType   : "json",
		       success    : function (data) {
			       ajaxResponse = data;
			       listRendering()
		       },
		       error      : function () {
			       alert("Error. 관리자에게 문의하십시오.");
		       },
	       });
});

function selectedCompany() {
	let selected = $('#selectCompany').val();
	$('#company').val(selected);
}

function initForm(searchFormId) {
	try {
		$("#" + searchFormId)[0].reset();
	}
	catch (e) {
		alert("[ initForm(searchFormId) ] :: " + e.message);
	}
}

function requestAjax(url, param) {
	try {
		$.ajax({
			       url        : url + param,
			       type       : "GET",
			       contentType: "application/x-www-form-urlencoded;charset=utf-8",
			       dataType   : "json",
			       success    : function (data) {
				       ajaxResponse = data;
				       listRendering()
			       },
			       error      : function () {
				       alert("Error. 관리자에게 문의하십시오.");
			       },
		       });

	}
	catch (e) {
		alert("[ requestAjax ] :: " + e.message);
	}
}

/**
 * 각 페이지 버튼에 페이지 이동기능 추가
 * @param selectedPageNum 이동하려는 페이지 번호
 * @param size 보여주려는 목록의 갯수
 */
function pageMove(selectedPageNum, size) {
	try {
		searchResultList(selectedPageNum, size);

	}
	catch (e) {
		alert("[ pageMove(renderingCount, selectedPageNum) ] :: " + e.message);
	}
}

/**
 * 선택한 페이지에 해당하는 게시글을 불러옴
 * @param selectedPageNum 선택한 페이지
 * @param size 보여주려는 목록의 갯수
 */
function searchResultList(selectedPageNum, size) {
	try {
		let url = CONTEXT_PATH + "/collects?";
		let param = $("#searchForm :input").filter(function (idx, element) {
			return $(element).val() != '';
		}).serialize();

		param += param == "" ? "" : "&";
		param += "page=" + selectedPageNum + "&size=" + size;

		requestAjax(url, param);

	}
	catch (e) {
		alert("[ searchResultList() ] :: " + e.message);
	}
}

/**
 * 검색 버튼 클릭시 호출될 함수
 */
function searchPost() {
	try {
		let url = CONTEXT_PATH + "/collects?";
		let currentPageNum = $("#currentPageNum").val() == "" ? 0 : $("#currentPageNum").val();
		let size = $("#renderingCount").val();
		let param = $("#searchForm :input").filter(function (idx, element) {
			return $(element).val() != '';
		}).serialize();

		param += param == "" ? "" : "&";
		param += "page=" + currentPageNum + "&size=" + size;

		requestAjax(url, param);
	}
	catch (e) {
		alert("[ searchResultList() ] " + e.message);
	}
}

/**
 * 게시글 목록 렌더링
 */
function listRendering() {
	try {
		$("#listTbody").empty();

		let postList = ajaxResponse.content;

		if (postList.length <= 0) {
			let eleTr = $("<tr />");
			let eleTd = $("<td />")
				.css("text-align", "center")
				.css("width", "100%")
				.text("No matching search results found");

			$(eleTr).append(eleTd);

			$("#listTbody").append(eleTr);
		} else {
			$.each(postList, function () {
				let eleTr = $("<tr class='graph_tr1'/>")
					.append($("<td style='text-align:center'/>").text(this.id))
					.append($("<td style='text-align:center'/>").append($("<img src=" + this.imgPath + " height='48px' width='64px'/>")))
					.append($("<td style='text-align:center'/>").append($("<a/>").attr("href", this.link)
					                                                             .attr("target", "blank")
					                                                             .text(this.title)))
					.append($("<td style='text-align:center'/>").text(this.regDate));

				$("#listTbody").append(eleTr);
			});
		}

		$("#totalResultCount").empty().append("Posts  : " + ajaxResponse.totalElements);
		renderingPagingArea();
	}
	catch (e) {
		alert("[ listRendering() ] " + e.message);
	}
}

function renderingPagingArea() {
	try {
		$("#pagingArea").empty();

		if (ajaxResponse.first != true) {
			let prePagebutton = $("<li/>").attr("class", "page-item")
			                              .append(
				                              $("<a/>").attr("class", "page-link")
				                                       .attr("href", "javascript:pageMove('"
				                                                     + (ajaxResponse.pageable.pageNumber - 1)
				                                                     + "', " + ajaxResponse.size + ");")
				                                       .text("<")
			                              );
			$("#pagingArea").append(prePagebutton);
		}

		// 페이징에 필요한 데이터 생성
		let endPage = Math.ceil((ajaxResponse.pageable.pageNumber + 1) / 10.0) * 10 - 1;
		let trueEndPage = Math.ceil(ajaxResponse.totalElements / ajaxResponse.size);
		let startPage = endPage - 9;

		if (trueEndPage <= endPage) endPage = trueEndPage - 1;

		// 페이지 번호 버튼 생성
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

			$("#pagingArea").append(pageNumButton);
		}

		// 다음 페이지 버튼 생성
		if (ajaxResponse.last != true) {

			let nextPagebutton = $("<li/>").attr("class", "page-item")
			                               .append(
				                               $("<a/>").attr("class", "page-link")
				                                        .attr("href", "javascript:pageMove('"
				                                                      + (ajaxResponse.pageable.pageNumber + 1)
				                                                      + "', " + ajaxResponse.size + ");")
				                                        .text(">")
			                               );

			$("#pagingArea").append(nextPagebutton);
		}
	}
	catch
		(e) {
		alert("[ renderingPagingArea() ] :: " + e.message);
	}
}