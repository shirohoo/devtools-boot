'use strict';

/**
 * @author shirohoo
 * 페이지 초기화
 */
(function init(){
	let url = '/boards?';
	let param = 'page=0&size=10';
	apiFetch(url, param, callbackDataBinding);
})();

const CONTEXT_PATH = '';

let response = {
	pages           : '',
	visitorsOfReduce: '',
	visitorsOfDay   : ''
};

function apiFetch(url, param, callback){
	fetch(url + param)
		.then(res => {if(res.status === 200) return res.json()})
		.then(data => callback(data))
		.catch(() => alert('400, Bad Request'));
}

function callbackDataBinding(data){
	response.pages = data.pages;
	response.visitorsOfReduce = data.visitorsOfReduce;
	response.visitorsOfDay = data.visitorsOfDay;
	renderingBoardArea()
}

/**
 * 검색 시 엔터키 감지
 */
function enterKeyUp(){
	if(event.keyCode == 13){
		search();
	}
}

/**
 * 숫자 3자리마다 ,추가
 */
function formatNumber(num){
	let regexp = /\B(?=(\d{3})+(?!\d))/g;
	return num.toString()
	          .replace(regexp, ',');
}

/**
 * 초기화 버튼
 */
function resetSearchForm(){
	try{
		document.querySelector('#searchForm').reset();
		document.querySelector('#company').value = '';
		search();
	}
	catch(e){
		alert(`[resetSearchForm] :: ${e.message}`);
	}
}

/**
 * 검색 버튼 클릭시 호출될 함수
 */
function search(){
	try{
		let url = `${CONTEXT_PATH}/boards?`;
		let param = new URLSearchParams();
		let searchConditions = Array.from(
			document.querySelector('#searchForm')
			        .getElementsByTagName('input')
		).filter((element) => {
			return element.id != '';
		});

		for(const conditions of searchConditions){
			param.append(conditions.name, conditions.value);
		}

		let currentPage = document.querySelector('#currentPageNum').value == ''
		                  ? 0 : document.querySelector('#currentPageNum').value;
		let size = document.querySelector('#renderingCount').value;

		param += param == '' ? '' : '&';
		param += `page=${currentPage}&size=${size}`;

		apiFetch(url, param, callbackDataBinding);
	}
	catch(e){
		alert(`[search] :: ${e.message}`);
	}
}

/**
 * 셀렉트 박스 회사 선택시 검색
 */
function selectedCompany(){
	let company = document.querySelector('#company');
	let selectCompany = document.querySelector('#selectCompany');
	company.value = selectCompany.value;
	search();
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
		let param = new URLSearchParams();

		let searchConditions = Array.from(
			document.querySelector('#searchForm')
			        .getElementsByTagName('input')
		).filter((element) => {
			return element.id != '';
		});

		for(const ele of searchConditions){
			param.append(ele.name, ele.value);
		}

		param += param == '' ? '' : '&';
		param += `page=${selectedPage}&size=${size}`;

		apiFetch(url, param, callbackDataBinding);
	}
	catch(e){
		alert(`[getPage] :: ${e.message}`);
	}
}

/**
 * 게시글 목록 렌더링
 */
function renderingBoardArea(){
	try{
		// 게시판 초기화
		let listTbody = document.querySelector('#boards');
		while(listTbody.firstChild){
			listTbody.removeChild(listTbody.firstChild);
		}

		// 누적 방문자 초기화
		let allVisitors = document.querySelector('.SHOW-allVisitors');
		while(allVisitors.firstChild){
			allVisitors.removeChild(allVisitors.firstChild);
		}
		allVisitors.textContent = `${formatNumber(response.visitorsOfReduce)} 명`;

		// 오늘 방문자 초기화
		let dau = document.querySelector('.SHOW-DAU');
		while(dau.firstChild){
			dau.removeChild(dau.firstChild);
		}
		dau.textContent = `${formatNumber(response.visitorsOfDay)} 명`;

		// 렌더링
		let posts = response.pages.content;
		if(posts.length <= 0){
			let eleTr = $('<tr />');
			let eleTd = $('<td />')
				.css('text-align', 'center')
				.css('width', '100%')
				.text('No matching search results found');

			$(eleTr).append(eleTd);
			$('#boards').append(eleTr);
		}
		else{
			$.each(posts, function(){
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

				$('#boards').append(eleTr);
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