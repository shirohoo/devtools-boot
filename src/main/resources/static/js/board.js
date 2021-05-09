'use strict';

const app = new Vue({
	                    el  : '#app',
	                    data: {
		                    CONTEXT : '',
		                    response: {
			                    pages           : '',
			                    visitorsOfReduce: '',
			                    visitorsOfDay   : ''
		                    },
		                    pager   : {},
	                    },

	                    mounted: function(){
		                    let url = '/boards?';
		                    let param = 'page=0&size=10';
		                    this.apiFetch(url, param, this.callbackDataBinding);
	                    },

	                    methods: {
		                    apiFetch(url, param, callback){
			                    fetch(url + param)
				                    .then(res => {if(res.status === 200) return res.json()})
				                    .then(data => callback(data))
				                    .catch(() => alert('400, Bad Request'));
		                    },
		                    callbackDataBinding(data){
			                    this.response.pages = data.pages;
			                    this.response.visitorsOfReduce = data.visitorsOfReduce;
			                    this.response.visitorsOfDay = data.visitorsOfDay;
			                    this.pager = this.setPage(data.pages.pageable.pageNumber,
			                                              data.pages.size,
			                                              data.pages.totalElements,
			                                              data.pages.totalPages);
		                    },
		                    setPage(currentPage, size, totalElements, totalPages){
			                    let endPage = Math.ceil((currentPage + 1) / 10.0) * 10 - 1;
			                    let startPage = endPage - 9;

			                    if(totalPages <= endPage){
				                    endPage = totalPages - 1;
			                    }

			                    let index = [];
			                    for(let i = startPage; i <= endPage; i++){
				                    index.push(i);
			                    }

			                    return {
				                    index        : index,
				                    size         : size,
				                    currentPage  : currentPage,
				                    totalPages   : totalPages,
				                    totalElements: totalElements
			                    };
		                    },
		                    enterKeyUp(){
			                    if(event.keyCode == 13){
				                    this.search();
			                    }
		                    },
		                    formatNumber(num){
			                    let regexp = /\B(?=(\d{3})+(?!\d))/g;
			                    return num.toString().replace(regexp, ',');
		                    },
		                    resetSearchForm(){
			                    document.querySelector('#searchForm').reset();
			                    document.querySelector('#company').value = '';
			                    this.search();
		                    },
		                    selectedCompany(){
			                    let company = document.querySelector('#company');
			                    let selectCompany = document.querySelector('#selectCompany');
			                    company.value = selectCompany.value;
			                    this.search();
		                    },
		                    search(){
			                    let url = `${this.CONTEXT}/boards?`;
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

			                    let currentPage = document.querySelector('#currentPageNum').value == '' ? 0 : document.querySelector('#currentPageNum').value;
			                    let size = document.querySelector('#renderingCount').value;

			                    param += param == '' ? '' : '&';
			                    param += `page=${currentPage}&size=${size}`;

			                    this.apiFetch(url, param, this.callbackDataBinding);
		                    },
		                    isBetweenDay(regDate){
			                    let regDateArray = String(regDate).split('-');
			                    let date = new Date(regDateArray[0], regDateArray[1] - 1, regDateArray[2])

			                    let nowDate = new Date();
			                    let betweenDay = Math.floor((nowDate.getTime() - date.getTime()) / 1000 / 60 / 60 / 24);
			                    return betweenDay === 0;
		                    },
		                    pageMove(targetPage, size){
			                    let url = `${this.CONTEXT}/boards?`;
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
			                    param += `page=${targetPage}&size=${size}`;

			                    this.apiFetch(url, param, this.callbackDataBinding);
		                    },
	                    },
                    });