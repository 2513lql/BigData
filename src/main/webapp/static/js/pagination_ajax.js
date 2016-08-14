/**
 * Created by ceix on 2016-04-14.
 * 关于ajax分页控件使用说明：
 * 1、页面必须设置 <nav id="pager" data-pageNum="xx"></nav>
 * 2、分页控件在点击分页页码时，自动调用page(pageName)方法，页面中ajax查询分页方法命名必须为page,并且设置参数pageNum
 * 3、设置新的查询条件时，必须调用ResetPager(pageNum,pageCount),重新修改分页控件
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
        $('#pager').empty();

        $('#pager').attr('data-pageNum',this.pageNum);
        $('#pager').attr('data-pageCount',this.pageCount);

        $('#pager').append(html);
    }

    this.SetEvents = function(){
        $('.pagination').find('li a').click(function(){
            if($(this).parent('li').hasClass('disabled')) return;
            var tpage = null;
            var curPage =  parseInt( $('#pager').attr('data-pageNum'));
            if($(this).attr('aria-label') == 'Previous') tpage = curPage-1;
            else if($(this).attr('aria-label') == 'Next') tpage = curPage+1;
            else{tpage = $(this).text();}
            $('#pager').attr('data-pageNum',tpage);
            if($("#search").val()!="")
                searchPage(tpage);
            else
                page(tpage);
        });
    }

    this._pager = function(){
        this.AppendHtml();
        this.SetEvents();
    }
    this._pager();
}

var ResetPager = function(pageNum,pageCount){
    new Pager(pageNum,pageCount);
}



