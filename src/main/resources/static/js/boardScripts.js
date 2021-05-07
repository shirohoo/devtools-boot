'use strict';

/**
 * @author shirohoo
 * 페이지 초기화
 */
(function init(){
	requestGetAjax('/boards?page=0&size=10', '');
})();

const CONTEXT_PATH = '';

let response = {
	pages           : '',
	visitorsOfReduce: '',
	visitorsOfDay   : ''
};

function requestGetAjax(url, param){
	try{
		$.ajax({
			       url        : url + param,
			       type       : 'get',
			       contentType: 'application/x-www-form-urlencoded;charset=utf-8',
			       dataType   : 'json',
			       success    : (data) => {
				       response.pages = data.pages;
				       response.visitorsOfReduce = data.visitorsOfReduce;
				       response.visitorsOfDay = data.visitorsOfDay;
				       listRendering()
			       },
			       error      : () => {
				       alert('Error. 관리자에게 문의하십시오.');
			       },
		       });

	}
	catch(e){
		alert(`[requestAjax] :: ${e.message}`);
	}
}

/**
 * 검색 시 엔터키 감지
 */
function enterKeyup(){
	if(event.keyCode == 13){
		searchPost();
	}
}

/**
 * 숫자 3자리마다 ,추가
 */
function numberFormat(num){
	let regexp = /\B(?=(\d{3})+(?!\d))/g;
	return num.toString()
	          .replace(regexp, ',');
}

/**
 * 초기화 버튼
 */
function resetSearchForm(searchFormId){
	try{
		$(`#${searchFormId}`)[0].reset();
		$('#company').val('');
		searchPost();
	}
	catch(e){
		alert(`[resetSearchForm] :: ${e.message}`);
	}
}

/**
 * 셀렉트 박스 회사 선택시 검색
 */
function selectedCompany(){
	let company = document.querySelector('#company');
	let selectCompany = document.querySelector('#selectCompany');
	company.value = selectCompany.value;
	searchPost();
}

/**
 * 각 페이지 버튼에 페이지 이동기능 추가
 * @param targetPage 이동하려는 페이지
 * @param size 보여주려는 목록의 갯수
 */
function pageMove(targetPage, size){
	try{
		getPage(targetPage, size);
		window.scrollTo({
			                top     : 0,
			                left    : 0,
			                behavior: 'smooth'
		                });
	}
	catch(e){
		alert(`[pageMove] :: ${e.message}`);
	}
}

/**
 * 선택한 페이지에 해당하는 게시글을 불러옴
 * @param selectedPage 선택한 페이지
 * @param size 보여주려는 목록의 갯수
 */
function getPage(selectedPage, size){
	try{
		let url = `${CONTEXT_PATH}/boards?`;
		let param = $('#searchForm :input')
			.filter((idx, element) => {
				return $(element).val() != '';
			}).serialize();
		param += param == '' ? '' : '&';
		param += `page=${selectedPage}&size=${size}`;
		requestGetAjax(url, param);
	}
	catch(e){
		alert(`[searchResultList] :: ${e.message}`);
	}
}

/**
 * 검색 버튼 클릭시 호출될 함수
 */
function searchPost(){
	try{
		let url = `${CONTEXT_PATH}/boards?`;
		let currentPage = $('#currentPageNum').val() == '' ? 0 : $('#currentPageNum').val();
		let size = $('#renderingCount').val();
		let param = $('#searchForm :input')
			.filter((idx, element) => {
				return $(element).val() != '';
			}).serialize();
		param += param == '' ? '' : '&';
		param += `page=${currentPage}&size=${size}`;
		requestGetAjax(url, param);
	}
	catch(e){
		alert(`[searchResultList] :: ${e.message}`);
	}
}

/**
 * 게시글 목록 렌더링
 */
function listRendering(){
	try{
		$('#listTbody').empty();

		$('.SHOW-allVisitors').empty();
		$('.SHOW-allVisitors').text(`${numberFormat(response.visitorsOfReduce)} 명`);

		$('.SHOW-DAU').empty();
		$('.SHOW-DAU').text(`${numberFormat(response.visitorsOfDay)} 명`);

		let postList = response.pages.content;

		if(postList.length <= 0){
			let eleTr = $('<tr />');
			let eleTd = $('<td />')
				.css('text-align', 'center')
				.css('width', '100%')
				.text('No matching search results found');

			$(eleTr).append(eleTd);
			$('#listTbody').append(eleTr);
		}
		else{
			$.each(postList, function(){
				let regDateArray = this.regDate.split('-');
				let regDate = new Date(regDateArray[0], regDateArray[1] - 1, regDateArray[2])

				let nowDate = new Date();
				let betweenDay = Math.floor((nowDate.getTime() - regDate.getTime()) / 1000 / 60 / 60 / 24);

				let eleTr = $("<tr class='graph_tr1'/>").append($(`<td style="text-align:center"/>`)
					                                                .append($(`<img src="${this.imgPath}" height="48px" width="96px" title="${this.company}" />`)))

				                                        .append($(`<td style="text-align:center"/>`)
					                                                .append($('<span/>')
						                                                        .attr('class', (betweenDay == 0 ? 'badge badge-danger' : ''))
						                                                        .text((betweenDay == 0 ? 'Today' : '')))
					                                                .css('text-align', 'left')
					                                                .css('font-weight', 'bold')
					                                                .css('color', 'skyblue')
					                                                .append($("<a/>")
						                                                        .attr('href', this.link)
						                                                        .attr('target', 'blank')
						                                                        .text(' ' + this.title)))

				                                        .append($(`<td style="text-align:center"/>`)
					                                                .text(this.regDate));

				$('#listTbody').append(eleTr);
			});
		}
		$('#totalResultCount').empty().append(`TOTAL : ${response.pages.totalElements}`);
		renderingPagingArea();
	}
	catch(e){
		alert(`[listRendering] :: ${e.message}`);
	}
}

/**
 * 페이지네이션 렌더링
 */
function renderingPagingArea(){
	try{
		$('#pagingArea').empty();
		if(response.pages.first != true){
			let prePagebutton = $('<li/>').attr('class', 'page-item')
			                              .append($('<a/>')
				                                      .attr('class', 'page-link')
				                                      .attr('href', `javascript:pageMove(${(response.pages.pageable.pageNumber - 1)}, ${response.pages.size});`)
				                                      .text('<')
			                              );

			$('#pagingArea').append(prePagebutton);
		}

		let endPage = Math.ceil((response.pages.pageable.pageNumber + 1) / 10.0) * 10 - 1;
		let trueEndPage = Math.ceil(response.pages.totalElements / response.pages.size);
		let startPage = endPage - 9;

		if(trueEndPage <= endPage){
			endPage = trueEndPage - 1;
		}

		for(let i = startPage; i <= endPage; i++){
			let pageNumButton = $("<li/>").attr('class', 'page-item');
			if(response.pages.pageable.pageNumber == i){
				$(pageNumButton).attr('class', 'page-item active');
			}
			$(pageNumButton).append($('<a/>')
				                        .attr('class', 'page-link')
				                        .attr('href', `javascript:pageMove(${i}, ${response.pages.size});`)
				                        .text(i + 1)
			);
			$('#pagingArea').append(pageNumButton);
		}

		if(response.pages.last != true){
			let nextPageButton = $('<li/>').attr('class', 'page-item')
			                               .append($("<a/>").attr('class', 'page-link')
			                                                .attr('href',
			                                                      `javascript:pageMove(${(response.pages.pageable.pageNumber + 1)}, ${response.pages.size});`
			                                                )
			                                                .text('>')
			                               );
			$('#pagingArea').append(nextPageButton);
		}

	}
	catch(e){
		alert(`[renderingPagingArea] :: ${e.message}`);
	}
}