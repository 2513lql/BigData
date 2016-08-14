    /**
 * Created by ceix on 2016-04-14.
 */

var Pager = function(pageNum,pageCount){
    this.pageNum=parseInt(pageNum);
    this.pageCount=parseInt(pageCount);

    this.AppendHtml = function(){
        var html ='';
        html +='<ul class="pagination">';
        var className = this.pageNum ==1?'disabled':'';
        html +='<li class="'+className+'"><a aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>';
        for(var i=1;i<=this.pageCount;i++){
            className = i == this.pageNum?'active':'';
            if(this.pageCount>12) {
                //断开点，设置省略号
                var key1 = 0,key2= 0,key3= 0,key4=0;
                if(this.pageNum<8){ key1=11;key4=this.pageCount-2;}
                else if(this.pageCount-this.pageNum<8){ key1=3,key4=this.pageCount-10; }
                else{  key1=3;key4=this.pageCount-2;key2=this.pageNum-5;key3=this.pageNum+5;}

                if(i<key1 ||(i>key2&&i<key3) ||i>key4)  html +='<li class="'+className+'"><a>'+i+'</a></li>';
                if(i == key1 || (i==key4 && key1!=11 &&key4!=this.pageCount-10)) html +='<li class="disabled"><a>...</a></li>';
            }else {
                html +='<li class="'+className+'"><a>'+i+'</a></li>';
            }
        }
        className =  this.pageNum ==this.pageCount?'disabled':'';
        html +='<li class="'+className+'"><a aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>';
        html +='</ul>';
        $('#pager').append(html);
    }

    this.SetEvents = function(){
        var baseUrl = window.location.host + window.location.pathname+'?';
        var baseSearch = window.location.search.substr(1);
        var reg = new RegExp('(^|&)pageNum=([^&]*)(&|$)', 'i');
        var r = baseSearch.match(reg);
        if(r!=null) var curPage = parseInt(r[2]);
        if(r!=null) baseSearch =baseSearch.replace(r[0],'');
        if(baseSearch!=null && baseSearch!='') baseSearch+='&';

        $('.pagination').find('li a').click(function(){
            if($(this).parent('li').hasClass('disabled')) return;
            var tpage = null;
            if($(this).attr('aria-label') == 'Previous') tpage = curPage-1;
            else if($(this).attr('aria-label') == 'Next') tpage = curPage+1;
            else{tpage = $(this).text();}
            window.location='http://'+baseUrl +baseSearch+ 'pageNum='+tpage;
        });
    }

    this._pager = function(){
        this.AppendHtml();
        this.SetEvents();
    }
    this._pager();
}

$(function(){ new Pager($('#pager').attr('data-pageNum'),$('#pager').attr('data-pageCount'));});


