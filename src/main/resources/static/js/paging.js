'use strict';
const pagingComponent = {
	props   : {
		pager       : Object,
		findFunction: Function
	},
	template: '' +
	          '<ul class="pagination pagination-sm justify-content-center" id="pagingArea" >' +
	          '<li v-show="pager.first !== true" class="page-item" style="cursor: pointer" >' +
	          '<a class="page-link" @click="findFunction(pager.currentPage - 1);" >&lt;</a >' +
	          '</li >' +
	          '<li v-for="i in pager.index" class="page-item" :class="{\'active\' : pager.currentPage === i}" style="cursor: pointer" >' +
	          '<a class="page-link" @click="findFunction(i);" >{{i+1}}</a >' +
	          '</li >' +
	          '<li v-show="pager.last !== true" class="page-item" style="cursor: pointer" >' +
	          '<a class="page-link" @click="findFunction(pager.currentPage + 1);" >&gt;</a >' +
	          '</li>' +
	          '</ul >'
};

function setPager(pages){
	let endPage = Math.ceil((pages.pageable.pageNumber + 1) / 10.0) * 10 - 1;
	let startPage = endPage - 9;
	if(pages.totalPages <= endPage){
		endPage = pages.totalPages - 1;
	}

	let index = [];
	for(let i = startPage; i <= endPage; i++){
		index.push(i);
	}

	return {
		index        : index,
		size         : pages.size,
		first        : pages.first,
		last         : pages.last,
		currentPage  : pages.pageable.pageNumber,
		totalPages   : pages.totalPages,
		totalElements: pages.totalElements
	};
}