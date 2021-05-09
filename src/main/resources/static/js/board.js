'use strict';
const app = new Vue({
	                    el     : '#app',
	                    data   : {
		                    search          : {
			                    page   : 0,
			                    size   : 10,
			                    company: '',
			                    title  : ''
		                    },
		                    contents        : {},
		                    pager           : {},
		                    visitorsOfReduce: 0,
		                    visitorsOfDay   : 0
	                    },
	                    mounted: function(){
		                    this.$nextTick(function(){
			                    this.findContents();
		                    });
	                    },
	                    methods: {
		                    findContents(page){
			                    if(page !== undefined) this.search.page = page;
			                    let queryString = Object.keys(this.search)
			                                            .map(k => encodeURIComponent(k) + '=' + encodeURIComponent(this.search[k]))
			                                            .join('&');
			                    let uri = '/boards?' + queryString;
			                    fetch(uri).then(res => {if(res.status === 200) return res.json()})
			                              .then(data => {
				                              this.contents = data.pages.content;
				                              this.visitorsOfReduce = data.visitorsOfReduce;
				                              this.visitorsOfDay = data.visitorsOfDay;
				                              this.pager = this.setPager(data.pages);
			                              })
			                              .catch(() => alert('400, Bad Request'));
		                    },
		                    setPager(pages){
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
		                    },
		                    numberFormatter(num){
			                    let regexp = /\B(?=(\d{3})+(?!\d))/g;
			                    return num.toString().replace(regexp, ',');
		                    },
		                    enterKeyUp(){
			                    if(event.keyCode === 13){
				                    this.findContents();
			                    }
		                    },
		                    resetSearchForm(){
			                    this.search.page = 0;
			                    this.search.size = 10;
			                    this.search.company = '';
			                    this.search.title = '';
			                    this.findContents();
		                    },
		                    isBetweenDay(regDate){
			                    let regDateArray = String(regDate).split('-');
			                    let date = new Date(regDateArray[0], regDateArray[1] - 1, regDateArray[2])

			                    let nowDate = new Date();
			                    let betweenDay = Math.floor((nowDate.getTime() - date.getTime()) / 1000 / 60 / 60 / 24);
			                    return betweenDay === 0;
		                    },
	                    },
                    });